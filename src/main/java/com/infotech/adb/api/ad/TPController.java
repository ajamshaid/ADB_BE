package com.infotech.adb.api.ad;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.dto.AccountDetailDTO;
import com.infotech.adb.dto.IBANVerificationRequest;
import com.infotech.adb.dto.RequestParameter;
import com.infotech.adb.dto.RestrictedCommoditiesDTO;
import com.infotech.adb.dto.RestrictedCountiesDTO;
import com.infotech.adb.dto.RestrictedSuppliersDTO;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.model.entity.AccountDetail;
import com.infotech.adb.model.entity.LogRequest;
import com.infotech.adb.service.ADService;
import com.infotech.adb.service.AccountService;
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
@RequestMapping("/ad")
@Log4j2
@Api(tags = "reference")
public class TPController {

    @Autowired
    private ADService adService;

    @Autowired
    private LogRequestService logRequestService;

    @Autowired
    private AccountService accountService;

    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @RequestMapping(value = "/verify/account", method = RequestMethod.POST)
    public CustomResponse verifyAccount(HttpServletRequest request,
                                        @RequestBody RequestParameter<IBANVerificationRequest> requestBody)
            throws CustomException, DataValidationException, NoDataFoundException, JsonProcessingException {

        ZonedDateTime requestTime = ZonedDateTime.now();
        // TODO validate request common parameters
        if (AppUtility.isEmpty(requestBody.getMessageId())) {
            throw new DataValidationException(messageBundle.getString("id.not.found"));
        }
        Optional<AccountDetail> accountDetail = null;
        try {
            IBANVerificationRequest requestData = requestBody.getData();
            accountDetail = accountService.getAccountByIbanAndEmailAndMobileNumber(requestData.getIban(), requestData.getEmail(), requestData.getMobileNumber());
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e, null);
        }
        CustomResponse customResponse = ResponseUtility.createdResponse(null, accountDetail.isPresent() ? 207 : 208,
                accountDetail.isPresent() ? messageBundle.getString("account.verified") :
                        messageBundle.getString("account.not.verified"), requestBody);

        ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();

        saveLogRequest("MSG1: Verify Profile", RequestMethod.POST.name(), requestBody, requestTime, responseBody);
        return customResponse;
    }


    @RequestMapping(value = "/account/detail", method = RequestMethod.POST)
    public CustomResponse getAccountDetails(HttpServletRequest request,
                                            @RequestBody RequestParameter<IBANVerificationRequest> requestBody)
            throws CustomException, DataValidationException, NoDataFoundException, JsonProcessingException {

        ZonedDateTime requestTime = ZonedDateTime.now();

        // TODO validate request common parameters
        AccountDetail accountDetail = getAccountDetail(requestBody);
        CustomResponse customResponse = ResponseUtility.buildResponseObject(accountDetail, new AccountDetailDTO(), 200,
                accountDetail == null ? messageBundle.getString("account.details.not.shared") :
                        messageBundle.getString("account.details.shared"), requestBody);

        ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();

        saveLogRequest("MSG2: Account Details", RequestMethod.POST.name(), requestBody, requestTime, responseBody);
        return customResponse;
    }

    @RequestMapping(value = "/account/negative/countries", method = RequestMethod.POST)
    public CustomResponse getNegativeCountriesList(HttpServletRequest request,
                                            @RequestBody RequestParameter<IBANVerificationRequest> requestBody)
            throws CustomException, DataValidationException, NoDataFoundException, JsonProcessingException {

        ZonedDateTime requestTime = ZonedDateTime.now();

        // TODO validate request common parameters
        AccountDetail accountDetail = getAccountDetail(requestBody);
        CustomResponse customResponse = ResponseUtility.buildResponseObject(accountDetail, new RestrictedCountiesDTO(), 200,
                accountDetail == null ? messageBundle.getString("negative.countries.not.shared") :
                        messageBundle.getString("negative.countries.shared"), requestBody);

        ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();

        saveLogRequest("MSG3: Negative List Countries", RequestMethod.POST.name(), requestBody, requestTime, responseBody);
        return customResponse;
    }

    @RequestMapping(value = "/account/negative/commodities", method = RequestMethod.POST)
    public CustomResponse getNegativeCommoditiesList(HttpServletRequest request,
                                                   @RequestBody RequestParameter<IBANVerificationRequest> requestBody)
            throws CustomException, DataValidationException, NoDataFoundException, JsonProcessingException {

        ZonedDateTime requestTime = ZonedDateTime.now();

        // TODO validate request common parameters
        AccountDetail accountDetail = getAccountDetail(requestBody);
        CustomResponse customResponse = ResponseUtility.buildResponseObject(accountDetail, new RestrictedCommoditiesDTO(), 200,
                accountDetail == null ? messageBundle.getString("negative.commodities.not.shared") :
                        messageBundle.getString("negative.commodities.shared"), requestBody);

        ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();

        saveLogRequest("MSG4: Negative List Commodities", RequestMethod.POST.name(), requestBody, requestTime, responseBody);
        return customResponse;
    }

    @RequestMapping(value = "/account/negative/suppliers", method = RequestMethod.POST)
    public CustomResponse getNegativeSuppliersList(HttpServletRequest request,
                                                     @RequestBody RequestParameter<IBANVerificationRequest> requestBody)
            throws CustomException, DataValidationException, NoDataFoundException, JsonProcessingException {

        ZonedDateTime requestTime = ZonedDateTime.now();
        AccountDetail accountDetail = getAccountDetail(requestBody);

        CustomResponse customResponse = ResponseUtility.buildResponseObject(accountDetail, new RestrictedSuppliersDTO(), 200,
                accountDetail == null ? messageBundle.getString("negative.suppliers.not.shared") :
                        messageBundle.getString("negative.suppliers.shared"), requestBody);

        ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();

        saveLogRequest("MSG5: Negative List Suppliers", RequestMethod.POST.name(), requestBody, requestTime, responseBody);
        return customResponse;
    }

    @RequestMapping(value = "/update/payment/modes", method = RequestMethod.POST)
    public CustomResponse updateAccountDetails(HttpServletRequest request,
                                            @RequestBody RequestParameter requestBody)
            throws CustomException, DataValidationException, NoDataFoundException, JsonProcessingException {

        HttpClient.updatePaymentModes();

        ZonedDateTime requestTime = ZonedDateTime.now();

        // TODO validate request common parameters
        AccountDetail accountDetail = getAccountDetail(requestBody);
        CustomResponse customResponse = ResponseUtility.buildResponseObject(accountDetail, new AccountDetailDTO(), 200,
                accountDetail == null ? messageBundle.getString("account.details.not.shared") :
                        messageBundle.getString("account.details.shared"), requestBody);
        requestBody.setData(accountDetail);
        CustomResponse customResponse2 = requestPSWAPI("/update/payment/modes", request, requestBody);

        saveLogRequest("MSG6: Update Payment Modes", RequestMethod.POST.name(),(ResponseUtility.APIResponse) customResponse.getBody(), requestTime,(ResponseUtility.APIResponse) customResponse2.getBody());
        return customResponse2;
    }

    @RequestMapping(value = "/update/negative/countries", method = RequestMethod.POST)
    public CustomResponse updateNegativeCountriesList(HttpServletRequest request,
                                                   @RequestBody RequestParameter requestBody)
            throws CustomException, DataValidationException, NoDataFoundException, JsonProcessingException {

        HttpClient.updateNegativeCountries();

        ZonedDateTime requestTime = ZonedDateTime.now();

        // TODO validate request common parameters
        AccountDetail accountDetail = getAccountDetail(requestBody);
        requestBody.setData(accountDetail);
        CustomResponse customResponse = ResponseUtility.buildResponseObject(accountDetail, new RestrictedCountiesDTO(), 200,
                accountDetail == null ? messageBundle.getString("negative.countries.not.shared") :
                        messageBundle.getString("negative.countries.shared"), requestBody);

        CustomResponse customResponse2 = requestPSWAPI("/update/negative/countries", request, requestBody);

//        saveLogRequest("MSG6: Update Payment Modes", RequestMethod.POST.name(), requestBody, requestTime, responseBody);
        return customResponse2;
    }

    @RequestMapping(value = "/update/negative/commodities", method = RequestMethod.POST)
    public CustomResponse updateNegativeCommoditiesList(HttpServletRequest request,
                                                     @RequestBody RequestParameter requestBody)
            throws CustomException, DataValidationException, NoDataFoundException, JsonProcessingException {

        HttpClient.updateNegativeCommodities();

        ZonedDateTime requestTime = ZonedDateTime.now();

        // TODO validate request common parameters
        AccountDetail accountDetail = getAccountDetail(requestBody);
        requestBody.setData(accountDetail);
        CustomResponse customResponse = ResponseUtility.buildResponseObject(accountDetail, new RestrictedCommoditiesDTO(), 200,
                accountDetail == null ? messageBundle.getString("negative.commodities.not.shared") :
                        messageBundle.getString("negative.commodities.shared"), requestBody);

        CustomResponse customResponse2 = requestPSWAPI("/update/negative/commodities", request, requestBody);

//        saveLogRequest("MSG6: Update Payment Modes", RequestMethod.POST.name(), requestBody, requestTime, responseBody);
        return customResponse2;
    }

    @RequestMapping(value = "/update/negative/suppliers", method = RequestMethod.POST)
    public CustomResponse updateNegativeSuppliersList(HttpServletRequest request,
                                                   @RequestBody RequestParameter requestBody)
            throws CustomException, DataValidationException, NoDataFoundException, JsonProcessingException {

        HttpClient.updateNegativeSupplier();

        ZonedDateTime requestTime = ZonedDateTime.now();
        AccountDetail accountDetail = getAccountDetail(requestBody);

        CustomResponse customResponse = ResponseUtility.buildResponseObject(accountDetail, new RestrictedSuppliersDTO(), 200,
                accountDetail == null ? messageBundle.getString("negative.suppliers.not.shared") :
                        messageBundle.getString("negative.suppliers.shared"), requestBody);

        CustomResponse customResponse2 = requestPSWAPI("/update/negative/suppliers", request, requestBody);

//        saveLogRequest("MSG6: Update Payment Modes", RequestMethod.POST.name(), requestBody, requestTime, responseBody);
        return customResponse2;
    }

    private CustomResponse requestPSWAPI(String uri,HttpServletRequest request, RequestParameter requestParameter) {

        ResponseUtility.APIResponse responseBodyPSW = HTTPClientUtils.postRequest(uri, request.getHeader("Authorization2"),
                requestParameter);
        CustomResponse customResponse2 = CustomResponse.status(HttpStatus.CREATED)
                .body(responseBodyPSW);
        return customResponse2;
    }

    private AccountDetail getAccountDetail(RequestParameter<IBANVerificationRequest> requestBody) throws DataValidationException, CustomException {
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

