package com.infotech.adb.psw.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.config.RestTemplateResponseErrorHandler;
import com.infotech.adb.dto.*;
import com.infotech.adb.service.LogRequestService;
import com.infotech.adb.util.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.UUID;


@Log4j2
@Component
public class PSWAPIConsumerService {

    @Autowired
    private LogRequestService logRequestService;

    //**************************
    // Get Authorization Token
    // **************************/
    public PSWAuthTokenResponse getAuthorizationToken() throws JsonProcessingException,ResourceAccessException {
        RestTemplate restTemplate = new RestTemplate(
                HTTPClientUtils.getClientHttpRequestFactory(
                        AppConstants.PSW.CLIENT_ID, AppConstants.PSW.CLIENT_SECRET));

        log.info("AppConstants.PSW.BASE_URL :" + AppConstants.PSW.BASE_URL);

        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(
                AppConstants.PSW.BASE_URL + AppConstants.PSW.API_AUTH).build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", AppConstants.PSW.AUT_GRANT_TYPE);
        map.add("client_id", AppConstants.PSW.CLIENT_ID);
        map.add("client_secret", AppConstants.PSW.CLIENT_SECRET);

        HttpEntity<MultiValueMap<String, String>> request
                = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        PSWAuthTokenResponse authTokenResponse = new PSWAuthTokenResponse();
        try {
            ResponseEntity<String> result = restTemplate.postForEntity(uriBuilder.toUri(), request, String.class);

            log.debug("________________Status Code:" + result.getStatusCode());
            log.debug("________________Body:" + result.getBody());

            authTokenResponse = PSWAuthTokenResponse.jsonToObject(result.getBody());
            authTokenResponse.setResponseCode(result.getStatusCodeValue());
            authTokenResponse.setMessage("Authentication Successful.");

            System.out.println("------------------AuthTokem Response" + authTokenResponse);
            log.debug("------------------AuthTokem Response" + authTokenResponse);
        } catch (HttpStatusCodeException ex) {
            HttpStatus statusCode = ex.getStatusCode();
            if (HttpStatus.UNAUTHORIZED == statusCode) {
                authTokenResponse.setResponseCode(statusCode.value());
                authTokenResponse.setMessage(ex.getLocalizedMessage());
            }
        }
        return authTokenResponse;
    }


    /*********************************************************************
     4.3
     Message 3 – Sharing of updated authorized payment modes by AD with PSW
     ********************************************************************/
    public ResponseUtility.APIResponse updateAccountAndPMInPWS(AccountPMDTO dto)
            throws HttpClientErrorException, JsonProcessingException {

        RequestParameter<AccountPMDTO> requestParameter = new RequestParameter<>(
                UUID.randomUUID(),
                AppConstants.AD_ID, AppConstants.PSW.ID, "03"
                , AppConstants.PSW.METHOD_ID_UPDATE_INFO_AND_PM
                , AppConstants.AD_SIGNATURE);

//        AccountDetailDTO dto = null; ///new AccountDetailDTO(accountDetail);
        requestParameter.setData(dto);
        log.debug("\nRequestParameter Json:" + requestParameter.toJson());
        return executeRequest(requestParameter, "4.3 - Sharing of Update Information and Payment Mode By AD");
    }


    /***********************************************************
     4.4  Message 4 – Trader Profile Active/Inactive Message by AD to PSW
     ***********************************************************/
    public ResponseUtility.APIResponse updateTraderProfileAccountStatus(TraderProfileStatusDTO dto)
            throws HttpClientErrorException, JsonProcessingException {

        RequestParameter<TraderProfileStatusDTO> requestParameter = new RequestParameter<>(
                UUID.randomUUID()
                , AppConstants.AD_ID
                , AppConstants.PSW.ID, "03"
                , AppConstants.PSW.METHOD_ID_UPDATE_TRADER_ACCT_STATUS
                , AppConstants.AD_SIGNATURE);
        requestParameter.setData(dto);
        System.out.println("\nRequestParameter Json:" + requestParameter.toJson());
        return executeRequest(requestParameter, "4.4 - Trader Profile Active/Inactive Message by AD to PSW");
    }


    /***********************************************************
     11.1    Message 1 – Sharing negative list of countries by AD with PSW / Sharing
     update in negative list of countries by AD with PSW
     ***********************************************************/
    public ResponseUtility.APIResponse shareNegativeListOfCountries(BankNegativeCountriesDTO restrictedDTO)
            throws HttpClientErrorException, JsonProcessingException {

        RequestParameter<BankNegativeCountriesDTO> requestParameter = new RequestParameter<>(
                UUID.randomUUID()
                , AppConstants.AD_ID
                , AppConstants.PSW.ID, "03"
                , AppConstants.PSW.METHOD_ID_SHARE_NEG_LIST_OF_COUNTRIES
                , AppConstants.AD_SIGNATURE);
        requestParameter.setData(restrictedDTO);

        return executeRequest(requestParameter, "11.1 – Sharing Updated Negative List of Countries with PSW");
    }

    /***********************************************************
     11.2 Message 2 – Sharing Negative List of Commodities by AD with PSW/ Sharing
     update in negative list of Commodities by AD with PSW
     ***********************************************************/
    public ResponseUtility.APIResponse shareNegativeListOfCommodities(BankNegativeCommoditiesDTO dto)
            throws HttpClientErrorException, JsonProcessingException {

        RequestParameter<BankNegativeCommoditiesDTO> requestParameter = new RequestParameter<>(
                UUID.randomUUID()
                , AppConstants.AD_ID
                , AppConstants.PSW.ID, "03"
                , AppConstants.PSW.METHOD_ID_SHARE_NEG_LIST_OF_COMMODITIES
                , AppConstants.AD_SIGNATURE);
        requestParameter.setData(dto);

        return executeRequest(requestParameter, "11.2 – Sharing Updated Negative List of Commodities with PSW");
    }

    /***********************************************************
     11.3 Message 3 – Sharing Negative List of Suppliers by AD with PSW/ Sharing
     update in negative list of Suppliers by AD with PSW
     ***********************************************************/
    public ResponseUtility.APIResponse shareNegativeListOfSuppliers(BankNegativeSuppliersDTO dto)
            throws HttpClientErrorException, JsonProcessingException {

        RequestParameter<BankNegativeSuppliersDTO> requestParameter = new RequestParameter<>(
                UUID.randomUUID()
                , AppConstants.AD_ID
                , AppConstants.PSW.ID, "03"
                , AppConstants.PSW.METHOD_ID_SHARE_NEG_LIST_OF_SUPPLIERS
                , AppConstants.AD_SIGNATURE);
        requestParameter.setData(dto);

        return executeRequest(requestParameter, "11.3 - Sharing Updated Negative List of Suppliers with PSW");
    }

    /***********************************************************
     5.1.1.	Message 1 – Share Financial Transaction Data with PSW by AD (IMPORT)
     ***********************************************************/
    public ResponseUtility.APIResponse shareFinancialInformationImport(FinancialTransactionImportDTO dto)
            throws HttpClientErrorException, JsonProcessingException {

        if (AppConstants.PAYMENT_MODE.IMP_OPEN_ACCOUNT_VALUE.equals(dto.getModeOfPayment())) {
            dto.setContractCollectionData(null);
            dto.setLcData(null);
            dto.setCashMargin(null);
        } else if (AppConstants.PAYMENT_MODE.IMP_LC_VALUE.equals(dto.getModeOfPayment())) {
            dto.setContractCollectionData(null);
            dto.setOpenAccountData(null);
            dto.setCashMargin(null);
        } else if (AppConstants.PAYMENT_MODE.IMP_CC_VALUE.equals(dto.getModeOfPayment())) {
            dto.setLcData(null);
            dto.setCashMargin(null);
            dto.setOpenAccountData(null);
        }else if (AppConstants.PAYMENT_MODE.IMP_ADV_PAYMENT_VALUE.equals(dto.getModeOfPayment())
        || AppConstants.PAYMENT_MODE.IMP_REMITTANCE_NOT_INVOLVED_VALUE.equals(dto.getModeOfPayment())) {
            dto.setLcData(null);
            dto.setContractCollectionData(null);
            dto.setCashMargin(null);
            dto.setOpenAccountData(null);
        }else if (AppConstants.PAYMENT_MODE.IMP_OPEN_ACCOUNT_CASH_MARGIN_VALUE.equals(dto.getModeOfPayment())) {
            dto.setLcData(null);
            dto.setContractCollectionData(null);
            dto.setOpenAccountData(null);
        }

        if(AppUtility.isEmpty(dto.getPaymentInformation().getBeneficiaryIban())){
            dto.getPaymentInformation().setBeneficiaryIban(null);
        }

        boolean isUpdate = false;
        if(!AppUtility.isEmpty(dto.getFinInsUniqueNumber())
                && AppConstants.RecordStatuses.PUSHED_TO_PSW.equals(dto.getStatus())) {
            isUpdate = true;
        }
        RequestParameter<FinancialTransactionImportDTO> requestParameter = new RequestParameter<>(
                UUID.randomUUID()
                , AppConstants.AD_ID
                , AppConstants.PSW.ID, "03"
                , isUpdate  ? AppConstants.PSW.METHOD_ID_SHARE_FIN_TRANS_DATA_IMPORT_UPDATE
                            : AppConstants.PSW.METHOD_ID_SHARE_FIN_TRANS_DATA_IMPORT_NEW
                , AppConstants.AD_SIGNATURE);
        requestParameter.setData(dto);

        return executeRequest(requestParameter, "5.1.1 – (IMPORT) Share Financial Transaction Data with PSW by AD ");
    }

    /***********************************************************
     5.1.3.	Message 3 – Sharing of BDA Information Import by AD with PSW
     ***********************************************************/
    public ResponseUtility.APIResponse shareBDAInformationImport(BDADTO dto)
            throws HttpClientErrorException, JsonProcessingException {

        boolean isUpdate = false;
        if(!AppUtility.isEmpty(dto.getBdaUniqueIdNumber())
                && AppConstants.RecordStatuses.PUSHED_TO_PSW.equals(dto.getStatus())) {
            isUpdate = true;
        }
        RequestParameter<BDADTO> requestParameter = new RequestParameter<>(
                UUID.randomUUID()
                , AppConstants.AD_ID
                , AppConstants.PSW.ID, "03"
                , AppConstants.AD_SIGNATURE
                ,isUpdate  ? AppConstants.PSW.METHOD_ID_SHARE_BDA_INFO_IMPORT_UPDATE
                : AppConstants.PSW.METHOD_ID_SHARE_BDA_INFO_IMPORT);
        requestParameter.setData(dto);

        return executeRequest(requestParameter, "5.1.3 – Sharing of BDA Information Import by AD with PSW");
    }

    /***********************************************************
     5.2.1.	Message 1 – Share Financial Transaction Data with PSW by AD (Export)
     ***********************************************************/
    public ResponseUtility.APIResponse shareFinancialInformationExport(FinancialTransactionExportDTO dto)
            throws HttpClientErrorException, JsonProcessingException {

        if (AppConstants.PAYMENT_MODE.EXP_OPEN_ACCOUNT_VALUE.equals(dto.getModeOfPayment())) {
            dto.setContractCollectionData(null);
            dto.setLcData(null);
        }
        if (AppConstants.PAYMENT_MODE.EXP_WITH_LC_VALUE.equals(dto.getModeOfPayment())) {
            dto.setContractCollectionData(null);
            dto.setOpenAccountData(null);
        } else if (AppConstants.PAYMENT_MODE.EXP_WITH_OUT_LC_VALUE.equals(dto.getModeOfPayment())) {
            dto.setLcData(null);
            dto.setOpenAccountData(null);
        }else {
            dto.setLcData(null);
            dto.setContractCollectionData(null);
            dto.setOpenAccountData(null);
        }

        if(AppUtility.isEmpty(dto.getPaymentInformation().getConsigneeIban())){
            dto.getPaymentInformation().setConsigneeIban(null);
        }

        boolean isUpdate = false;
        if(!AppUtility.isEmpty(dto.getFinInsUniqueNumber())
                && AppConstants.RecordStatuses.PUSHED_TO_PSW.equals(dto.getStatus())) {
            isUpdate = true;
        }

        RequestParameter<FinancialTransactionExportDTO> requestParameter = new RequestParameter<>(
                UUID.randomUUID()
                , AppConstants.AD_ID
                , AppConstants.PSW.ID, "03"
                , isUpdate ? AppConstants.PSW.METHOD_ID_SHARE_FIN_TRANS_DATA_EXPORT_UPDATE
                            :AppConstants.PSW.METHOD_ID_SHARE_FIN_TRANS_DATA_EXPORT_NEW
                , AppConstants.AD_SIGNATURE);
        requestParameter.setData(dto);

        return executeRequest(requestParameter, "5.2.1 – (Export) Share Financial Transaction Data with PSW by AD ");
    }

    /***********************************************************
     5.2.3.	Message 3 – Sharing of BCA Information Export by AD with PSW
     ***********************************************************/
    public ResponseUtility.APIResponse shareBCAInformationExport(BCADTO dto)
            throws HttpClientErrorException, JsonProcessingException {

        boolean isUpdate = false;
        if(!AppUtility.isEmpty(dto.getBcaUniqueIdNumber())
                && AppConstants.RecordStatuses.PUSHED_TO_PSW.equals(dto.getStatus())) {
            isUpdate = true;
        }

        RequestParameter<BCADTO> requestParameter = new RequestParameter<>(
                UUID.randomUUID()
                , AppConstants.AD_ID
                , AppConstants.PSW.ID, "03"
                , isUpdate ? AppConstants.PSW.METHOD_ID_SHARE_BCA_INFO_EXPORT_UPDATE
                :AppConstants.PSW.METHOD_ID_SHARE_BCA_INFO_EXPORT
                , AppConstants.AD_SIGNATURE);
        requestParameter.setData(dto);

        return executeRequest(requestParameter, "5.2.3 – Sharing of BCA Information Export by AD with PSW");
    }

    /***********************************************************
     6.1 Message 1 – Sharing of GD Clearance Message by AD with PSW (Import/Export):
     ***********************************************************/
    public ResponseUtility.APIResponse shareGDClearanceMsg(GDClearanceDTO dto)
            throws HttpClientErrorException, JsonProcessingException {

        RequestParameter<GDClearanceDTO> requestParameter = new RequestParameter<>(
                UUID.randomUUID()
                , AppConstants.AD_ID
                , AppConstants.PSW.ID, "03"
                , AppConstants.PSW.METHOD_ID_SHARE_GD_CLEARANCE_MESSAGE
                , AppConstants.AD_SIGNATURE);
        requestParameter.setData(dto);

        return executeRequest(requestParameter, "6.1 – Sharing of GD Clearance Message by AD with PSW (Import/Export)");
    }


    /***********************************************************
     7.2.1.	Message 2 – Sharing of Change of Bank request approval/rejection message by AD with PSW
     ***********************************************************/
    public ResponseUtility.APIResponse shareCOBApprovalRejectionMsg(ChangeBankRequestDTO dto)
            throws HttpClientErrorException, JsonProcessingException {

        RequestParameter<ChangeBankRequestDTO> requestParameter = new RequestParameter<>(
                UUID.randomUUID()
                , AppConstants.AD_ID
                , AppConstants.PSW.ID, "03"
                , AppConstants.PSW.METHOD_ID_SHARE_COB_APPROVAL_REJECTION_MESSAGE
                , AppConstants.AD_SIGNATURE);
        requestParameter.setData(dto);

        return executeRequest(requestParameter, "7.2.1 – Sharing of Change of Bank request approval/rejection message by AD with PSW");
    }

    /***********************************************************
     7.4.1.	Message 4 – Message 4 – Sharing of Change of Bank request approval/rejection message
     by other selected AD with PSW

     ***********************************************************/
    public ResponseUtility.APIResponse shareCOBApprovalRejectionMsgByOldAD(ChangeBankRequestDTO dto)
            throws HttpClientErrorException, JsonProcessingException {

        RequestParameter<ChangeBankRequestDTO> requestParameter = new RequestParameter<>(
                UUID.randomUUID()
                , AppConstants.AD_ID
                , AppConstants.PSW.ID, "03"
                , AppConstants.PSW.METHOD_ID_SHARE_COB_APPROVAL_REJECTION_BY_OLD_ADB_MESSAGE
                , AppConstants.AD_SIGNATURE);
        requestParameter.setData(dto);

        return executeRequest(requestParameter, "7.2.1 – Sharing of Change of Bank request approval/rejection message by AD with PSW");
    }

    /***********************************************************
     8.1.	Message 1 – Cancellation of Financial Transaction by AD (Import/Export):
     ***********************************************************/
    public ResponseUtility.APIResponse cancellationOfFinancialTransaction(CancellationOfFTDTO dto)
            throws HttpClientErrorException, JsonProcessingException {

        RequestParameter<CancellationOfFTDTO> requestParameter = new RequestParameter<>(
                UUID.randomUUID()
                , AppConstants.AD_ID
                , AppConstants.PSW.ID, "03"
                , AppConstants.PSW.METHOD_ID_CANCELLATION_OF_FT
                , AppConstants.AD_SIGNATURE);
        requestParameter.setData(dto);

        return executeRequest(requestParameter, "8.1 - Cancellation of Financial Transaction by AD (Import/Export)");
    }

    /***********************************************************
     9.1.	Message 1 – Reversal of BDA/BCA Message by AD to PSW
     ***********************************************************/
     public ResponseUtility.APIResponse reversalOfBdaBca(ReversalOfBdaBcaDTO dto)
     throws HttpClientErrorException, JsonProcessingException {

     RequestParameter<ReversalOfBdaBcaDTO> requestParameter = new RequestParameter<>(
     UUID.randomUUID()
     , AppConstants.AD_ID
     , AppConstants.PSW.ID, "03"
     , AppConstants.PSW.METHOD_ID_REVERSAL_OF_BDA_BCA
     , AppConstants.AD_SIGNATURE);
     requestParameter.setData(dto);

     return executeRequest(requestParameter, "9.1 – Reversal of BDA/BCA Message by AD to PSW");
     }

    /***********************************************************
     10.1 Message 1 – Settlement of Financial Instrument by AD (Import/Export)
     ***********************************************************/
     public ResponseUtility.APIResponse settlementOfFinInstrument(SettelmentOfFIDTO dto)
     throws HttpClientErrorException, JsonProcessingException {

     RequestParameter<SettelmentOfFIDTO> requestParameter = new RequestParameter<>(
     UUID.randomUUID()
     , AppConstants.AD_ID
     , AppConstants.PSW.ID, "03"
     , AppConstants.PSW.METHOD_ID_SETTLEMENT_FIN_INST
     , AppConstants.AD_SIGNATURE);
     requestParameter.setData(dto);

     return executeRequest(requestParameter, "10.1 – Settlement of Financial Instrument by AD (Import/Export)");
     }

     /*********************************************
     *   Private Methods......
     ***********************************************/

    /*********************************************************
     * UTILITY Method to Execute PSW API Request
     *********************************************************/
    private ResponseUtility.APIResponse executeRequest(RequestParameter requestParameter, String messageName) throws JsonProcessingException {

        ResponseUtility.APIResponse apiResponse = null;
        try{
        // fetch toke.
        PSWAuthTokenResponse authTokenResponse = getAuthorizationToken();

        String token = authTokenResponse.getApiToken();
        Date requestTime = AppUtility.getCurrentTimeStamp();
        //Auth completed.

        if (!AppUtility.isEmpty(authTokenResponse.getResponseCode()) && HttpStatus.OK.value() == authTokenResponse.getResponseCode()) {
            UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(
                    AppConstants.PSW.BASE_URL + AppConstants.PSW.API_UPDATE_URL).build();

            log.debug("\n----------- Request Sent----------------");
            log.debug("\nRequestParameter Json:" + requestParameter.toJson());
            log.debug("\n---------------------------");

            apiResponse = postRequest(uriBuilder.toUriString(), token, requestParameter);

            log.debug("\n======API Response TO_STRING===========" + apiResponse.toString());

            logRequestService.saveLogRequest(messageName,RequestMethod.POST.name(), requestParameter,requestTime, apiResponse);

            if (HttpStatus.OK.toString() == apiResponse.getMessage().getCode()) {
                log.debug("\n===================PSW  OK response =================");
            }
        } else {
            log.debug("\n===================PSW AUthentication Failed======== Request Not Forwarded to PSW =================");
            ///   logRequest = LogRequest.buildNewObject("Sharing of Update Information and Payment Mode By AD", RequestMethod.POST.name(),requestParameter, requestTime, authTokenResponse);

        }
        } catch (ResourceAccessException rex ){
            apiResponse = ResponseUtility.APIFailureResponse(rex.getRootCause().getLocalizedMessage());
            log.error(rex.getLocalizedMessage());
            rex.printStackTrace();
        } catch(Exception ex){
            apiResponse = ResponseUtility.APIFailureResponse(ex.getLocalizedMessage());
            log.error(ex.getLocalizedMessage());
            ex.printStackTrace();
        }
        return apiResponse;
    }

    /**
     * Rest Template Post Request Execution...
     *
     * @param uri
     * @param token
     * @param requestParameter
     * @return
     */
    private static ResponseUtility.APIResponse postRequest(String uri, String token, RequestParameter requestParameter) {
        HttpHeaders headers = buildHeaders(token);
        HttpEntity<RequestParameter> request = new HttpEntity<>(requestParameter, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());

        ResponseEntity<ResponseUtility.PSWAPIResponse> response = restTemplate.postForEntity(uri, request, ResponseUtility.PSWAPIResponse.class);
        return response.getBody();
    }

    private static HttpHeaders buildHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AppConstants.HEADER_AUTHORIZATION, "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /*********************************************
     *   Main Methods......
     ***********************************************/

    public static void main(String[] arg) {

        System.out.println("-----Hello----");

        PSWAPIConsumerService pswApiConsumerService = new PSWAPIConsumerService();

        try {
            PSWAuthTokenResponse authTokenResponse = pswApiConsumerService.getAuthorizationToken();
            System.out.println("-------------PSWAuthTokenResponse: " + authTokenResponse);

            AccountDetailDTO accountDetail = new AccountDetailDTO();
            accountDetail.setIban("PK 123213123");
            //   accountDetail.setAccountType("701");

            //    pswApiConsumer.updateAccountAndPMInPWS(accountDetail);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
