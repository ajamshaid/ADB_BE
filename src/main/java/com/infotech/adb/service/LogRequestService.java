package com.infotech.adb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.dto.RequestParameter;
import com.infotech.adb.model.entity.LogRequest;
import com.infotech.adb.model.repository.LogRequestRepository;
import com.infotech.adb.util.AppUtility;
import com.infotech.adb.util.ResponseUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
//@Transactional(value = "masterTransactionManager", rollbackFor = {Throwable.class})
@Log4j2
public class LogRequestService {

    @Autowired
    private LogRequestRepository logRequestRepository;


    public void saveLogRequest(String messageName, String messageType, RequestParameter requestBody, ResponseUtility.APIResponse responseBody) {
        LogRequest logRequest = new LogRequest();
        logRequest.setReceiverId(requestBody.getReceiverId());
        logRequest.setSenderId(requestBody.getSenderId());
        logRequest.setMsgIdentifier(messageName);
        logRequest.setRequestMethod(messageType);
        try {
            logRequest.setRequestPayload(requestBody.toJson());
            logRequest.setResponsePayload(responseBody.toJson());
        } catch (JsonProcessingException e) {
            log.error("-- LogRequest without payload's will be saved as error occurred while parsing Request/Response payload :" + e.getMessage());
            e.printStackTrace();
        }
        logRequest.setRequestTime(ZonedDateTime.now());
        logRequest.setResponseTime(ZonedDateTime.now());
        logRequest.setCreatedOn(ZonedDateTime.now());
        logRequest.setResponseCode(responseBody.getResponseCode());
        logRequest.setResponseMessage(responseBody.getMessage().getDescription());
   //     System.out.println("Going to save Log Request :"+logRequest);
        System.out.println("-------Saving Log object to DB is disabled for testing-----");
   //     this.createLogRequest(logRequest);
    }

    public List<LogRequest> getAllLogRequests(Boolean isSuspended) {
        log.info("getAllLogRequests method called..");
        if (AppUtility.isEmpty(isSuspended)) {
            return logRequestRepository.findAllByOrderByIdDesc();
        } else {
            return logRequestRepository.findAll();
        }
    }

    public Optional<LogRequest> getLogRequestById(Long id) {
        log.info("getLogRequestById method called..");
        if (!AppUtility.isEmpty(id)) {
            return logRequestRepository.findById(id);
        }
        return Optional.empty();
    }

    public LogRequest createLogRequest(LogRequest logRequest) {
        log.info("createLogRequest method called..");
        return logRequestRepository.save(logRequest);
    }

    public LogRequest updateLogRequest(LogRequest logRequest) {
        log.info("updateLogRequest method called..");
        logRequest.setUpdatedOn(ZonedDateTime.now());
        return logRequestRepository.save(logRequest);
    }

    public void deleteLogRequestById(Long id) {
        log.info("deleteLogRequestById method called..");

        logRequestRepository.deleteById(id);
    }
}
