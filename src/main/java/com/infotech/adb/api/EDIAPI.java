package com.infotech.adb.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

//        JsonParser springParser = JsonParserFactory.getJsonParser();
//        String data = springParser.parseMap(reqBodyStr).get("data").toString();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode neoJsonNode = objectMapper.readTree(reqBodyStr);
        JsonNode dataNode = neoJsonNode.get("data");

        String data = dataNode.toString();

        System.out.println(data.toString());

        if (!RequestParameter.isValidRequest(requestParameter)) {
            customResponse = ResponseUtility.successResponse(null
                    , AppConstants.PSWResponseCodes.VALIDATION_ERROR
                    , "Request Parameters Missing"
                    , requestParameter, false);
        } else {
            String requestSignature = AppUtility.buildSignature(data);
            boolean isSignatureVerified = requestSignature.equals(requestParameter.getSignature());


            String processingCode = requestParameter.getProcessingCode();


            // @TODO Dummy code to Skip Signature for GD Parsing....Remove once Signature is Verified.
            if(processingCode.equals("101") || processingCode.equals("102") ){
                isSignatureVerified = true;
            }

            if (!isSignatureVerified) {
                customResponse = ResponseUtility.successResponse(null
                        , AppConstants.PSWResponseCodes.SIGNATURE_INVALID
                        , "Invalid Signature Received"
                        , requestParameter, false);
            } else {  // Verified Signatures


                log.info("Valid Request with processing code:" + processingCode);
                switch (processingCode) {
                    case "101": //5.1.2 Message 2 – Sharing of GD and Financial Information with AD by PSW
                        customResponse = this.shareImportGDFinInfoToBank(data, requestParameter);
                        break;
                    case "102": //5.2.2 Message 2 – Sharing of GD and Financial Information with AD by PSW
                        customResponse = this.shareExportGDFinInfoToBank(data, requestParameter);
                        break;

                    case "301": // 4.1 Message 1, Verification of NTN,IBAN,Email and Mob
                        customResponse = this.verifyAccount(data, requestParameter);
                        break;
                    case "302": // 4.2.	Message 2 – Sharing of Account Details & Authorized Payment Modes with PSW by AD
                        RequestParameter.isValidIBANRequest(requestParameter, true);
                        customResponse = this.getAccountDetails(data, requestParameter);
                        break;
                    case "306": // 7.1.1 Message 1 – Sharing of Change of Bank request with AD
                        customResponse = this.chageOfBankRequest(data, requestParameter);
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
            throws CustomException, NoDataFoundException {
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
        logRequestService.saveLogRequest("Verify Trader Profile From AD", RequestMethod.POST.name(), requestParameter, responseBody);
        return customResponse;
    }

    /**************************
     // 4.2.	Message 2 – Sharing of Account Details & Authorized Payment Modes with PSW by AD
     1.PSW will request AD to share the user account details and authorized payment modes against IBAN.
     2. In Response the AD will share the user account details and authorized payment modes against IBAN.
     / **************************/
    public CustomResponse getAccountDetails(String data, RequestParameter requestParameter)
            throws CustomException, NoDataFoundException {
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
        logRequestService.saveLogRequest(logMessage, RequestMethod.POST.name(), requestParameter, responseBody);
        return customResponse;
    }

    /**************************
     // 5.1.2 Message 2 – Sharing of GD and Financial Information with AD by PSW
     1. PSW will share the goods declaration and financial information of import with the authorized dealer.
     2. AD receive the information and shares the acknowledgement with PSW.
     **************************/
    public CustomResponse shareImportGDFinInfoToBank(String data, RequestParameter requestParameter)
            throws NoDataFoundException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        GDImportDTO dto = mapper.readValue(data, GDImportDTO.class);
        System.out.println("IN coming GD Info:" + dto);

        if (!AppUtility.isEmpty(dto)) {
            referenceService.updateGD(dto.convertToEntity());
        }
        CustomResponse customResponse = null;

        customResponse = ResponseUtility.successResponse("{}", AppConstants.PSWResponseCodes.OK,
                "Updated [Import] GD and financial information."
                , requestParameter, false);

        ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();
        String logMessage = "Sharing of [Import] GD and Financial Information with AD by PSW";

        logRequestService.saveLogRequest(logMessage, RequestMethod.POST.name(), requestParameter, responseBody);
        return customResponse;
    }

    /**************************
     // 5.2.2 Message 2 – Sharing of GD and Financial Information with AD by PSW
     1. PSW will share the GD and financial information of export with AD.
     2. AD receive the information and shares the acknowledgement with PSW.
     **************************/
    public CustomResponse shareExportGDFinInfoToBank(String data, RequestParameter requestParameter)
            throws NoDataFoundException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        GDExportDTO dto = mapper.readValue(data, GDExportDTO.class);
        System.out.println("IN coming GD Info:" + dto);

        if (!AppUtility.isEmpty(dto)) {
            referenceService.updateGDExport(dto.convertToEntity());
        }

        CustomResponse customResponse  = ResponseUtility.successResponse("{}", AppConstants.PSWResponseCodes.OK,
                "Updated [Export] GD and financial information."
                , requestParameter, false);

        ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();
        String logMessage = "Sharing of [Export] GD and Financial Information with AD by PSW";

        logRequestService.saveLogRequest(logMessage, RequestMethod.POST.name(), requestParameter, responseBody);
        return customResponse;
    }

    /**************************
     // 7.1.	Message 1 – Sharing of Change of Bank request with AD
     // 1. PSW will share the Change of Bank request with AD.
     **************************/
    public CustomResponse chageOfBankRequest(String data, RequestParameter requestParameter)
            throws NoDataFoundException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        ChangeBankRequestDTO dto = mapper.readValue(data, ChangeBankRequestDTO.class);
        System.out.println("IN coming COB Object is:" + dto);

        if (!AppUtility.isEmpty(dto)) {
            referenceService.updateCOB(dto.convertToEntity());
        }
        CustomResponse customResponse  = ResponseUtility.successResponse("{}",AppConstants.PSWResponseCodes.OK,
                "Change of bank request received."
                ,requestParameter, false);

        ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();
        String logMessage = "Message 1 – Sharing of Change of Bank request with AD";

        logRequestService.saveLogRequest(logMessage, RequestMethod.POST.name(), requestParameter, responseBody);
        return customResponse;
    }

/*
    public CustomResponse getBuildAndLogResponseByRequestType(RequestParameter requestBody
            , String requestType)
            throws CustomException, DataValidationException, NoDataFoundException {

        CustomResponse customResponse = null;

        if (RequestParameter.isValidIBANRequest(requestBody, true)) {
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
 * /
            customResponse = ResponseUtility.successResponse(dto
                    , noData ? AppConstants.PSWResponseCodes.NO_DATA_FOUND : AppConstants.PSWResponseCodes.OK
                    , message
                    , requestBody,false
            );
            ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();
            logRequestService.saveLogRequest(logMessage, RequestMethod.POST.name(), requestBody, responseBody);
        }
        return customResponse;
    }

    //**************************
    // 4.2.	Message 2 – Sharing of Account Details & Authorized Payment Modes with PSW by AD
    // ************************* * /
@RequestMapping(value = "/account/details", method = RequestMethod.POST)
public CustomResponse getAccountDetails(@RequestBody RequestParameter<IBANVerificationRequest> requestBody)
        throws CustomException, DataValidationException, NoDataFoundException {
    return getBuildAndLogResponseByRequestType(requestBody, AppConstants.REQ_TYPE_ACCT_DETAILS_WITH_PM);
}

 */

}


