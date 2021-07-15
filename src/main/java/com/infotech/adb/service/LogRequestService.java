package com.infotech.adb.service;

import com.infotech.adb.dto.RequestParameter;
import com.infotech.adb.model.entity.LogRequest;
import com.infotech.adb.model.repository.LogRequestRepository;
import com.infotech.adb.util.AppUtility;
import com.infotech.adb.util.ResponseUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
//@Transactional(value = "masterTransactionManager", rollbackFor = {Throwable.class})
@Log4j2
public class LogRequestService {

    @Autowired
    private LogRequestRepository logRequestRepository;

    public void saveLogRequest(String messageName, String messageType, RequestParameter requestBody, Date requestTime, ResponseUtility.APIResponse apiResponse) {


        LogRequest logRequest = LogRequest.buildNewObject(messageName, RequestMethod.POST.name(), requestBody, requestTime, apiResponse);;
        System.out.println("Going to save Log Request :"+logRequest);
   //     System.out.println("-------Saving Log object to DB is disabled for testing-----");
        this.createLogRequest(logRequest);
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
        return logRequestRepository.save(logRequest);
    }

    public void deleteLogRequestById(Long id) {
        log.info("deleteLogRequestById method called..");

        logRequestRepository.deleteById(id);
    }
}
