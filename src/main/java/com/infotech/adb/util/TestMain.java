package com.infotech.adb.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infotech.adb.model.entity.FinancialTransaction;
import com.infotech.adb.model.entity.ItemInformation;
import com.infotech.adb.model.entity.PaymentInformation;
import com.infotech.adb.model.entity.ReversalOfBdaBca;
import com.infotech.adb.silkbank.jms.MQMessageParser;
import com.infotech.adb.silkbank.jms.MQUtility;
import com.opencsv.exceptions.CsvException;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class TestMain {
    public static final int SET_SIZE_REQUIRED = 10;
    public static final int NUMBER_RANGE = 100;

    public static void main(String args[]) throws CsvException, FileNotFoundException, JsonProcessingException, JSONException {

        MQMessageParser parser = new MQMessageParser();


        String data = "1762423-1|SWIFT BIZ SOLUTIONS  PVT  LIMITED|PK51SAUD0000362001300612|301|TF2103368462||YITONG CHEMICAL ||3000.00|USD|160.4|TF2103368462|8517.1219^1063.110|20||CHINA||YITONG CHEMICAL |CHINA|CFR|20210920|20211030";

        parser.parseAndBuildFTImport(data);

        System.out.println("----------------Done--------------");

    }

    public static void testingSignature() throws JsonProcessingException, JSONException {
        StringBuffer sb = new StringBuffer("");

        File myObj = new File("C://Json.txt");
        try{
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
             sb.append(myReader.nextLine());
//            System.out.println("++++++++++++++++"+s);
        }
        myReader.close();
    } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
    }

        System.out.println("+++++++++"+sb.toString());


        ObjectMapper objectMapper = new ObjectMapper();

        String json1 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(sb.toString());


        JSONObject obj = new JSONObject(sb.toString());
        String data = obj.getJSONObject("data").toString();

        System.out.println("-------------Data "+data.toString());
        System.out.println("Signature is:"+AppUtility.buildSignature(data.toString()));



//        Trade Type|Trader NTN|Trader Name|IBAN|Financial Instrument Unique No|BDA/BCA Unique Identification No


//        MSG.TYPE!UNIQUE.ID!Trade Type|Trader NTN|Trader Name|IBAN|Financial Instrument Unique No
  //      PSW811!PSW811TF210056793120210202!01|1336346-8|MOHID INDUSTRIES|PK87SAUD0000032000633154|SBP-IMP-000001-23042021



        String msg =  "02|1336346-8|MOHID INDUSTRIES|PK87SAUD0000032000633154|SBP-IMP-000001-23042021|SBP-BCA-000001-23042021";

       // System.out.println( "---"+parseAndBuildRevBDABCA(msg).toString());

    }


}
