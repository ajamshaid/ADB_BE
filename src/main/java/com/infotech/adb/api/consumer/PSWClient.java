package com.infotech.adb.api.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.config.RestTemplateResponseErrorHandler;
import com.infotech.adb.dto.AccountDetailDTO;
import com.infotech.adb.dto.RequestParameter;
import com.infotech.adb.model.entity.AccountDetail;
import com.infotech.adb.util.AppConstants;
import com.infotech.adb.util.HTTPClientUtils;
import com.infotech.adb.util.PSWAuthTokenResponse;
import com.infotech.adb.util.ResponseUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


@Log4j2
@Service
public class PSWClient {
    public PSWAuthTokenResponse getAuthorizationToken(String clientID, String clientSecret) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate(
                HTTPClientUtils.getClientHttpRequestFactory(
                        clientID, clientSecret));

        // Set Headers...
        HttpHeaders headers = new HttpHeaders()  ;
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
        }catch (HttpStatusCodeException ex){
            HttpStatus statusCode = ex.getStatusCode();
           if(HttpStatus.UNAUTHORIZED == statusCode){
               authTokenResponse.setResponseCode(statusCode.value());
               authTokenResponse.setMessage(ex.getLocalizedMessage());
           }
        }
        return authTokenResponse ;
    }

    public ResponseUtility.APIResponse updateAccountAndPMInPWS(AccountDetail accountDetail)
            throws HttpClientErrorException , JsonProcessingException {

        RequestParameter<AccountDetailDTO> requestParameter = new RequestParameter<>(
                AppConstants.MESSAGE_GUID.MSG_UPDATE_ACCT_INFO_PAYMENT_MODE,
                AppConstants.AD_ID, AppConstants.PSW.ID, "03", AppConstants.PSW.METHOD_ID_UPDATE_INFO_AND_PM
                , AppConstants.AD_SIGNATURE);

        AccountDetailDTO dto = new AccountDetailDTO(accountDetail);
        requestParameter.setData(dto);

        PSWAuthTokenResponse authTokenResponse = getAuthorizationToken(AppConstants.PSW.CLIENT_ID, AppConstants.PSW.CLIENT_SECRET);
        String token = authTokenResponse.getApiToken();
        ResponseUtility.APIResponse apiResponse = null;
        if (HttpStatus.OK.value() == authTokenResponse.getResponseCode()) {
            UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(
                    AppConstants.PSW.BASE_URL + AppConstants.PSW.API_UPDATE_URL).build();
             apiResponse = postRequest(uriBuilder.toUriString(), token, requestParameter);

            System.out.println("================="+ apiResponse);
        }
        return apiResponse;
    }


    /*********************************************
     *   UTILITY Methods......
     ***********************************************/

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
        headers.add(AppConstants.HEADER_AUTHORIZATION, "Bearer "+token);
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
