package com.infotech.adb.dto;

import com.infotech.adb.model.entity.LogRequest;
import com.infotech.adb.util.AppUtility;
import com.infotech.adb.util.JsonUtils;
import com.infotech.adb.util.ResponseUtility;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Date;

@Data
public class LogRequestDTO implements BaseDTO<LogRequestDTO, LogRequest> {

    private Long id;

    private String senderId;
    private String receiverId;
    private String methodId;
    private ZonedDateTime createdOn;
    private String messageName;
    private RequestParameter requestPayload;
    private ResponseUtility.APIResponse responsePayload;
    private String responseCode;
    private String requestMethod;
    private String responseMessage;

    private Date requestTime;
    private Date responseTime;

    public LogRequestDTO() {
    }

    public LogRequestDTO(Long id) {
        this.id = id;
    }

    @Override
    public LogRequest convertToEntity() {
        LogRequest logRequest = new LogRequest();
        logRequest.setId(this.id);
//        logRequest.setResponseTime(this.responseTime);
//        logRequest.setRequestTime(this.requestTime);
        logRequest.setMsgIdentifier(this.messageName);
        logRequest.setRequestMethod(this.requestMethod);
        return logRequest;
    }

    @Override
    public void convertToDTO(LogRequest entity, boolean partialFill) {
        this.id = entity.getId();
        this.senderId = entity.getSenderId();
        this.receiverId = entity.getReceiverId();

        this.responseCode = entity.getResponseCode();
        this.responseMessage = entity.getResponseMessage();

        this.responseTime = entity.getResponseTime();
        this.requestTime = entity.getRequestTime();
        this.messageName = entity.getMsgIdentifier();
        this.requestMethod = entity.getRequestMethod();
//        this.requestFormattedTime = AppUtility.formatZonedDateTime("HH:mm a", entity.getRe);
        if(!AppUtility.isEmpty(entity.getResponsePayload()))
            this.responsePayload = JsonUtils.jsonToObject(entity.getResponsePayload(), ResponseUtility.PSWAPIResponse.class);
        if(!AppUtility.isEmpty(entity.getRequestPayload()))
        this.requestPayload = JsonUtils.jsonToObject(entity.getRequestPayload(), RequestParameter.class);
    }

    @Override
    public LogRequestDTO convertToNewDTO(LogRequest logRequest, boolean partialFill) {
        LogRequestDTO logRequestDTO = new LogRequestDTO();
        logRequestDTO.convertToDTO(logRequest, partialFill);
        return logRequestDTO;
    }
}