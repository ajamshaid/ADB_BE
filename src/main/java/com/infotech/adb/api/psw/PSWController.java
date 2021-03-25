package com.infotech.adb.api.psw;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.dto.RequestParameter;
import com.infotech.adb.model.entity.LogRequest;
import com.infotech.adb.service.LogRequestService;
import com.infotech.adb.util.CustomResponse;
import com.infotech.adb.util.ResponseUtility;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/psw")
@Log4j2
@Api(tags = "PSW")
public class PSWController {

    @Autowired
    private LogRequestService logRequestService;

    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @RequestMapping(value = "/update/payment/modes", method = RequestMethod.POST)
    public CustomResponse getAccountDetails(HttpServletRequest request,
                                            @RequestBody RequestParameter requestBody) {

        return getCustomResponse(requestBody, messageBundle.getString("account.details.shared"));
    }

    @RequestMapping(value = "/update/negative/countries", method = RequestMethod.POST)
    public CustomResponse getNegativeCountriesList(HttpServletRequest request,
                                            @RequestBody RequestParameter requestBody) {

        return getCustomResponse(requestBody, messageBundle.getString("negative.countries.shared"));
    }

    @RequestMapping(value = "/update/negative/commodities", method = RequestMethod.POST)
    public CustomResponse getNegativeCommoditiesList(HttpServletRequest request,
                                                   @RequestBody RequestParameter requestBody) {

        return getCustomResponse(requestBody, messageBundle.getString("negative.commodities.shared"));
    }

    @RequestMapping(value = "/update/negative/suppliers", method = RequestMethod.POST)
    public CustomResponse getNegativeSuppliersList(HttpServletRequest request,
                                                     @RequestBody RequestParameter requestBody) {

        return getCustomResponse(requestBody, messageBundle.getString("negative.suppliers.shared"));
    }

    @RequestMapping(value = "/bca/information", method = RequestMethod.POST)
    public CustomResponse bcaInformation(HttpServletRequest request,
                                                   @RequestBody RequestParameter requestBody) {

        return getCustomResponse(requestBody, messageBundle.getString("bca.information.updated"));
    }


    @RequestMapping(value = "/financial/transaction", method = RequestMethod.POST)
    public CustomResponse financialTransaction(HttpServletRequest request,
                                         @RequestBody RequestParameter requestBody) {

        return getCustomResponse(requestBody, messageBundle.getString("negative.suppliers.shared"));
    }

    //    5.2.3. Message 3 – Sharing of BCA Information

    private CustomResponse getCustomResponse(RequestParameter requestBody, String message) {
        return ResponseUtility.createdResponse(null, 200, message, requestBody);
    }

    private void saveLogRequest(String messageName, String messageType, RequestParameter requestBody, ZonedDateTime requestTime, ResponseUtility.APIResponse responseBody) throws JsonProcessingException {
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
