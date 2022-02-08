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

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
//@Transactional(value = "masterTransactionManager", rollbackFor = {Throwable.class})
@Log4j2
public class LogRequestService {

    @Autowired
    private LogRequestRepository logRequestRepository;

    @Autowired
    private EntityManager em;

    public void saveLogRequest(String messageName, String messageType, RequestParameter requestBody, Date requestTime, ResponseUtility.APIResponse apiResponse) {


        LogRequest logRequest = LogRequest.buildNewObject(messageName, RequestMethod.POST.name(), requestBody, requestTime, apiResponse);
        ;
        System.out.println("Going to save Log Request :" + logRequest.getResponseMessage());
        //     System.out.println("-------Saving Log object to DB is disabled for testing-----");
        this.createLogRequest(logRequest);
    }


    public List<LogRequest> searchLogs(String Iban, String msgId, String fromDate, String toDate) throws ParseException {
        log.info("searchLogs method called..");
        if (AppUtility.isEmpty(msgId)) {
            msgId = "%";
        }
        Date date1 = null, date2 = null;

        if (!AppUtility.isEmpty(fromDate)) {
            date1 = new SimpleDateFormat("dd-MM-yyyy").parse(fromDate);
        } else {
            date1 = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1970");
        }
        if (!AppUtility.isEmpty(toDate)) {
            date2 = new SimpleDateFormat("dd-MM-yyyy").parse(toDate);
        } else {
            date2 = new Date();
        }
        return logRequestRepository.searchLogs(Iban, msgId, date1, date2);
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
