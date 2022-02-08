package com.infotech.adb.api;

import com.infotech.adb.dto.LogRequestDTO;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.model.entity.LogRequest;
import com.infotech.adb.service.LogRequestService;
import com.infotech.adb.util.AppUtility;
import com.infotech.adb.util.CustomResponse;
import com.infotech.adb.util.ResponseUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/logrequest")
@Log4j2
public class LogRequestAPI {

    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    private LogRequestService logRequestService;


    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public CustomResponse searchLogs(@RequestParam(value = "iban", required = false) String iban ,
                                     @RequestParam(value = "msgId", required = false) String msgId,
                                     @RequestParam(value = "fromDate", required = false) String fromDate,
                                     @RequestParam(value = "toDate", required = false) String toDate)
            throws CustomException, DataValidationException, NoDataFoundException {
        log.info("searchLogs API initiated...");

        List<LogRequest> logRequests = null;
        try {
            logRequests = logRequestService.searchLogs(iban,msgId, fromDate, toDate);
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e, null);
        }
        return ResponseUtility.buildResponseList(logRequests, new LogRequestDTO());
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public CustomResponse getAllLogRequests(HttpServletRequest request,
                                      @RequestParam(value = "status", required = false) Boolean status)
            throws CustomException, NoDataFoundException {

        List<LogRequest> logRequests = null;
        try {
            logRequests = logRequestService.getAllLogRequests(status);
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e, null);
        }
        return ResponseUtility.buildResponseList(logRequests, new LogRequestDTO());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CustomResponse getLogRequestById(HttpServletRequest request,
                                      @PathVariable Long id)
            throws CustomException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(id)) {
            throw new DataValidationException(messageBundle.getString("id.not.found"));
        }
        Optional<LogRequest> logRequest = null;
        try {
            logRequest = logRequestService.getLogRequestById(id);
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e, null);
        }
        return ResponseUtility.buildResponseObject(logRequest, new LogRequestDTO(), false);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public CustomResponse createLogRequest(HttpServletRequest request,
                                     @RequestBody LogRequestDTO logRequestRequest)
            throws CustomException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(logRequestRequest) || !AppUtility.isEmpty(logRequestRequest.getId())) {
            throw new DataValidationException(messageBundle.getString("validation.error"));
        }
        LogRequest logRequest = null;
        try {
            logRequest = logRequestService.createLogRequest(logRequestRequest.convertToEntity());
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e,null);
        }
        return ResponseUtility.buildResponseObject(logRequest);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public CustomResponse updateLogRequest(HttpServletRequest request,
                                     @RequestBody LogRequestDTO logRequestRequest)
            throws CustomException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(logRequestRequest) || AppUtility.isEmpty(logRequestRequest.getId())) {
            throw new DataValidationException(messageBundle.getString("validation.error"));
        }
        LogRequest logRequest = null;
        try {
            logRequest = logRequestService.updateLogRequest(logRequestRequest.convertToEntity());
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e, null);
        }
        return ResponseUtility.buildResponseObject(logRequest, new LogRequestDTO(), false);
    }
 }
