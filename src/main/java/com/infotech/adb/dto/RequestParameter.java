package com.infotech.adb.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.util.AppUtility;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ResourceBundle;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class RequestParameter<T> {

    private UUID messageId;
    private Timestamp timestamp;
    private String senderId;
    private String receiverId;
    private String processingCode;
    private String methodId;
    private String signature;

    private T data;


    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    public String toJson() throws JsonProcessingException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(this);
    }

    public RequestParameter newRequestParameter() {
        RequestParameter newRParameter = new RequestParameter();
        newRParameter.messageId = this.messageId;
        newRParameter.timestamp = this.timestamp;
        newRParameter.senderId = this.senderId;
        newRParameter.receiverId = this.receiverId;
        newRParameter.processingCode = this.processingCode;
        newRParameter.methodId = this.methodId;
        newRParameter.signature = this.signature;
        return newRParameter;
    }

    public static boolean isValidRequest(RequestParameter requestParameter,boolean ibanOnly) throws DataValidationException {
        boolean isValid = true;
        StringBuffer sb = new StringBuffer("");
        if (AppUtility.isEmpty(requestParameter.getMessageId())) {
            sb.append(" Message ID " + messageBundle.getString("missing.request.parameter"));
            isValid = false;
        }
        if (AppUtility.isEmpty(requestParameter.getTimestamp())) {
            sb.append(" TimeStamp " + messageBundle.getString("missing.request.parameter"));
            isValid = false;
        }
        if (AppUtility.isEmpty(requestParameter.getSenderId())) {
            sb.append(" Sender ID " + messageBundle.getString("missing.request.parameter"));
            isValid = false;
        }
        if (AppUtility.isEmpty(requestParameter.getReceiverId())) {
            sb.append(" Receiver ID " + messageBundle.getString("missing.request.parameter"));
            isValid = false;
        }
        if (AppUtility.isEmpty(requestParameter.getSignature())) {
            sb.append(" Signature " + messageBundle.getString("missing.request.parameter"));
            isValid = false;
        }
        if (AppUtility.isEmpty(requestParameter.getProcessingCode())) {
            sb.append(" Processing Code " + messageBundle.getString("missing.request.parameter"));
            isValid = false;
        }
        if (requestParameter.data instanceof IBANVerificationRequest) {
            IBANVerificationRequest data = (IBANVerificationRequest) requestParameter.data;

            if (AppUtility.isEmpty(data.getIban())) {
                sb.append(" IBAN " + messageBundle.getString("missing.request.parameter"));
                isValid = false;
            }
            if(!ibanOnly) {
                if (AppUtility.isEmpty(data.getNtn())) {
                    sb.append(" NTN " + messageBundle.getString("missing.request.parameter"));
                    isValid = false;
                }
                if (AppUtility.isEmpty(data.getEmail())) {
                    sb.append(" Email " + messageBundle.getString("missing.request.parameter"));
                    isValid = false;
                }
                if (AppUtility.isEmpty(data.getMobileNumber())) {
                    sb.append(" Mobile Number  " + messageBundle.getString("missing.request.parameter"));
                    isValid = false;
                }
            }
        }

        if (!isValid) {
            throw new DataValidationException(sb.toString());
        }
        return isValid;
    }
}
