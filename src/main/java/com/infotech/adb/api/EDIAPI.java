package com.infotech.adb.api;

import com.infotech.adb.dto.AccountDetailDTO;
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
@RequestMapping("/api/dealers/saud")
@Log4j2
@Api(tags = "@EDI")
public class EDIAPI {

    @Autowired
    private LogRequestService logRequestService;

    @Autowired
    private MQServices mqService;

    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @RequestMapping(value = "/edi", method = RequestMethod.POST, headers = {"content-type=application/json"})
    public CustomResponse getMessageAndResponse(@RequestBody RequestParameter<IBANVerificationRequest> requestBody)
            throws CustomException, DataValidationException, NoDataFoundException {

        CustomResponse customResponse = null;

        if (RequestParameter.isValidRequest(requestBody, true)) {

            String processingCode = requestBody.getProcessingCode();

            log.info("Valid Request with processing code:" + processingCode);
            switch (processingCode) {
                case "301": // 4.1 Message 1, Verification of NTN,IBAN,Email and Mob
                    customResponse = this.verifyAccount(requestBody);
                    break;
                case "302": // 4.2.	Message 2 – Sharing of Account Details & Authorized Payment Modes with PSW by AD
                    customResponse = this.getAccountDetails(requestBody);
                    break;
                default: // Default Custom response
                    log.info("No Case Matched for processing code:" + processingCode);
                    throw new DataValidationException("Invalid Request! No Processing Code Matched");
            }
        } else {
            throw new DataValidationException("Required Parameter Missing!");
        }
        return customResponse;
    }


    //**************************
    // 4.1.	Message 1 – Verification of NTN, IBAN, Email address and Mobile No. from AD
    // **************************/
    public CustomResponse verifyAccount(RequestParameter<IBANVerificationRequest> requestBody)
            throws CustomException, DataValidationException, NoDataFoundException {
        ZonedDateTime requestTime = ZonedDateTime.now();
        CustomResponse customResponse = null;
        boolean isVerified = false;
        try {
            IBANVerificationRequest ibanVerificationRequest = requestBody.getData();
            isVerified = mqService.isAccountVerified(ibanVerificationRequest);
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e, null);
        }
        customResponse = ResponseUtility.successResponse(null
                , isVerified ? AppConstants.PSWResponseCodes.VERIFIED : AppConstants.PSWResponseCodes.UN_VERIFIED
                , isVerified ? messageBundle.getString("account.verified") : messageBundle.getString("account.un-verified")
                , requestBody, false
        );

        ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();
        logRequestService.saveLogRequest("Verify Trader Profile From AD", RequestMethod.POST.name(), requestBody, requestTime, responseBody);
        return customResponse;
    }

    //**************************
    // 4.2.	Message 2 – Sharing of Account Details & Authorized Payment Modes with PSW by AD
    // **************************/
    public CustomResponse getAccountDetails(RequestParameter<IBANVerificationRequest> requestBody)
            throws CustomException, DataValidationException, NoDataFoundException {
        ZonedDateTime requestTime = ZonedDateTime.now();
        CustomResponse customResponse = null;
        AccountDetailDTO accountDetailDTO = null;
        String message = "";
        String logMessage = "";
        boolean noData = false;
        try {
            IBANVerificationRequest ibanVerificationRequest = requestBody.getData();
            accountDetailDTO = mqService.getAccountDetailsByIban(ibanVerificationRequest.getIban());
            noData = AppUtility.isEmpty(accountDetailDTO);
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e, null);
        }
        message = messageBundle.getString(noData ? "account.details.not.shared" : "account.details.shared");
        logMessage = "Sharing of Account Details & Authorized Payment Modes";

        customResponse = ResponseUtility.successResponse(accountDetailDTO
                , noData ? AppConstants.PSWResponseCodes.NO_DATA_FOUND : AppConstants.PSWResponseCodes.OK
                , message
                , requestBody,false
        );

        ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();
        logRequestService.saveLogRequest(logMessage, RequestMethod.POST.name(), requestBody, requestTime, responseBody);
        return customResponse;
    }
}


