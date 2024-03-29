package com.infotech.adb.util;

import com.infotech.adb.dto.RequestParameter;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

public class HTTPClientUtils {
    public static final String AUTHORIZATION = "Authorization";

    public static HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory(String userName, String pass)
    {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(httpClient(userName, pass));

        return clientHttpRequestFactory;
    }

    private static HttpClient httpClient(String userName, String pass)
    {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(userName,pass));

        HttpClient client = HttpClientBuilder
                .create()
                .setDefaultCredentialsProvider(credentialsProvider)
                .build();
        return client;
    }


    /************************************
     *
     * @param uri
     * @param token
     * @return
     */

    private static ResponseUtility.APIResponse getRequest(String uri, String token) {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ResponseUtility.APIResponse> responseEntity = restTemplate.exchange
                (uri, HttpMethod.GET, new HttpEntity<>(getHeaders(token)), ResponseUtility.APIResponse.class);

        return responseEntity.getBody();
    }

   public static ResponseUtility.APIResponse postRequest(String uri, String token, RequestParameter requestParameter) {
        HttpHeaders headers = getHeaders(token);
        HttpEntity<RequestParameter> request = new HttpEntity<>(requestParameter, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ResponseUtility.APIResponse> response = restTemplate.postForEntity(uri, request, ResponseUtility.APIResponse.class);
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