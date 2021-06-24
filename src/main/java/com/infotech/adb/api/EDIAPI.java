package com.infotech.adb.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infotech.adb.dto.*;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.service.LogRequestService;
import com.infotech.adb.service.MQServices;
import com.infotech.adb.service.ReferenceService;
import com.infotech.adb.util.AppConstants;
import com.infotech.adb.util.AppUtility;
import com.infotech.adb.util.CustomResponse;
import com.infotech.adb.util.ResponseUtility;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
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
    private ReferenceService referenceService;

    @Autowired
    private MQServices mqService;

    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @RequestMapping(value = "/edi", method = RequestMethod.POST, headers = {"content-type=application/json"})
    public CustomResponse getMessageAndResponse(@RequestBody String reqBodyStr)
            throws CustomException, DataValidationException, NoDataFoundException, JsonProcessingException {

        CustomResponse customResponse = null;
        RequestParameter requestParameter = new RequestParameter();

        ObjectMapper mapper = new ObjectMapper();
        requestParameter = mapper.readValue(reqBodyStr, RequestParameter.class);

        JsonParser springParser = JsonParserFactory.getJsonParser();
        String data = springParser.parseMap(reqBodyStr).get("data").toString();


        if (!RequestParameter.isValidRequest(requestParameter)) {
            customResponse = ResponseUtility.successResponse(null
                    , AppConstants.PSWResponseCodes.VALIDATION_ERROR
                    , "Request Parameters Missing"
                    , requestParameter, false);
        } else {
            String requestSignature = AppUtility.buildSignature(data);
            boolean isSignatureVerified = requestSignature.equals(requestParameter.getSignature());

            if (!isSignatureVerified) {
                customResponse = ResponseUtility.successResponse(null
                        , AppConstants.PSWResponseCodes.SIGNATURE_INVALID
                        , "Invalid Signature Received"
                        , requestParameter, false);
            } else {  // Verified Signatures
                String processingCode = requestParameter.getProcessingCode();

                log.info("Valid Request with processing code:" + processingCode);
                switch (processingCode) {
                    case "301": // 4.1 Message 1, Verification of NTN,IBAN,Email and Mob
                        customResponse = this.verifyAccount(data, requestParameter);
                        break;
                    case "302": // 4.2.	Message 2 – Sharing of Account Details & Authorized Payment Modes with PSW by AD
                        RequestParameter.isValidIBANRequest(requestParameter, true);
                        customResponse = this.getAccountDetails(data, requestParameter);
                        break;
                    case "101": //5.1.2 Message 2 – Sharing of GD and Financial Information with AD by PSW
                        customResponse = this.shareImportGDFinInfoToBank(data, requestParameter);
                        break;
                    case "102": //5.2.2 Message 2 – Sharing of GD and Financial Information with AD by PSW
                        customResponse = this.shareExportGDFinInfoToBank(data, requestParameter);
                        break;
                    default: // Default Custom response
                        log.info("No Case Matched for processing code:" + processingCode);
                        throw new DataValidationException("Invalid Request! No Processing Code Matched");
                }
            }
        }
        return customResponse;
    }


    /**************************
     // 4.1.	Message 1 – Verification of NTN, IBAN, Email address and Mobile No. from AD
     1. PSW will share user NTN, IBAN, Email Address, Mobile Number with respective AD for verification.
     2. In Response, the AD will share the verification status of the shared IBAN, Email Address and Mobile
     No. with PSW.
     **************************/
    public CustomResponse verifyAccount(String data, RequestParameter requestParameter)
            throws CustomException, DataValidationException, NoDataFoundException {
        ZonedDateTime requestTime = ZonedDateTime.now();
        CustomResponse customResponse = null;
        boolean isVerified = false;
        try {
            ObjectMapper mapper = new ObjectMapper();
            IBANVerificationRequest ibanVerificationRequest = mapper.readValue(data, IBANVerificationRequest.class);

            isVerified = mqService.isAccountVerified(ibanVerificationRequest);
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e, null);
        }
        customResponse = ResponseUtility.successResponse(null
                , isVerified ? AppConstants.PSWResponseCodes.VERIFIED : AppConstants.PSWResponseCodes.UN_VERIFIED
                , isVerified ? messageBundle.getString("account.verified") : messageBundle.getString("account.un-verified")
                , requestParameter, false
        );

        ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();
        logRequestService.saveLogRequest("Verify Trader Profile From AD", RequestMethod.POST.name(), requestParameter, requestTime, responseBody);
        return customResponse;
    }

    /**************************
     // 4.2.	Message 2 – Sharing of Account Details & Authorized Payment Modes with PSW by AD
     1.PSW will request AD to share the user account details and authorized payment modes against IBAN.
     2. In Response the AD will share the user account details and authorized payment modes against IBAN.
     / **************************/
    public CustomResponse getAccountDetails(String data, RequestParameter requestParameter)
            throws CustomException, DataValidationException, NoDataFoundException {
        ZonedDateTime requestTime = ZonedDateTime.now();
        CustomResponse customResponse = null;
        AccountDetailDTO accountDetailDTO = null;
        String message = "";
        String logMessage = "";
        boolean noData = false;
        try {
            ObjectMapper mapper = new ObjectMapper();
            IBANVerificationRequest ibanVerificationRequest = mapper.readValue(data, IBANVerificationRequest.class);

//            IBANVerificationRequest ibanVerificationRequest = requestBody.getData();
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
                , requestParameter, false
        );

        ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();
        logRequestService.saveLogRequest(logMessage, RequestMethod.POST.name(), requestParameter, requestTime, responseBody);
        return customResponse;
    }

    /**************************
     // 5.1.2 Message 2 – Sharing of GD and Financial Information with AD by PSW
     1. PSW will share the goods declaration and financial information of import with the authorized dealer.
     2. AD receive the information and shares the acknowledgement with PSW.
     **************************/
    public CustomResponse shareImportGDFinInfoToBank(String data, RequestParameter requestParameter)
            throws CustomException, DataValidationException, NoDataFoundException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        GDImportDTO dto = mapper.readValue(data, GDImportDTO.class);
        System.out.println("IN coming GD Info:" + dto);

        if (!AppUtility.isEmpty(dto)) {
            referenceService.updateGD(dto.convertToEntity());
        }

        ZonedDateTime requestTime = ZonedDateTime.now();
        CustomResponse customResponse = null;

        customResponse = ResponseUtility.successResponse("{}", AppConstants.PSWResponseCodes.OK,
                "Updated GD and financial information."
                , requestParameter, false);

        ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();
        String logMessage = "Sharing of GD and Financial Information with AD by PSW";

        logRequestService.saveLogRequest(logMessage, RequestMethod.POST.name(), requestParameter, requestTime, responseBody);
        return customResponse;
    }

    /**************************
     // 5.2.2 Message 2 – Sharing of GD and Financial Information with AD by PSW
     1. PSW will share the GD and financial information of export with AD.
     2. AD receive the information and shares the acknowledgement with PSW.
     **************************/
    public CustomResponse shareExportGDFinInfoToBank(String data, RequestParameter requestParameter)
            throws CustomException, DataValidationException, NoDataFoundException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        GDExportDTO dto = mapper.readValue(data, GDExportDTO.class);
        System.out.println("IN coming GD Info:" + dto);

        if (!AppUtility.isEmpty(dto)) {
            referenceService.updateGDExport(dto.convertToEntity());
        }

        ZonedDateTime requestTime = ZonedDateTime.now();
        CustomResponse customResponse = null;

        customResponse = ResponseUtility.successResponse("{}", AppConstants.PSWResponseCodes.OK,
                "Updated GD and financial information."
                , requestParameter, false);

        ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();
        String logMessage = "Sharing of GD and Financial Information with AD by PSW";

        logRequestService.saveLogRequest(logMessage, RequestMethod.POST.name(), requestParameter, requestTime, responseBody);
        return customResponse;
    }

}


