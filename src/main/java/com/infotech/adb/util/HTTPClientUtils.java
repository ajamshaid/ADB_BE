package com.infotech.adb.util;

import com.infotech.adb.dto.RequestParameter;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

public class HTTPClientUtils {
    public static final String AUTHORIZATION = "Authorization";

//    @Value("${app.base.url}")
    private static String BASE_URL = "http://localhost:8081/adb/psw";


    private static ResponseUtility.APIResponse getRequest(String uri, String token) {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ResponseUtility.APIResponse> responseEntity = restTemplate.exchange
                (uri, HttpMethod.GET, new HttpEntity<>(getHeaders(token)), ResponseUtility.APIResponse.class);

        return responseEntity.getBody();
    }

//    public static ResponseUtility.APIResponse postRequest(String uri, String token, RequestParameter requestParameter) {
//        HttpHeaders headers = getHeaders(token);
//        HttpEntity<RequestParameter<IBANVerificationRequest>> request = new HttpEntity<>(requestParameter, headers);
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<ResponseUtility.APIResponse> response = restTemplate.postForEntity(baseUrl + uri, request, ResponseUtility.APIResponse.class);
//        return response.getBody();
//    }
//
//    public static ResponseUtility.APIResponse updatePaymentModes(String uri, String token) {
//        RequestParameter<IBANVerificationRequest> requestParameter =
//                new RequestParameter<>();
//        requestParameter.setMessageId(UUID.randomUUID());
//        IBANVerificationRequest data = new IBANVerificationRequest();
//        data.setIban("PK36SCBL0010001123456005");
//        data.setEmail("abc@psw.gov.pk");
//        data.setMobileNumber("03451234567");
//        requestParameter.setData(data);
//        return postRequest(uri,token,requestParameter);
//    }

    public static ResponseUtility.APIResponse postRequest(String uri, String token, RequestParameter requestParameter) {
        HttpHeaders headers = getHeaders(token);
        HttpEntity<RequestParameter> request = new HttpEntity<>(requestParameter, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ResponseUtility.APIResponse> response = restTemplate.postForEntity(BASE_URL + uri, request, ResponseUtility.APIResponse.class);
        return response.getBody();
    }

    private static HttpHeaders getHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public static HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};
    }

    public static void main(String[] args) {

    }
}