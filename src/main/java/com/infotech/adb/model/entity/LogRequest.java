package com.infotech.adb.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.dto.RequestParameter;
import com.infotech.adb.util.AppUtility;
import com.infotech.adb.util.ResponseUtility;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "LOG_REQUEST")
@JsonIgnoreProperties(ignoreUnknown = true)
public class LogRequest {

    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "RESPONSE_MESSAGE", nullable = false)
    private String responseMessage;
    @Column(name = "RESPONSE_CODE", nullable = false)
    private String responseCode;

    @Column(name = "MSG_IDENTIFIER", nullable = false)
    private String msgIdentifier;

    @Column(name = "REQUEST_METHOD", nullable = false)
    private String requestMethod;

    @Column(name = "REQUEST_PAYLOAD", length = 4000)
    private String requestPayload;

    @Column(name = "RESPONSE_PAYLOAD", length = 4000)
    private String responsePayload;

    @Column(name = "REQUEST_TIME", columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private Date requestTime;

    @Column(name = "RESPONSE_TIME", columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private Date responseTime;

    @Column(name = "SENDER_ID", nullable = false)
    private String senderId;

    @Column(name = "RECEIVER_ID", nullable = false)
    private String receiverId;

    public LogRequest() {
    }
    public static LogRequest buildNewObject(String messageName, String messageType, RequestParameter requestBody
            ,Date requestTime ,ResponseUtility.APIResponse apiResponse){
            LogRequest logRequest = new LogRequest();
            logRequest.setMsgIdentifier(messageName);
            logRequest.setReceiverId(requestBody.getReceiverId());
            logRequest.setSenderId(requestBody.getSenderId());
            logRequest.setRequestMethod(messageType);
            logRequest.setRequestTime(requestTime);
            logRequest.setResponseTime(AppUtility.getCurrentTimeStamp());
            logRequest.setResponseCode(apiResponse.getMessage().getCode());
            logRequest.setResponseMessage(apiResponse.getMessage().getDescription());
        try {
            logRequest.setRequestPayload(requestBody.toJson());
            logRequest.setResponsePayload(apiResponse.toJson());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return  logRequest;
    }
}