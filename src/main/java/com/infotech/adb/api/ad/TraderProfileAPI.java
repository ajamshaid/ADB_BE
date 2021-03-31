package com.infotech.adb.api.ad;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.dto.*;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.model.entity.AccountDetail;
import com.infotech.adb.model.entity.LogRequest;
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
import java.util.ResourceBundle;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/trader-profile")
@Log4j2
@Api(tags = "@TP")
public class TraderProfileAPI {

    @Autowired
    private LogRequestService logRequestService;

    @Autowired
    private AccountService accountService;

    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    //@TODO verify this method implementation....
    private AccountDetail getAccountDetail(RequestParameter<IBANVerificationRequest> requestBody) throws DataValidationException, CustomException {
        // TODO validate request common parameters
        if (AppUtility.isEmpty(requestBody.getMessageId())) {
            throw new DataValidationException(messageBundle.getString("id.not.found"));
        }
        AccountDetail accountDetail = null;
        try {
            accountDetail = accountService.getAccountDetailsByIban(requestBody.getData().getIban());
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e, null);
        }
        return accountDetail;
    }

    //**************************
    // 4.1.	Message 1 – Verification of NTN, IBAN, Email address and Mobile No. from AD
    // **************************/

    /**
     * @param requestBody
     * @return
     * @throws CustomException
     * @throws DataValidationException
     * @throws NoDataFoundException
     */
    @RequestMapping(value = "/accounts/verification", method = RequestMethod.POST)
    public CustomResponse verifyAccount(@RequestBody RequestParameter<IBANVerificationRequest> requestBody)
            throws CustomException, DataValidationException, NoDataFoundException {
        ZonedDateTime requestTime = ZonedDateTime.now();

        CustomResponse customResponse = null;
        if (RequestParameter.isValidRequest(requestBody, false)) {
            boolean accountExist = false;
            try {
                IBANVerificationRequest ibanVerificationRequest = requestBody.getData();
                accountExist = accountService.isAccountDetailExists(ibanVerificationRequest);
            } catch (Exception e) {
                ResponseUtility.exceptionResponse(e, null);
            }
            customResponse = ResponseUtility.successResponse(null
                    , accountExist ? AppConstants.PSWResponseCodes.VERIFIED : AppConstants.PSWResponseCodes.UN_VERIFIED
                    , accountExist ? messageBundle.getString("account.verified") : messageBundle.getString("account.un-verified")
                    , requestBody
            );

            ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();
            saveLogRequest("Verify Trader Profile From AD", RequestMethod.POST.name(), requestBody, requestTime, responseBody);

        }
        return customResponse;
    }

    //**************************
    // 4.2.	Message 2 – Sharing of Account Details & Authorized Payment Modes with PSW by AD
    // **************************/
    @RequestMapping(value = "/account/details", method = RequestMethod.POST)
    public CustomResponse getAccountDetails(@RequestBody RequestParameter<IBANVerificationRequest> requestBody)
            throws CustomException, DataValidationException, NoDataFoundException {

        ZonedDateTime requestTime = ZonedDateTime.now();

        CustomResponse customResponse = null;
        if (RequestParameter.isValidRequest(requestBody, true)) {
            AccountDetail accountDetail = null;
            try {
                IBANVerificationRequest ibanVerificationRequest = requestBody.getData();
                accountDetail = accountService.getAccountDetailsByIban(ibanVerificationRequest.getIban());

            } catch (Exception e) {
                ResponseUtility.exceptionResponse(e, null);
            }
            boolean noData = AppUtility.isEmpty(accountDetail);
            AccountDetailDTO dto = null;
            if (!noData) {
                accountDetail.getAuthorizedPaymentModesSet();
                dto = new AccountDetailDTO();
                dto.convertToDTO(accountDetail, true);
            }

            customResponse = ResponseUtility.successResponse(dto
                    , noData ? AppConstants.PSWResponseCodes.NO_DATA_FOUND : AppConstants.PSWResponseCodes.OK
                    , messageBundle.getString(noData ? "account.details.not.shared" : "account.details.shared")
                    , requestBody
            );
            ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();

            saveLogRequest("Sharing of Account Details & Authorized Payment Modes", RequestMethod.POST.name(), requestBody, requestTime, responseBody);
        }
        return customResponse;
    }

    //**************************
    // 4.3.	Message 3 – Sharing Negative List of Countries with PSW
    // **************************/
    @RequestMapping(value = "/account/negative-countries", method = RequestMethod.POST)
    public CustomResponse getNegativeCountriesList(@RequestBody RequestParameter<IBANVerificationRequest> requestBody)
            throws CustomException, DataValidationException, NoDataFoundException, JsonProcessingException {

        ZonedDateTime requestTime = ZonedDateTime.now();
        CustomResponse customResponse = null;

        if (RequestParameter.isValidRequest(requestBody, true)) {
            AccountDetail accountDetail = null;
            try {
                IBANVerificationRequest ibanVerificationRequest = requestBody.getData();
                accountDetail = accountService.getAccountDetailsByIban(ibanVerificationRequest.getIban());

            } catch (Exception e) {
                ResponseUtility.exceptionResponse(e, null);
            }
            boolean noData = AppUtility.isEmpty(accountDetail);
            RestrictedCountiesDTO dto = null;
            if (!noData) {
                accountDetail.getRestrictedCoutriesSet();
                dto = new RestrictedCountiesDTO();
                dto.convertToDTO(accountDetail, true);
            }

            customResponse = ResponseUtility.successResponse(dto
                    , noData ? AppConstants.PSWResponseCodes.NO_DATA_FOUND : AppConstants.PSWResponseCodes.OK
                    , messageBundle.getString(noData ? "negative.countries.not.shared" : "negative.countries.shared")
                    , requestBody
            );
            ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();

            saveLogRequest("Sharing Negative List of Countries", RequestMethod.POST.name(), requestBody, requestTime, responseBody);
        }
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

//        saveLogRequest("MSG4: Negative List Commodities", RequestMethod.POST.name(), requestBody, requestTime, responseBody);
        saveLogRequest("Negative List Commodities", RequestMethod.POST.name(), requestBody, requestTime, responseBody);
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

//        saveLogRequest("MSG5: Negative List Suppliers", RequestMethod.POST.name(), requestBody, requestTime, responseBody);
        saveLogRequest("Negative List Suppliers", RequestMethod.POST.name(), requestBody, requestTime, responseBody);
        return customResponse;
    }

    @RequestMapping(value = "/update/payment/modes", method = RequestMethod.POST)
    public CustomResponse updateAccountDetails(HttpServletRequest request,
                                               @RequestBody RequestParameter<IBANVerificationRequest> requestBody)
            throws CustomException, DataValidationException, NoDataFoundException, JsonProcessingException {

        HttpClientTest.updatePaymentModes();

        ZonedDateTime requestTime = ZonedDateTime.now();

        AccountDetail accountDetail = getAccountDetail(requestBody);
        RequestParameter pswRequestPayload = requestBody.newRequestParameter();
        pswRequestPayload.setData(new RestrictedSuppliersDTO().convertToNewDTO(accountDetail, false));

        CustomResponse customResponse2 = requestPSWAPI("/update/payment/modes", request, pswRequestPayload);

//        saveLogRequest("MSG6: Update Payment Modes", RequestMethod.POST.name(), requestBody, requestTime,(ResponseUtility.APIResponse) customResponse2.getBody());
        saveLogRequest("Update Payment Modes With PSW by AD", RequestMethod.POST.name(), requestBody, requestTime, (ResponseUtility.APIResponse) customResponse2.getBody());
        return customResponse2;
    }

    @RequestMapping(value = "/update/negative/countries", method = RequestMethod.POST)
    public CustomResponse updateNegativeCountriesList(HttpServletRequest request,
                                                      @RequestBody RequestParameter<IBANVerificationRequest> requestBody)
            throws CustomException, DataValidationException, NoDataFoundException, JsonProcessingException {

        HttpClientTest.updateNegativeCountries();

        ZonedDateTime requestTime = ZonedDateTime.now();

        AccountDetail accountDetail = getAccountDetail(requestBody);
        RequestParameter pswRequestPayload = requestBody.newRequestParameter();
        pswRequestPayload.setData(new RestrictedSuppliersDTO().convertToNewDTO(accountDetail, false));

        CustomResponse customResponse2 = requestPSWAPI("/update/negative/countries", request, pswRequestPayload);

//        saveLogRequest("MSG7: Update Negative Countries", RequestMethod.POST.name(), requestBody, requestTime,(ResponseUtility.APIResponse) customResponse2.getBody());
        saveLogRequest("Update Negative Countries With PSW by AD", RequestMethod.POST.name(), requestBody, requestTime, (ResponseUtility.APIResponse) customResponse2.getBody());

        return customResponse2;
    }

    @RequestMapping(value = "/update/negative/commodities", method = RequestMethod.POST)
    public CustomResponse updateNegativeCommoditiesList(HttpServletRequest request,
                                                        @RequestBody RequestParameter<IBANVerificationRequest> requestBody)
            throws CustomException, DataValidationException, NoDataFoundException, JsonProcessingException {

        HttpClientTest.updateNegativeCommodities();

        ZonedDateTime requestTime = ZonedDateTime.now();

        AccountDetail accountDetail = getAccountDetail(requestBody);
        RequestParameter pswRequestPayload = requestBody.newRequestParameter();
        pswRequestPayload.setData(new RestrictedSuppliersDTO().convertToNewDTO(accountDetail, false));

        CustomResponse customResponse2 = requestPSWAPI("/update/negative/commodities", request, pswRequestPayload);

//        saveLogRequest("MSG8: Update Negative Commodities", RequestMethod.POST.name(), requestBody, requestTime,(ResponseUtility.APIResponse) customResponse2.getBody());
        saveLogRequest("Update Negative Commodities With PSW by AD", RequestMethod.POST.name(), requestBody, requestTime, (ResponseUtility.APIResponse) customResponse2.getBody());
        return customResponse2;
    }

    @RequestMapping(value = "/update/negative/suppliers", method = RequestMethod.POST)
    public CustomResponse updateNegativeSuppliersList(HttpServletRequest request,
                                                      @RequestBody RequestParameter<IBANVerificationRequest> requestBody)
            throws CustomException, DataValidationException, NoDataFoundException, JsonProcessingException {

        HttpClientTest.updateNegativeSupplier();

        ZonedDateTime requestTime = ZonedDateTime.now();
        AccountDetail accountDetail = getAccountDetail(requestBody);
        RequestParameter pswRequestPayload = requestBody.newRequestParameter();
        pswRequestPayload.setData(new RestrictedSuppliersDTO().convertToNewDTO(accountDetail, false));

        CustomResponse customResponse2 = requestPSWAPI("/update/negative/suppliers", request, pswRequestPayload);

//        saveLogRequest("MSG9: Update Negative Supplier", RequestMethod.POST.name(), requestBody, requestTime,(ResponseUtility.APIResponse) customResponse2.getBody());
        saveLogRequest("Update Negative Supplier With PSW by AD", RequestMethod.POST.name(), requestBody, requestTime, (ResponseUtility.APIResponse) customResponse2.getBody());
        return customResponse2;
    }

    private CustomResponse requestPSWAPI(String uri, HttpServletRequest request, RequestParameter requestParameter) {

        ResponseUtility.APIResponse responseBodyPSW = HTTPClientUtils.postRequest(uri, request.getHeader("Authorization"),
                requestParameter);
        CustomResponse customResponse2 = CustomResponse.status(HttpStatus.CREATED).body(responseBodyPSW);
        return customResponse2;
    }

    private void saveLogRequest(String messageName, String messageType, RequestParameter requestBody,
                                ZonedDateTime requestTime, ResponseUtility.APIResponse responseBody) {
        LogRequest logRequest = new LogRequest();
        logRequest.setReceiverId(requestBody.getReceiverId());
        logRequest.setSenderId(requestBody.getSenderId());
        logRequest.setMsgIdentifier(messageName);
        logRequest.setRequestMethod(messageType);
        try {
            logRequest.setRequestPayload(requestBody.toJson());
            logRequest.setResponsePayload(responseBody.toJson());
        } catch (JsonProcessingException e) {
            log.error("-- LogRequest without payload's will be saved as error occured while parsing Request/Response payload :" + e.getMessage());
            e.printStackTrace();
        }
        logRequest.setRequestTime(requestTime);
        logRequest.setResponseTime(ZonedDateTime.now());
        logRequest.setCreatedOn(ZonedDateTime.now());
        logRequest.setResponseCode(responseBody.getResponseCode());
        logRequest.setResponseMessage(responseBody.getMessage());
        logRequestService.createLogRequest(logRequest);
    }
}

