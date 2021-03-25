package com.infotech.adb.dto;

import com.infotech.adb.model.entity.LogRequest;
import com.infotech.adb.util.AppUtility;
import com.infotech.adb.util.JsonUtils;
import com.infotech.adb.util.ResponseUtility;
import lombok.Data;

import javax.persistence.Column;
import java.time.ZonedDateTime;

@Data
public class LogRequestDTO implements BaseDTO<LogRequestDTO, LogRequest> {

    private Long id;

    private String senderId;
    private String receiverId;
    private ZonedDateTime createdOn;
    private String messageName;
    private RequestParameter requestPayload;
    private ResponseUtility.APIResponse responsePayload;
    private String requestMethod;
    private ZonedDateTime requestTime;
    private ZonedDateTime responseTime;
    private String requestFormattedTime;

    public LogRequestDTO() {
    }

    public LogRequestDTO(Long id) {
        this.id = id;
    }

    @Override
    public LogRequest convertToEntity() {
        LogRequest logRequest = new LogRequest();
        logRequest.setId(this.id);
        logRequest.setResponseTime(this.responseTime);
        logRequest.setRequestTime(this.requestTime);
        logRequest.setMsgIdentifier(this.messageName);
        logRequest.setCreatedOn(this.createdOn);
        logRequest.setRequestMethod(this.requestMethod);
        return logRequest;
    }

    @Override
    public void convertToDTO(LogRequest entity, boolean partialFill) {
        this.id = entity.getId();
        this.senderId = entity.getSenderId();
        this.receiverId = entity.getReceiverId();
        this.responsePayload = JsonUtils.jsonToObject(entity.getResponsePayload(), ResponseUtility.APIResponse.class);
        this.requestPayload = JsonUtils.jsonToObject(entity.getRequestPayload(), RequestParameter.class);
        this.responseTime = entity.getResponseTime();
        this.requestTime = entity.getRequestTime();
        this.messageName = entity.getMsgIdentifier();
        this.createdOn = entity.getCreatedOn();
        this.requestMethod = entity.getRequestMethod();
        this.requestFormattedTime = AppUtility.formatZonedDateTime("HH:mm a", entity.getRequestTime());
    }

    @Override
    public LogRequestDTO convertToNewDTO(LogRequest logRequest, boolean partialFill) {
        LogRequestDTO logRequestDTO = new LogRequestDTO();
        logRequestDTO.convertToDTO(logRequest, partialFill);
        return logRequestDTO;
    }
}