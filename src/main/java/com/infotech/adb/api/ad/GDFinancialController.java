package com.infotech.adb.api.ad;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.dto.*;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.model.entity.AccountDetail;
import com.infotech.adb.model.entity.GDFinancial;
import com.infotech.adb.model.entity.LogRequest;
import com.infotech.adb.service.ADService;
import com.infotech.adb.service.AccountService;
import com.infotech.adb.service.GDFinancialService;
import com.infotech.adb.service.LogRequestService;
import com.infotech.adb.util.*;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/ad/gdf")
@Log4j2
@Api(tags = "reference")
public class GDFinancialController {

    @Autowired
    private ADService adService;

    @Autowired
    private LogRequestService logRequestService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private GDFinancialService gdFinancialService;


    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @RequestMapping(value = "/information", method = RequestMethod.POST)
    public CustomResponse verifyAccount(HttpServletRequest request,
                                        @RequestBody RequestParameter<GDFinancialDTO> requestBody)
            throws CustomException, DataValidationException, NoDataFoundException, JsonProcessingException {

        ZonedDateTime requestTime = ZonedDateTime.now();
        // TODO validate request common parameters
        if (AppUtility.isEmpty(requestBody.getMessageId())) {
            throw new DataValidationException(messageBundle.getString("id.not.found"));
        }
        GDFinancialDTO gdFinancialDTO = null;

        try {
            GDFinancialDTO requestData = requestBody.getData();
            GDFinancial newGDFinancial = new GDFinancial();
            newGDFinancial.setGdNumber(requestData.getGDNumber());
            newGDFinancial.setGdfObjectJson(JsonUtils.objectToJson(requestData));
            newGDFinancial.setCreatedOn(ZonedDateTime.now());
            gdFinancialService.createGDFinancial(newGDFinancial);
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e, null);
        }

        CustomResponse customResponse = ResponseUtility.createdResponse(null,200,
                messageBundle.getString("gd.financial.updated"), requestBody);

        ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();

        saveLogRequest("MSG1: Verify Profile", RequestMethod.POST.name(), requestBody, requestTime, responseBody);
        return customResponse;
    }


    @RequestMapping(value = "/get/information", method = RequestMethod.POST)
    public CustomResponse getAccountDetails(HttpServletRequest request,
                                            @RequestBody RequestParameter<IBANVerificationRequest> requestBody)
            throws CustomException, DataValidationException, NoDataFoundException, JsonProcessingException {

        ZonedDateTime requestTime = ZonedDateTime.now();
        // MEthodId is gdNumber
        GDFinancial gdFinancial = gdFinancialService.getGDFinancialByGDNumber(requestBody.getMethodId());
        GDFinancialDTO gdFinancialDTO = JsonUtils.jsonToObject(gdFinancial.getGdfObjectJson(), GDFinancialDTO.class);
        CustomResponse customResponse = ResponseUtility.createdResponse(gdFinancialDTO, 200,
                gdFinancial == null ? messageBundle.getString("account.details.not.shared") :
                        messageBundle.getString("account.details.shared"), requestBody);

        ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();

        saveLogRequest("MSG2: Account Details", RequestMethod.POST.name(), requestBody, requestTime, responseBody);
        return customResponse;
    }

    private AccountDetail getAccountDetail(@RequestBody RequestParameter<IBANVerificationRequest> requestBody) throws DataValidationException, CustomException {
        // TODO validate request common parameters
        if (AppUtility.isEmpty(requestBody.getMessageId())) {
            throw new DataValidationException(messageBundle.getString("id.not.found"));
        }
        AccountDetail accountDetail = null;
        try {
            accountDetail = adService.getAccountByIban(requestBody.getData());
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e, null);
        }
        return accountDetail;
    }

    private void saveLogRequest(String messageName, String messageType, RequestParameter requestBody,
                                ZonedDateTime requestTime, ResponseUtility.APIResponse responseBody) throws JsonProcessingException {
        LogRequest logRequest = new LogRequest();
        logRequest.setMsgIdentifier(messageName);
        logRequest.setRequestMethod(messageType);
        logRequest.setRequestPayload(requestBody.toJson());
        logRequest.setResponsePayload(responseBody.toJson());
        logRequest.setRequestTime(requestTime);
        logRequest.setResponseTime(ZonedDateTime.now());
        logRequest.setCreatedOn(ZonedDateTime.now());
        logRequest.setResponseCode(responseBody.getMessage().getCode());
        logRequest.setResponseMessage(responseBody.getMessage().getDescription());
        logRequestService.createLogRequest(logRequest);
    }

    private void saveLogRequest(String messageName, String messageType, ResponseUtility.APIResponse requestBody,
                                ZonedDateTime requestTime, ResponseUtility.APIResponse responseBody) throws JsonProcessingException {
        LogRequest logRequest = new LogRequest();
        logRequest.setMsgIdentifier(messageName);
        logRequest.setRequestMethod(messageType);
        logRequest.setRequestPayload(requestBody.toJson());
        logRequest.setResponsePayload(responseBody.toJson());
        logRequest.setRequestTime(requestTime);
        logRequest.setResponseTime(ZonedDateTime.now());
        logRequest.setCreatedOn(ZonedDateTime.now());
        logRequest.setResponseCode(responseBody.getMessage().getCode());
        logRequest.setResponseMessage(responseBody.getMessage().getDescription());
        logRequestService.createLogRequest(logRequest);
    }
}