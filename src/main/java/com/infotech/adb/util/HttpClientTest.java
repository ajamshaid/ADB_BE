package com.infotech.adb.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class HttpClientTest {

//    private String serverPath="https://paymate.pk:7003";
////    private String serverPath="http://localhost:8081";
//
//    public void billInquiry(String userName, String password, String consumerNumber, String bankMnemonic,
//                                                     String reserved) throws IOException {
//
//        final String POST_PARAMS = "{\"Username\": \""+userName+"\"," +
//                "  \"Password\": \""+password+"\"," +
//                "  \"Bank_Mnemonic\": \""+bankMnemonic+"\"," +
//                "  \"Reserved\": \""+reserved+"\"," +"\"Consumer_Number\": \""+consumerNumber+"\"}";
//        System.out.println(POST_PARAMS);
//        URL obj = new URL(serverPath+"/tellermate-api/1link-bill/search");
//        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
//        postConnection.setRequestMethod("POST");
//        postConnection.setRequestProperty("Content-Type", "application/json");
//
//
//        postConnection.setDoOutput(true);
//        OutputStream os = postConnection.getOutputStream();
//        os.write(POST_PARAMS.getBytes());
//        os.flush();
//        os.close();
//
//
//        int responseCode = postConnection.getResponseCode();
//        System.out.println("POST Response Code :  " + responseCode);
//        System.out.println("POST Response Message : " + postConnection.getResponseMessage());
//
//
//        BufferedReader in = new BufferedReader(new InputStreamReader(
//                postConnection.getInputStream()));
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//
//        while ((inputLine = in .readLine()) != null) {
//            response.append(inputLine);
//        } in .close();
//
//        OneLinkBillInquiryResponseDTO inquiryResponseDTO =new ObjectMapper().readValue(response.toString(), OneLinkBillInquiryResponseDTO.class);
//        // print result
//        System.out.println(response.toString());
//        System.out.println(inquiryResponseDTO.getAmountWithinDueDate());
//        return inquiryResponseDTO;
//    }
//
//    public void billPay(String userName, String password, String consumerNumber, String bankMnemonic,
//                                             String reserved, String tranAuthId, String transactionAmount, String tranDate, String tranTime) throws IOException {
//
//        final String POST_PARAMS = "{\"Username\": \""+userName+"\"," +
//                "  \"Password\": \""+password+"\"," +
//                "  \"Bank_Mnemonic\": \""+bankMnemonic+"\"," +
//                "  \"Reserved\": \""+reserved
//                +"\"," +
//                "  \"Tran_Auth_Id\": \""+tranAuthId
//                +"\"," +
//                "  \"Transaction_Amount\": \""+transactionAmount
//                +"\"," +
//                "  \"Tran_Date\": \""+tranDate
//                +"\"," +
//                "  \"Tran_Time\": \""+tranTime+"\"," +"\"Consumer_Number\": \""+consumerNumber+"\"}";
//        System.out.println(POST_PARAMS);
//        URL obj = new URL(serverPath+"/tellermate-api/1link-bill/pay");
//        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
//        postConnection.setRequestMethod("POST");
//        postConnection.setRequestProperty("Content-Type", "application/json");
//        postConnection.setDoOutput(true);
//        OutputStream os = postConnection.getOutputStream();
//        os.write(POST_PARAMS.getBytes());
//        os.flush();
//        os.close();
//
//
//        int responseCode = postConnection.getResponseCode();
//        System.out.println("POST Response Code :  " + responseCode);
//        System.out.println("POST Response Message : " + postConnection.getResponseMessage());
//
//
//        BufferedReader in = new BufferedReader(new InputStreamReader(
//                postConnection.getInputStream()));
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//
//        while ((inputLine = in .readLine()) != null) {
//            response.append(inputLine);
//        } in .close();
//        System.out.println(response.toString());
//        OneLinkPayBillResponseDTO inquiryResponseDTO =new ObjectMapper().readValue(response.toString(), OneLinkPayBillResponseDTO.class);
//        // print result
//
//        return inquiryResponseDTO;
//    }
//
//    public void echoMessage(String userName, String password, String ping) throws IOException {
//
//        final String POST_PARAMS = "{\"Username\": \""+userName+"\"," +
//                "  \"Password\": \""+password+"\"," +
//                "  \"Ping\": \""+ping+"\"}";
//        System.out.println(POST_PARAMS);
//        URL obj = new URL(serverPath+"/tellermate-api/1link-bill/echo");
//        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
//        postConnection.setRequestMethod("POST");
//        postConnection.setRequestProperty("Content-Type", "application/json");
//
//
//        postConnection.setDoOutput(true);
//        OutputStream os = postConnection.getOutputStream();
//        os.write(POST_PARAMS.getBytes());
//        os.flush();
//        os.close();
//
//
//        int responseCode = postConnection.getResponseCode();
//        System.out.println("POST Response Code :  " + responseCode);
//        System.out.println("POST Response Message : " + postConnection.getResponseMessage());
//
//
//        BufferedReader in = new BufferedReader(new InputStreamReader(
//                postConnection.getInputStream()));
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//
//        while ((inputLine = in .readLine()) != null) {
//            response.append(inputLine);
//        } in .close();
//
//        OneLinkPayBillResponseDTO inquiryResponseDTO =new ObjectMapper().readValue(response.toString(), OneLinkPayBillResponseDTO.class);
//        // print result
//        System.out.println(response.toString());
//        return inquiryResponseDTO;
//    }

    public static void updatePaymentModes(){

    }
    public static void updateNegativeCountries(){

    }
    public static void updateNegativeCommodities(){

    }
    public static void updateNegativeSupplier(){

    }

}
