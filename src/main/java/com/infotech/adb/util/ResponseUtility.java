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
import java.sql.Timestamp;
import java.util.*;

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
        return successResponse(data,responseCode,responseMessage, null);
    }
    @SuppressWarnings("rawtypes")
    public static CustomResponse successResponse(Object data, String responseCode, String responseMessage,RequestParameter requestParameter) {

        if(AppUtility.isEmpty(responseCode)){
            responseCode = AppConstants.PSWResponseCodes.OK;
        }

        return CustomResponse
                .status(HttpStatus.OK)
                .body(buildAPIResponse(data, responseCode,
                        AppUtility.isEmpty(responseMessage)
                                ? messageBundle.getString("generic.success")
                                : responseMessage
                        ,requestParameter
                ));
    }


    @NoArgsConstructor
    @Data
    public static class APIResponse {

        private UUID messageId;
        private Timestamp timestamp;
        private String senderId;
        private String receiverId;
        private String responseCode;
        private String methodId;
        private String signature;
        private String message;

        private Object data;

        public APIResponse(Object data, String responseCode, String message, RequestParameter requestParameter) {
            this.data = data;
            this.message = message;
            this.responseCode = responseCode;
            this.messageId = requestParameter.getMessageId();
            this.timestamp = requestParameter.getTimestamp();
            this.senderId = requestParameter.getSenderId();
            this.receiverId = requestParameter.getReceiverId();

            this.methodId = requestParameter.getMethodId();
            this.signature = requestParameter.getSignature();
        }

        public APIResponse(Object data, String responseCode , String message) {
            this.data = data;
            this.message = message;
            this.responseCode = responseCode;
        }

        public String toJson() throws JsonProcessingException {
            ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
            return objectWriter.writeValueAsString(this);
        }
    }

    public static APIResponse buildAPIResponse(Object data, String responseCode , String message, RequestParameter requestParameter) {
        APIResponse response = new APIResponse(data, responseCode , message,requestParameter );
        return response;
    }

    public static APIResponse buildAPIResponse(Object data, String responseCode , String message) {
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
        } else if (e instanceof EmptyResultDataAccessException){
            throw new NoDataFoundException("No Data Found.", e);
        }
        throw new CustomException(e);
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
        return CustomResponse
                .status(HttpStatus.CREATED)
                .body(buildAPIResponse(data, ""+HttpStatus.CREATED.value(),
                        AppUtility.isEmpty(responseMessage)? messageBundle.getString("generic.success"): responseMessage
                        , requestParameter));
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
