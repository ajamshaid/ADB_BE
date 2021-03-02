package com.infotech.adb.api.psw;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.dto.AccountDetailDTO;
import com.infotech.adb.dto.RequestParameter;
import com.infotech.adb.dto.RestrictedCommoditiesDTO;
import com.infotech.adb.dto.RestrictedCountiesDTO;
import com.infotech.adb.dto.RestrictedSuppliersDTO;
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
@Api(tags = "reference")
public class PSWController {

    @Autowired
    private LogRequestService logRequestService;

    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @RequestMapping(value = "/update/paymentmodes", method = RequestMethod.POST)
    public CustomResponse getAccountDetails(HttpServletRequest request,
                                            @RequestBody RequestParameter<AccountDetailDTO> requestBody) {

        CustomResponse customResponse = ResponseUtility.createdResponse(null, 200,
                messageBundle.getString("payment.modes.updated"), requestBody);

        return customResponse;
    }

    @RequestMapping(value = "/update/negative/countries", method = RequestMethod.POST)
    public CustomResponse getNegativeCountriesList(HttpServletRequest request,
                                            @RequestBody RequestParameter<RestrictedCountiesDTO> requestBody) {

        CustomResponse customResponse = ResponseUtility.createdResponse(null, 200,
                messageBundle.getString("negative.countries.updated"), requestBody);

        return customResponse;
    }

    @RequestMapping(value = "/account/negative/commodities", method = RequestMethod.POST)
    public CustomResponse getNegativeCommoditiesList(HttpServletRequest request,
                                                   @RequestBody RequestParameter<RestrictedCommoditiesDTO> requestBody) {

        CustomResponse customResponse = ResponseUtility.createdResponse(null, 200,
                messageBundle.getString("negative.commodities.updated"), requestBody);

        return customResponse;
    }

    @RequestMapping(value = "/account/negative/suppliers", method = RequestMethod.POST)
    public CustomResponse getNegativeSuppliersList(HttpServletRequest request,
                                                     @RequestBody RequestParameter<RestrictedSuppliersDTO> requestBody) {

        CustomResponse customResponse = ResponseUtility.createdResponse(null, 200,
                messageBundle.getString("negative.suppliers.updated"), requestBody);

        return customResponse;
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