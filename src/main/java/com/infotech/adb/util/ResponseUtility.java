package com.infotech.adb.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.infotech.adb.dto.BaseDTO;
import com.infotech.adb.dto.RequestParameter;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DBConstraintViolationException;
import com.infotech.adb.exceptions.NoDataFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Log4j2
public class ResponseUtility {

    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    /**
     * Generate success response
     * This method will not be used locally
     *
     * @param data
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static CustomResponse successResponse(Object data, String responseCode, String responseMessage) {
        return successResponse(data,responseCode,responseMessage, null,false);
    }

    @SuppressWarnings("rawtypes")
    public static CustomResponse successResponse(Object data, String responseCode, String responseMessage,RequestParameter requestParameter, boolean isPSWResponse) {

        HttpStatus status = HttpStatus.OK;
        String message = AppUtility.isEmpty(responseMessage)
                ? messageBundle.getString("generic.success")
                : responseMessage;

        if(AppUtility.isEmpty(responseCode)){
            responseCode = AppConstants.PSWResponseCodes.OK;
        }
        if(responseCode.equals(AppConstants.PSWResponseCodes.NO_DATA_FOUND)){
            status = HttpStatus.NO_CONTENT;
            message = messageBundle.getString("generic.no.content");
        }

        APIResponse response = buildAPIResponse(data, responseCode,new Message(responseCode, message)
                ,requestParameter,isPSWResponse);

        return CustomResponse
                .status(status)
                .body(response);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Message {
        private String code;
        private String description;
    }
    @NoArgsConstructor
    @Data
    public static class APIResponse {

        private String messageId;
        private String timestamp;
        private String senderId;
        private String receiverId;
        private String responseCode;
//        private String methodId;
        private String signature;
        private Message message;

        private Object data;

        public APIResponse(Object data, String responseCode, Message message, RequestParameter requestParameter) {
            this.data =  AppUtility.isEmpty(data)? "" : data;
            this.message = message;
            this.responseCode = responseCode;
            if(!AppUtility.isEmpty(requestParameter)) {
                this.messageId = requestParameter.getMessageId();
                this.timestamp = AppUtility.getCurrentTimeStamp().toString();
                this.senderId = AppConstants.AD_ID;//requestParameter.getReceiverId();
                this.receiverId = requestParameter.getSenderId();

                //     this.methodId = requestParameter.getMethodId();
                this.signature = requestParameter.getSignature();
            }
        }

        public APIResponse(Object data, String responseCode , Message message) {
            this.data = data;
            this.message = message;
            this.responseCode = responseCode;
        }

        public String toJson() throws JsonProcessingException {
            ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
            return objectWriter.writeValueAsString(this);
        }
    }

    @NoArgsConstructor
    @Data
    public static class PSWAPIResponse extends APIResponse {
        private String methodId;

        public PSWAPIResponse(Object data, String responseCode, Message message, RequestParameter requestParameter) {
            super(data, responseCode, message, requestParameter);
            this.methodId = requestParameter.getMethodId();
        }
    }

    public static APIResponse buildAPIResponse(Object data, String responseCode , Message message, RequestParameter requestParameter,boolean isPSWResponse) {
        APIResponse response = new APIResponse(data, responseCode , message,requestParameter );
        if(isPSWResponse) {
            response = new PSWAPIResponse(data, responseCode , message,requestParameter);
        }
        return response;
    }

    public static APIResponse buildAPIResponse(Object data, String responseCode , Message message) {
        APIResponse response = new APIResponse(data, responseCode,message);
        return response;
    }

    /**
     * Generate generic response for list data
     *
     * @param listOfEntities
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static <B, E> CustomResponse buildResponseList(List<E> listOfEntities) throws NoDataFoundException {
        if (!AppUtility.isEmpty(listOfEntities))
            return ResponseUtility.successResponse(listOfEntities,null, listOfEntities.size() + " Records Found!");
        throw new NoDataFoundException();
    }

    /**
     * Generate generic response for list data
     *
     * @param listOfEntities
     * @param baseObject
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static <B, E> CustomResponse buildResponseList(List<E> listOfEntities, BaseDTO<B, E> baseObject) throws CustomException, NoDataFoundException {
        if (!AppUtility.isEmpty(listOfEntities)) {
            List<B> data = new ArrayList<B>(listOfEntities.size());
            B bo;
            for (E obj : listOfEntities) {
                bo = baseObject.convertToNewDTO(obj, false);
                data.add(bo);
            }
            return ResponseUtility.successResponse(data, null,listOfEntities.size() + " Records Found!");
        }
        throw new NoDataFoundException();
    }

     public static <B, E> CustomResponse buildResponseObject(E entityObject) throws CustomException, NoDataFoundException {
        if (!AppUtility.isEmpty(entityObject))
            return ResponseUtility.successResponse(entityObject, null, "Valid Object");
        throw new NoDataFoundException();
    }

    /**
     * Generate generic response for single Object data
     */
    @SuppressWarnings("rawtypes")
    public static <B, E> CustomResponse buildResponseObject(E entityObject, BaseDTO<B, E> baseObject, boolean partialFill) throws CustomException, NoDataFoundException {
        if (!AppUtility.isEmpty(entityObject))
            return ResponseUtility.successResponse(baseObject.convertToNewDTO(entityObject, partialFill), null,"Valid Object");
        throw new NoDataFoundException();
    }

    /**
     * Generate generic response for single Optional Object data
     */
    @SuppressWarnings("rawtypes")
    public static <B, E> CustomResponse buildResponseObject(Optional<E> entityObject, BaseDTO<B, E> baseObject, boolean partialFill) throws CustomException, NoDataFoundException {
        return buildResponseObject(entityObject.get(),baseObject,partialFill);
    }


    /**
     * Exception Response
     * @param e
     * @param DBConstraint
     * @throws CustomException
     */
    public static void exceptionResponse(Exception e, String DBConstraint) throws CustomException {
        e.printStackTrace();
        log.error(e);
        if(!AppUtility.isEmpty(DBConstraint)) {
            ResponseUtility.validateDBConstraint(e, DBConstraint, "Data");
        } else if (e instanceof EmptyResultDataAccessException ){
            throw new NoDataFoundException(e.getMessage(), e);
        }
        throw new CustomException(e.getMessage(),e);
    }


    /**
     * Validates DB Constraints...
     *
     * @param exc
     * @param constraintName
     * @param value
     * @throws DBConstraintViolationException
     */
    public static void validateDBConstraint(Exception exc, String constraintName, String value) throws DBConstraintViolationException {
        if (exc instanceof DataIntegrityViolationException) {
            if (exc.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException constraintException = (ConstraintViolationException) exc.getCause();

                if (!AppUtility.isEmpty(constraintException.getConstraintName())) {

                    value = value + " already exist!";
                    if (constraintException.getCause() instanceof SQLIntegrityConstraintViolationException) {
                        SQLIntegrityConstraintViolationException integrityException = (SQLIntegrityConstraintViolationException) constraintException.getCause();
                        value = integrityException.getMessage();
                    }
                    throw new DBConstraintViolationException(value, exc);
                }
            }
        }
    }


    /*
     Generate created response
     This method will not be used locally

     @param data
     @return
   */
    @SuppressWarnings("rawtypes")
    public static CustomResponse createdResponse(Object data, String code, String responseMessage,
                                                 RequestParameter requestParameter) {

        String message = AppUtility.isEmpty(responseMessage)
                ? messageBundle.getString("generic.success")
                : responseMessage;

        return CustomResponse
                .status(HttpStatus.CREATED)
                .body(buildAPIResponse(data, ""+HttpStatus.CREATED.value(),
                        new Message(HttpStatus.CREATED.value()+"", message)
                        , requestParameter,false));
    }


//    public static <B, E> CustomResponse buildResponseObject(Optional<E> entityObject, BaseDTO<B, E> baseObject, int responseCode, String responseMsg, RequestParameter requestBody) {
//        if (!AppUtility.isEmpty(entityObject))
//            return ResponseUtility.createdResponse(baseObject.convertToNewDTO(entityObject.get(), false),""+responseCode,responseMsg,requestBody);
//        throw new NoDataFoundException();
//    }
//
    public static <B, E> CustomResponse buildResponseObject(E entityObject, BaseDTO<B, E> baseObject, int responseCode, String responseMsg, RequestParameter requestBody) {
        if (!AppUtility.isEmpty(entityObject))
            return ResponseUtility.createdResponse(baseObject.convertToNewDTO(entityObject,false),""+responseCode,responseMsg,requestBody);
        throw new NoDataFoundException();
    }

}
