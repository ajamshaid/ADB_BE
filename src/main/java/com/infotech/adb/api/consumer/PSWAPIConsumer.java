package com.infotech.adb.api.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.config.RestTemplateResponseErrorHandler_ToDel;
import com.infotech.adb.dto.*;
import com.infotech.adb.model.entity.LogRequest;
import com.infotech.adb.service.LogRequestService;
import com.infotech.adb.util.AppConstants;
import com.infotech.adb.util.HTTPClientUtils;
import com.infotech.adb.util.PSWAuthTokenResponse;
import com.infotech.adb.util.ResponseUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.ZonedDateTime;
import java.util.UUID;


@Log4j2
@Service
public class PSWAPIConsumer {

    @Autowired
    private LogRequestService logRequestService;

    //**************************
    // Get Authorization Token
    // **************************/
    public PSWAuthTokenResponse getAuthorizationToken() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate(
                HTTPClientUtils.getClientHttpRequestFactory(
                        AppConstants.PSW.CLIENT_ID, AppConstants.PSW.CLIENT_SECRET));

        System.out.println("AppConstants.PSW.BASE_URL :"+AppConstants.PSW.BASE_URL);

        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(
                AppConstants.PSW.BASE_URL + AppConstants.PSW.API_AUTH).build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("grant_type", AppConstants.PSW.AUT_GRANT_TYPE);
        map.add("client_id", AppConstants.PSW.CLIENT_ID);
        map.add("client_secret", AppConstants.PSW.CLIENT_SECRET);

        HttpEntity<MultiValueMap<String, String>> request
                = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        PSWAuthTokenResponse authTokenResponse = new PSWAuthTokenResponse();
        try {
            ResponseEntity<String> result = restTemplate.postForEntity(uriBuilder.toUri(), request, String.class);

            System.out.println("________________Status Code:" + result.getStatusCode());
            log.debug("________________Status Code:" + result.getStatusCode());
            log.debug("________________Body:" + result.getBody());

            authTokenResponse = PSWAuthTokenResponse.jsonToObject(result.getBody());
            authTokenResponse.setResponseCode(result.getStatusCodeValue());
            authTokenResponse.setMessage("Authentication Successful.");

            System.out.println("------------------AuthTokem Response"+authTokenResponse);
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

        return executeRequest(requestParameter, "Sharing of Update Information and Payment Mode By AD");
    }


    /***********************************************************
     4.4
     Message 4 – Trader Profile Active/Inactive Message by AD to PSW
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

        return executeRequest(requestParameter, "Trader Profile Active/Inactive Message by AD to PSW");
    }



    /***********************************************************
     11.1
     Message 1 – Sharing negative list of countries by AD with PSW / Sharing
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

        return executeRequest(requestParameter, "Sharing Updated Negative List of Countries with PSW");
    }

    /***********************************************************
    11.2
     Message 2 – Sharing Negative List of Commodities by AD with PSW/ Sharing
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

        return executeRequest(requestParameter, "Sharing Updated Negative List of Commodities with PSW");
    }
    /***********************************************************
     11.3
     Message 3 – Sharing Negative List of Suppliers by AD with PSW/ Sharing
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

        return executeRequest(requestParameter, "Sharing Updated Negative List of Suppliers with PSW");
    }

    /***********************************************************
     5.1.1.	Message 1 – Share Financial Transaction Data with PSW by AD (IMPORT)
     ***********************************************************/
     public ResponseUtility.APIResponse shareFinancialInformationImport(FinancialTransactionImportDTO dto)
     throws HttpClientErrorException, JsonProcessingException {

     RequestParameter<FinancialTransactionImportDTO> requestParameter = new RequestParameter<>(
     UUID.randomUUID()
     , AppConstants.AD_ID
     , AppConstants.PSW.ID, "03"
     , AppConstants.PSW.METHOD_ID_SHARE_FIN_TRANS_DATA_IMPORT
     , AppConstants.AD_SIGNATURE);
     requestParameter.setData(dto);

     return executeRequest(requestParameter, "Sharing Financial Transaction Data For Import with PSW by AD");
     }

    /***********************************************************
     5.2.1.	Message 1 – Share Financial Transaction Data with PSW by AD (Export)
     ***********************************************************/
     public ResponseUtility.APIResponse shareFinancialInformationExport(FinancialTransactionExportDTO dto)
     throws HttpClientErrorException, JsonProcessingException {

     RequestParameter<FinancialTransactionExportDTO> requestParameter = new RequestParameter<>(
     UUID.randomUUID()
     , AppConstants.AD_ID
     , AppConstants.PSW.ID, "03"
     , AppConstants.PSW.METHOD_ID_SHARE_FIN_TRANS_DATA_EXPORT
     , AppConstants.AD_SIGNATURE);
     requestParameter.setData(dto);

     return executeRequest(requestParameter, "Sharing Financial Transaction Data for Export with PSW by AD");
     }

    /***********************************************************
     4.11.	Message 11 – Update in Trader’s Email and Mobile Number Message by AD to PSW
     ***********************************************************
    public ResponseUtility.APIResponse updateTraderProfile(TraderProfileDTO dto)
            throws HttpClientErrorException, JsonProcessingException {

        RequestParameter<TraderProfileDTO> requestParameter = new RequestParameter<>(
                 UUID.randomUUID()
                , AppConstants.AD_ID
                , AppConstants.PSW.ID, "03"
                , AppConstants.PSW.METHOD_ID_UPDATE_TRADERS_EMAIL_MOB
                , AppConstants.AD_SIGNATURE);
        requestParameter.setData(dto);

        return executeRequest(requestParameter, "Update in Trader’s Email and Mobile Number Message by AD to PSW");
    }


    /***********************************************************
     5.1.1.	Message 1 – Share Financial Transaction Data with PSW by AD (IMPORT)
     ***********************************************************
    public ResponseUtility.APIResponse shareFinancialInformationImport(FinancialTransactionImportDTO dto)
            throws HttpClientErrorException, JsonProcessingException {

        RequestParameter<FinancialTransactionImportDTO> requestParameter = new RequestParameter<>(
                 UUID.randomUUID()
                , AppConstants.AD_ID
                , AppConstants.PSW.ID, "03"
                , AppConstants.PSW.METHOD_ID_SHARE_FIN_TRANS_DATA_IMPORT
                , AppConstants.AD_SIGNATURE);
        requestParameter.setData(dto);

        return executeRequest(requestParameter, "Sharing Financial Transaction Data For Import with PSW by AD");
    }


    /***********************************************************
     5.1.3.	Message 3 – Sharing of BDA Information Import by AD with PSW
     ***********************************************************
    public ResponseUtility.APIResponse shareBDAInformationImport(BDAImportDTO dto)
            throws HttpClientErrorException, JsonProcessingException {

        RequestParameter<BDAImportDTO> requestParameter = new RequestParameter<>(
                 UUID.randomUUID()
                , AppConstants.AD_ID
                , AppConstants.PSW.ID, "03"
                , AppConstants.PSW.METHOD_ID_SHARE_BDA_INFO_IMPORT
                , AppConstants.AD_SIGNATURE);
        requestParameter.setData(dto);

        return executeRequest(requestParameter, "Sharing of BDA Information [Import] by AD with PSW");
    }





    /***********************************************************
     5.2.3.	Message 3 – Sharing of BCA Information Export by AD with PSW
     ***********************************************************/
    public ResponseUtility.APIResponse shareBCAInformationExport(BCADTO dto)
            throws HttpClientErrorException, JsonProcessingException {

        RequestParameter<BCADTO> requestParameter = new RequestParameter<>(
                 UUID.randomUUID()
                , AppConstants.AD_ID
                , AppConstants.PSW.ID, "03"
                , AppConstants.PSW.METHOD_ID_SHARE_BCA_INFO_EXPORT
                , AppConstants.AD_SIGNATURE);
        requestParameter.setData(dto);

        return executeRequest(requestParameter, "Sharing of BCA Information [Export] by AD with PSW");
    }


    /***********************************************************
     6.	Sharing of Cash Margin Message by AD to PSW for Payment Mode - Open Account
     ***********************************************************/
    public ResponseUtility.APIResponse shareCashMarginMessage(CashMarginDTO dto)
            throws HttpClientErrorException, JsonProcessingException {

        RequestParameter<CashMarginDTO> requestParameter = new RequestParameter<>(
                 UUID.randomUUID()
                , AppConstants.AD_ID
                , AppConstants.PSW.ID, "03"
                , AppConstants.PSW.METHOD_ID_SHARE_CASH_MARGIN_MESSAGE
                , AppConstants.AD_SIGNATURE);
        requestParameter.setData(dto);

        return executeRequest(requestParameter, "Sharing of Cash Margin Message by AD to PSW for Payment Mode - Open Account");
    }


    /***********************************************************
     7.1.3.	Message 1 – Sharing of Change of Bank request approval/rejection message by AD with PSW
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

        return executeRequest(requestParameter, "Sharing of Change of Bank request approval/rejection message by AD with PSW");
    }


    /***********************************************************
     9.1.	Message 1 – Cancellation of Financial Transaction by AD (Import/Export):
     ***********************************************************/
    public ResponseUtility.APIResponse cancelFinancialTransaction(TradeTransactionDTO dto)
            throws HttpClientErrorException, JsonProcessingException {

        RequestParameter<TradeTransactionDTO> requestParameter = new RequestParameter<>(
                 UUID.randomUUID()
                , AppConstants.AD_ID
                , AppConstants.PSW.ID, "03"
                , AppConstants.PSW.METHOD_ID_FIN_TRANS_CANCELLATION
                , AppConstants.AD_SIGNATURE);
        requestParameter.setData(dto);

        return executeRequest(requestParameter, "Cancellation of Financial Transaction by AD (Import/Export)");
    }

    /***********************************************************
     10.1.	Message 1 – – Reversal of BDA/BCA Message by AD to PSW
     ***********************************************************/
    public ResponseUtility.APIResponse reversalOfBdaBca(TradeTransBDAInfoDTO dto)
            throws HttpClientErrorException, JsonProcessingException {

        RequestParameter<TradeTransBDAInfoDTO> requestParameter = new RequestParameter<>(
                 UUID.randomUUID()
                , AppConstants.AD_ID
                , AppConstants.PSW.ID, "03"
                , AppConstants.PSW.METHOD_ID_REVERSAL_OF_BDA_BCA
                , AppConstants.AD_SIGNATURE);
        requestParameter.setData(dto);

        return executeRequest(requestParameter, "– Reversal of BDA/BCA Message by AD to PSW");
    }

    /***********************************************************
     11.1.	Message 1  – Settlement of Financial Transaction by AD (Import/Export):
     ***********************************************************/
    public ResponseUtility.APIResponse settlementOfFinTrans(TradeTransSettlementDTO dto)
            throws HttpClientErrorException, JsonProcessingException {

        RequestParameter<TradeTransSettlementDTO> requestParameter = new RequestParameter<>(
                 UUID.randomUUID()
                , AppConstants.AD_ID
                , AppConstants.PSW.ID, "03"
                , AppConstants.PSW.METHOD_ID_FIN_TRANS_SETTLEMENT
                , AppConstants.AD_SIGNATURE);
        requestParameter.setData(dto);

        return executeRequest(requestParameter, "– Settlement of Financial Transaction by AD (Import/Export):");
    }


    /*********************************************
     *   Private Methods......
     ***********************************************/

    /*********************************************************
     * UTILITY Method to Execute PSW API Request
     *********************************************************/
    private ResponseUtility.APIResponse executeRequest(RequestParameter requestParameter, String messageName) throws JsonProcessingException {

        // fetch toke.
        PSWAuthTokenResponse authTokenResponse = getAuthorizationToken();

        String token = authTokenResponse.getApiToken();
        ResponseUtility.APIResponse apiResponse = null;

        ZonedDateTime requestTime = ZonedDateTime.now();
        LogRequest logRequest = null;
        //Auth completed.
        if (HttpStatus.OK.value() == authTokenResponse.getResponseCode()) {
            UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(
                    AppConstants.PSW.BASE_URL + AppConstants.PSW.API_UPDATE_URL).build();

            apiResponse = postRequest(uriBuilder.toUriString(), token, requestParameter);

            System.out.println("=================" + apiResponse);

            if(HttpStatus.OK.toString() == apiResponse.getResponseCode()) {
                logRequest = LogRequest.buildNewObject(messageName, RequestMethod.POST.name(), requestParameter, requestTime, apiResponse);
                logRequestService.createLogRequest(logRequest);
            }
        } else {
            ///   logRequest = LogRequest.buildNewObject("Sharing of Update Information and Payment Mode By AD", RequestMethod.POST.name(),requestParameter, requestTime, authTokenResponse);

        }
        return apiResponse;
    }

    /**
     * Rest Template Post Request Execution...
     * @param uri
     * @param token
     * @param requestParameter
     * @return
     */
    private static ResponseUtility.APIResponse postRequest(String uri, String token, RequestParameter requestParameter) {
        HttpHeaders headers = buildHeaders(token);
        HttpEntity<RequestParameter> request = new HttpEntity<>(requestParameter, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler_ToDel());

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

        PSWAPIConsumer pswApiConsumer = new PSWAPIConsumer();

        try {
            PSWAuthTokenResponse authTokenResponse = pswApiConsumer.getAuthorizationToken();
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
