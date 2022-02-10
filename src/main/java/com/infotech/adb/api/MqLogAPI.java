package com.infotech.adb.api;

import com.infotech.adb.dto.MqLogDTO;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.model.entity.MqLog;
import com.infotech.adb.service.MQServices;
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
@RequestMapping("/mqlog")
@Log4j2
public class MqLogAPI {

    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    private MQServices mqService;


    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public CustomResponse searchLogs(@RequestParam(value = "message", required = false) String message ,
                                     @RequestParam(value = "msgType", required = false) String msgType,
                                     @RequestParam(value = "fromDate", required = false) String fromDate,
                                     @RequestParam(value = "toDate", required = false) String toDate)
            throws CustomException, DataValidationException, NoDataFoundException {
        log.info("searchLogs API initiated...");

        List<MqLog> mqLogs = null;
        try {
            mqLogs = mqService.searchLogs(message,msgType, fromDate, toDate);
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e, null);
        }
        return ResponseUtility.buildResponseList(mqLogs, new MqLogDTO());
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public CustomResponse getAllLogRequests(HttpServletRequest request,
                                      @RequestParam(value = "status", required = false) Boolean status)
            throws CustomException, NoDataFoundException {

        List<MqLog> mqLogs = null;
        try {
            mqLogs = mqService.getAllMqLog(status);
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e, null);
        }
        return ResponseUtility.buildResponseList(mqLogs, new MqLogDTO());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CustomResponse getLogRequestById(HttpServletRequest request,
                                      @PathVariable Long id)
            throws CustomException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(id)) {
            throw new DataValidationException(messageBundle.getString("id.not.found"));
        }
        Optional<MqLog> mqLogs = null;
        try {
            mqLogs = mqService.getMqLogById(id);
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e, null);
        }
        return ResponseUtility.buildResponseObject(mqLogs, new MqLogDTO(), false);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public CustomResponse createLogRequest(HttpServletRequest request,
                                     @RequestBody MqLogDTO mqLogDTO)
            throws CustomException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(mqLogDTO) || !AppUtility.isEmpty(mqLogDTO.getId())) {
            throw new DataValidationException(messageBundle.getString("validation.error"));
        }
        MqLog mqLog = null;
        try {
            mqLog = mqService.createMqLog(mqLogDTO.convertToEntity());
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e,null);
        }
        return ResponseUtility.buildResponseObject(mqLog);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public CustomResponse updateLogRequest(HttpServletRequest request,
                                     @RequestBody MqLogDTO mqLogDTO)
            throws CustomException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(mqLogDTO) || AppUtility.isEmpty(mqLogDTO.getId())) {
            throw new DataValidationException(messageBundle.getString("validation.error"));
        }
        MqLog mqLog = null;
        try {
            mqLog = mqService.updateMqLog(mqLogDTO.convertToEntity());
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e, null);
        }
        return ResponseUtility.buildResponseObject(mqLog, new MqLogDTO(), false);
    }
 }
