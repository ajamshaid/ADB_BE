package com.infotech.adb.api.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.config.RestTemplateResponseErrorHandler;
import com.infotech.adb.dto.*;
import com.infotech.adb.model.entity.AccountDetail;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.ZonedDateTime;


@Log4j2
@Service
public class PSWClient {

    @Autowired
    private LogRequestService logRequestService;

    //**************************
    // Get Authorization Token
    // **************************/
    public PSWAuthTokenResponse getAuthorizationToken(String clientID, String clientSecret) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate(
                HTTPClientUtils.getClientHttpRequestFactory(
                        clientID, clientSecret));

        // Set Headers...
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded ");
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(AppConstants.PSW.BASE_URL + AppConstants.PSW.API_AUTH)
                .queryParam("grant_type", AppConstants.PSW.AUT_GRANT_TYPE)
//                .queryParam("username", userName)
//                .queryParam("password", password)
                .build();


        PSWAuthTokenResponse authTokenResponse = new PSWAuthTokenResponse();
        try {
            ResponseEntity<String> result = restTemplate.postForEntity(uriBuilder.toUri(), entity, String.class);
            log.debug("________________Status Code:" + result.getStatusCode());
            log.debug("________________Body:" + result.getBody());

            //        JSONObject jsonObject = new JSONObject(result.getBody());
//        String accessToken = jsonObject.getString("access_token");

            authTokenResponse = PSWAuthTokenResponse.jsonToObject(result.getBody());
            authTokenResponse.setResponseCode(result.getStatusCodeValue());
            authTokenResponse.setMessage("Authentication Successful.");
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
     4.6.	Message 6 – Sharing of Updated Information and Authorized Payment Modes by AD with PSW
     ********************************************************************/
    public ResponseUtility.APIResponse updateAccountAndPMInPWS(AccountDetail accountDetail)
            throws HttpClientErrorException, JsonProcessingException {

        RequestParameter<AccountDetailDTO> requestParameter = new RequestParameter<>(
                AppConstants.MESSAGE_GUID.MSG_UPDATE_ACCT_INFO_PAYMENT_MODE,
                AppConstants.AD_ID, AppConstants.PSW.ID, "03"
                , AppConstants.PSW.METHOD_ID_UPDATE_INFO_AND_PM
                , AppConstants.AD_SIGNATURE);

        AccountDetailDTO dto = new AccountDetailDTO(accountDetail);
        requestParameter.setData(dto);

        return executeRequest(requestParameter, "Sharing of Update Information and Payment Mode By AD");
    }

    /***********************************************************
     4.7.	Message 7 – Sharing Updated Negative List of Countries with PSW
     ***********************************************************/
    public ResponseUtility.APIResponse updateRestrictedListOFCountries(RestrictedCountriesDTO restrictedDTO)
            throws HttpClientErrorException, JsonProcessingException {

        RequestParameter<RestrictedCountriesDTO> requestParameter = new RequestParameter<>(
                AppConstants.MESSAGE_GUID.MSG_UPDATE_GUID
                , AppConstants.AD_ID
                , AppConstants.PSW.ID, "03"
                , AppConstants.PSW.METHOD_ID_UPDATE_RESTRICTED_COUNTRIES
                , AppConstants.AD_SIGNATURE);
        requestParameter.setData(restrictedDTO);

        return executeRequest(requestParameter, "Sharing Updated Negative List of Countries with PSW");
    }

    /***********************************************************
     4.8.	Message 8 – Sharing Updated Negative List of Commodities with PSW
     ***********************************************************/
    public ResponseUtility.APIResponse updateRestrictedListOFCommodities(RestrictedCommoditiesDTO dto)
            throws HttpClientErrorException, JsonProcessingException {

        RequestParameter<RestrictedCommoditiesDTO> requestParameter = new RequestParameter<>(
                AppConstants.MESSAGE_GUID.MSG_UPDATE_GUID
                , AppConstants.AD_ID
                , AppConstants.PSW.ID, "03"
                , AppConstants.PSW.METHOD_ID_UPDATE_RESTRICTED_COMMODITIES
                , AppConstants.AD_SIGNATURE);
        requestParameter.setData(dto);

        return executeRequest(requestParameter, "Sharing Updated Negative List of Commodities with PSW");
    }
    /***********************************************************
     4.9.	Message 9 – Sharing Updated Negative List of Suppliers with PSW
     ***********************************************************/
    public ResponseUtility.APIResponse updateRestrictedListOFSuppliers(RestrictedSuppliersDTO dto)
            throws HttpClientErrorException, JsonProcessingException {

        RequestParameter<RestrictedSuppliersDTO> requestParameter = new RequestParameter<>(
                AppConstants.MESSAGE_GUID.MSG_UPDATE_GUID
                , AppConstants.AD_ID
                , AppConstants.PSW.ID, "03"
                , AppConstants.PSW.METHOD_ID_UPDATE_RESTRICTED_SUPPLIERS
                , AppConstants.AD_SIGNATURE);
        requestParameter.setData(dto);

        return executeRequest(requestParameter, "Sharing Updated Negative List of Suppliers with PSW");
    }

    /***********************************************************
     4.10.	Message 10 – Trader Profile Active/Inactive Message by AD to PSW
     ***********************************************************/
    public ResponseUtility.APIResponse updateTraderProfileAccountStatus(TraderProfileStatusDTO dto)
            throws HttpClientErrorException, JsonProcessingException {

        RequestParameter<TraderProfileStatusDTO> requestParameter = new RequestParameter<>(
                AppConstants.MESSAGE_GUID.MSG_UPDATE_GUID
                , AppConstants.AD_ID
                , AppConstants.PSW.ID, "03"
                , AppConstants.PSW.METHOD_ID_UPDATE_TRADER_ACCT_STATUS
                , AppConstants.AD_SIGNATURE);
        requestParameter.setData(dto);

        return executeRequest(requestParameter, "Trader Profile Active/Inactive Message by AD to PSW");
    }


    /***********************************************************
     4.11.	Message 11 – Update in Trader’s Email and Mobile Number Message by AD to PSW
     ***********************************************************/
    public ResponseUtility.APIResponse updateTraderProfile(TraderProfileDTO dto)
            throws HttpClientErrorException, JsonProcessingException {

        RequestParameter<TraderProfileDTO> requestParameter = new RequestParameter<>(
                AppConstants.MESSAGE_GUID.MSG_UPDATE_GUID
                , AppConstants.AD_ID
                , AppConstants.PSW.ID, "03"
                , AppConstants.PSW.METHOD_ID_UPDATE_TRADERS_EMAIL_MOB
                , AppConstants.AD_SIGNATURE);
        requestParameter.setData(dto);

        return executeRequest(requestParameter, "Update in Trader’s Email and Mobile Number Message by AD to PSW");
    }


    /***********************************************************
     5.1.1.	Message 1 – Share Financial Transaction Data with PSW by AD
     ***********************************************************/
    public ResponseUtility.APIResponse shareFinancialInformation(FinancialTransactionDTO dto)
            throws HttpClientErrorException, JsonProcessingException {

        RequestParameter<FinancialTransactionDTO> requestParameter = new RequestParameter<>(
                AppConstants.MESSAGE_GUID.MSG_UPDATE_GUID
                , AppConstants.AD_ID
                , AppConstants.PSW.ID, "03"
                , AppConstants.PSW.METHOD_ID_SHARE_FIN_TRANS_DATA_IMPORT
                , AppConstants.AD_SIGNATURE);
        requestParameter.setData(dto);

        return executeRequest(requestParameter, "Sharing Financial Transaction Data with PSW by AD");
    }




    /*********************************************
     *   Private Methods......
     ***********************************************/

    /*********************************************************
     * UTILITY Method to Execute PSW API Request
     *********************************************************/
    private ResponseUtility.APIResponse executeRequest(RequestParameter requestParameter, String messageName) throws JsonProcessingException {

        // fetch toke.
        PSWAuthTokenResponse authTokenResponse = getAuthorizationToken(AppConstants.PSW.CLIENT_ID, AppConstants.PSW.CLIENT_SECRET);
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
            logRequest = LogRequest.buildNewObject(messageName, RequestMethod.POST.name(), requestParameter, requestTime, apiResponse);
            logRequestService.createLogRequest(logRequest);
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

        PSWClient pswClient = new PSWClient();

        try {
//            PSWAuthTokenResponse authTokenResponse = pswClient.getAuthorizationToken("adb", "adb");
//            System.out.println("-------------PSWAuthTokenResponse: " + authTokenResponse);

            AccountDetail accountDetail = new AccountDetail();
            accountDetail.setIban("PK 123213123");
            accountDetail.setAccountType("701");

            pswClient.updateAccountAndPMInPWS(accountDetail);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
