package com.infotech.adb.api.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.util.HTTPClientUtils;
import com.infotech.adb.util.PSWAuthTokenResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


@Log4j2

public class PSWClient {

    @Value("${psw.base.url}")
    private static String PSW_BASE_URL = "http://localhost:8080/adb";

    @Value("${psw.auth.api}")
    private static String PSW_AUT_API = "/oauth/token";

    @Value("${psw.oauth.grantType}")
    private static String PSW_AUT_GRANT_TYPE = "client_credentials";

    @Value("${psw.oauth.clientId}")
    private static String PSW_CLIENT_ID = "adb";

    @Value("${psw.oauth.clientSecret}")
    private static String PSW_CLIENT_SECRET = "adb";

    public PSWAuthTokenResponse getAuthorizationToken(String clientID, String clientSecret) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate(
                HTTPClientUtils.getClientHttpRequestFactory(
                        clientID, clientSecret));

        // Set Headers...
        HttpHeaders headers = new HttpHeaders()  ;
        headers.set("Content-Type", "application/x-www-form-urlencoded ");
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        UriComponents builder = UriComponentsBuilder.fromHttpUrl(PSW_BASE_URL + PSW_AUT_API)
                .queryParam("grant_type", PSW_AUT_GRANT_TYPE)
//                .queryParam("username", userName)
//                .queryParam("password", password)
                .build();



        PSWAuthTokenResponse authTokenResponse = new PSWAuthTokenResponse();
        try {
            ResponseEntity<String> result = restTemplate.postForEntity(builder.toUri(), entity, String.class);
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

    public static void main(String[] arg) {

        System.out.println("-----Hello----");

        PSWClient pswClient = new PSWClient();

        try {
            PSWAuthTokenResponse authTokenResponse = pswClient.getAuthorizationToken("adb", "adb");
            System.out.println("-------------PSWAuthTokenResponse: " + authTokenResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
