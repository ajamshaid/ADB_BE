package com.infotech.adb.api.ad;

import com.infotech.adb.dto.AccountDetailDTO;
import com.infotech.adb.dto.BaseDTO;
import com.infotech.adb.dto.IBANVerificationRequest;
import com.infotech.adb.dto.RequestParameter;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.service.LogRequestService;
import com.infotech.adb.service.MQServices;
import com.infotech.adb.util.AppConstants;
import com.infotech.adb.util.AppUtility;
import com.infotech.adb.util.CustomResponse;
import com.infotech.adb.util.ResponseUtility;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    private MQServices mqService;

    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    public CustomResponse getBuildAndLogResponseByRequestType(RequestParameter<IBANVerificationRequest> requestBody
            , String requestType)
            throws CustomException, DataValidationException, NoDataFoundException {

        ZonedDateTime requestTime = ZonedDateTime.now();
        CustomResponse customResponse = null;

        if (RequestParameter.isValidRequest(requestBody, true)) {
            AccountDetailDTO accountDetail = null;
            try {
                IBANVerificationRequest ibanVerificationRequest = requestBody.getData();
                accountDetail = mqService.getAccountDetailsByIban(ibanVerificationRequest.getIban());

            } catch (Exception e) {
                ResponseUtility.exceptionResponse(e, null);
            }
            boolean noData = AppUtility.isEmpty(accountDetail);
            BaseDTO dto = null;
            String message = "";
            String logMessage = "";
/*
            if (AppConstants.REQ_TYPE_ACCT_DETAILS_WITH_PM.equals(requestType)) {
                dto = noData ? null : new AccountDetailDTO();//accountDetail);
                message = messageBundle.getString(noData ? "account.details.not.shared" : "account.details.shared");
                logMessage = "Sharing of Account Details & Authorized Payment Modes";
            } else if (AppConstants.REQ_TYPE_RES_COUNTRIES.equals(requestType)) {
                dto = noData ? null : new RestrictedCountriesDTO(accountDetail);
                message = messageBundle.getString(noData ? "negative.countries.not.shared" : "negative.countries.shared");
                logMessage = "Sharing Negative List of Countries";
            } else if (AppConstants.REQ_TYPE_RES_COMMODITIES.equals(requestType)) {
                dto = noData ? null : new RestrictedCommoditiesDTO(accountDetail);
                message = messageBundle.getString(noData ? "negative.commodities.not.shared" : "negative.commodities.shared");
                logMessage = "Sharing Negative List of Countries";
            } else if (AppConstants.REQ_TYPE_RES_SUPPLIERS.equals(requestType)) {
                dto = noData ? null : new RestrictedSuppliersDTO(accountDetail);
                message = messageBundle.getString(noData ? "negative.suppliers.not.shared" : "negative.suppliers.shared");
                logMessage = "Sharing Negative List of Countries";
            }

 */
            customResponse = ResponseUtility.successResponse(dto
                    , noData ? AppConstants.PSWResponseCodes.NO_DATA_FOUND : AppConstants.PSWResponseCodes.OK
                    , message
                    , requestBody,false
            );
            ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();
            logRequestService.saveLogRequest(logMessage, RequestMethod.POST.name(), requestBody, requestTime, responseBody);
        }
        return customResponse;
    }


    //**************************
    // 4.1.	Message 1 – Verification of NTN, IBAN, Email address and Mobile No. from AD
    // **************************/

    /**
     * @param requestBody IBANVerificationRequest
     * @return CustomResponse
     * @throws CustomException         Exception
     * @throws DataValidationException Exception
     * @throws NoDataFoundException    Exception
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
                accountExist = mqService.isAccountVerified(ibanVerificationRequest);
            } catch (Exception e) {
                ResponseUtility.exceptionResponse(e, null);
            }
            customResponse = ResponseUtility.successResponse(null
                    , accountExist ? AppConstants.PSWResponseCodes.VERIFIED : AppConstants.PSWResponseCodes.UN_VERIFIED
                    , accountExist ? messageBundle.getString("account.verified") : messageBundle.getString("account.un-verified")
                    , requestBody , false
            );

            ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();
            logRequestService.saveLogRequest("Verify Trader Profile From AD", RequestMethod.POST.name(), requestBody, requestTime, responseBody);

        }
        return customResponse;
    }

    //**************************
    // 4.2.	Message 2 – Sharing of Account Details & Authorized Payment Modes with PSW by AD
    // **************************/
    @RequestMapping(value = "/account/details", method = RequestMethod.POST)
    public CustomResponse getAccountDetails(@RequestBody RequestParameter<IBANVerificationRequest> requestBody)
            throws CustomException, DataValidationException, NoDataFoundException {
        return getBuildAndLogResponseByRequestType(requestBody, AppConstants.REQ_TYPE_ACCT_DETAILS_WITH_PM);
    }

    //**************************
    // 4.3.	Message 3 – Sharing Negative List of Countries with PSW
    // **************************/
    @RequestMapping(value = "/account/negative-countries", method = RequestMethod.POST)
    public CustomResponse getNegativeCountriesList(@RequestBody RequestParameter<IBANVerificationRequest> requestBody)
            throws CustomException, DataValidationException, NoDataFoundException {
        return getBuildAndLogResponseByRequestType(requestBody, AppConstants.REQ_TYPE_RES_COUNTRIES);
    }

    //**************************
    // 4.4.	Message 4 – Sharing Negative List of Commodities with PSW
    // **************************/
    @RequestMapping(value = "/account/negative-commodities", method = RequestMethod.POST)
    public CustomResponse getNegativeCommoditiesList(@RequestBody RequestParameter<IBANVerificationRequest> requestBody)
            throws CustomException, DataValidationException, NoDataFoundException {
        return getBuildAndLogResponseByRequestType(requestBody, AppConstants.REQ_TYPE_RES_COMMODITIES);
    }

    //**************************
    // 4.5.	Message 5 – Sharing Negative List of Suppliers with PSW
    // **************************/
    @RequestMapping(value = "/account/negative-suppliers", method = RequestMethod.POST)
    public CustomResponse getNegativeSuppliersList(@RequestBody RequestParameter<IBANVerificationRequest> requestBody)
            throws CustomException, DataValidationException, NoDataFoundException {
        return getBuildAndLogResponseByRequestType(requestBody, AppConstants.REQ_TYPE_RES_SUPPLIERS);
    }



}

