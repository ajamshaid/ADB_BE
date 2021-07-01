package com.infotech.adb.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.mq.*;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.constants.MQConstants;
import com.opencsv.exceptions.CsvException;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.UUID;

public class TestMain {
    public static final int SET_SIZE_REQUIRED = 10;
    public static final int NUMBER_RANGE = 100;

    public static void main(String args[]) throws CsvException, FileNotFoundException, JsonProcessingException, JSONException {

        String reqBodyStr2 = "{\n" +
                "   \"messageId\":\"a1374655-5eb8-4a0e-9eb5-989521cd1ca8\",\n" +
                "   \"timestamp\":\"20200925183412\",\n" +
                "   \"senderId\":\"PSW\",\n" +
                "   \"receiverId\":\"SLB\",\n" +
                "   \"processingCode\":\"101\",\n" +
                "   \"data\":{\n" +
                "      \"gdNumber\":\"KPPI-HC-86-03-09-2020\",\n" +
                "      \"consignmentCategory\":\"Commercial\",\n" +
                "      \"gdStatus\": \"03\",\n" +
                "      \"gdType\":\"Home Consumption\",\n" +
                "      \"collectorate\":\"Qasim International Container Terminal\",\n" +
                "      \"blAwbNumber\":\"BL-24923231\",\n" +
                "      \"blAwbDate\":\"20201012\",\n" +
                "      \"virAirNumber\":\"KEWB-0005-010112020\",\n" +
                "      \"consignorConsigneeInfo\":{\n" +
                "         \"ntnFtn\":\"0425425\",\n" +
                "         \"strn\":\"1700003019489\",\n" +
                "         \"consigneeName\":\"PSW\",\n" +
                "         \"consigneeAddress\":\"PECHS\",\n" +
                "         \"consignorName\":\"M/S. International Jute Traders\",\n" +
                "         \"consignorAddress\":\"95, MOTIJHEEL COMMERCIAL AREA (2ND FLOOR) BANGLADESH.\"\n" +
                "      },\n" +
                "      \"financialInfo\":{\n" +
                "            \"importerIban\": \"PK88SAUD0000032000486666\",\n" +
                "            \"modeOfPayment\": \"301\",\n" +
                "            \"finInsUniqueNumber\": \"SLB-IMP-000002-29062021\",\n" +
                "            \"currency\": \"USD\",\n" +
                "            \"invoiceNumber\": \"AS1234567\",\n" +
                "            \"invoiceDate\": \"20200223\",\n" +
                "            \"totalDeclaredValue\": 5000.0002,\n" +
                "            \"deliveryTerm\": \"CFR\",\n" +
                "            \"fobValueUsd\": 1.0000,\n" +
                "            \"freightUsd\": 1.0000,\n" +
                "            \"cfrValueUsd\": 1.0000,\n" +
                "            \"insuranceUsd\": 0.0000,\n" +
                "            \"landingChargesUsd\": 100.0000,\n" +
                "            \"assessedValueUsd\": 50.0000,\n" +
                "            \"otherCharges\": 0.0000,\n" +
                "            \"exchangeRate\": 158.0000\n" +
                "\n" +
                "      },\n" +
                "      \"generalInformation\":{\n" +
                "         \"packagesInformation\":[\n" +
                "            {\n" +
                "               \"numberOfPackages\":100,\n" +
                "               \"packageType\":\"Box\"\n" +
                "            },\n" +
                "            {\n" +
                "               \"numberOfPackages\":100,\n" +
                "               \"packageType\":\"BULK\"\n" +
                "            }\n" +
                "         ],\n" +
                "         \"containerVehicleInformation\":[\n" +
                "            {\n" +
                "               \"containerOrTruckNumber\":\"ASF9999991\",\n" +
                "               \"sealNumber\":\"SL2674\",\n" +
                "               \"containerType\":\"20FT\"\n" +
                "            },\n" +
                "            {\n" +
                "               \"containerOrTruckNumber\":\"ASF9999992\",\n" +
                "               \"sealNumber\":\"SL2674\",\n" +
                "               \"containerType\":\"40FT\"\n" +
                "            }\n" +
                "         ],\n" +
                "         \"netWeight\":\"1.89400 MT\",\n" +
                "         \"grossWeight\":\"1.11400 MT\",\n" +
                "         \"portOfShipment\":\"AUCHN\",\n" +
                "         \"portOfDelivery\":\"AUCHN\",\n" +
                "         \"portOfDischarge\":\"AUCHN\",\n" +
                "         \"terminalLocation\":\"Qasim International Container Terminal\"\n" +
                "      },\n" +
                "      \"itemInformation\":[\n" +
                "         {\n" +
                "            \"hsCode\":\"8446.1000\",\n" +
                "            \"quantity\":6.0000,\n" +
                "            \"unitPrice\":20,\n" +
                "            \"totalValue\":120,\n" +
                "            \"importValue\":120,\n" +
                "            \"uom\":\"KG\"\n" +
                "         },\n" +
                "         {\n" +
                "            \"hsCode\":\"8446.1000\",\n" +
                "            \"quantity\":6.0000,\n" +
                "            \"unitPrice\":20,\n" +
                "            \"totalValue\":120,\n" +
                "            \"importValue\":120,\n" +
                "            \"uom\":\"KG\"\n" +
                "         }\n" +
                "      ]\n" +
                "   },\n" +
                "   \"signature\":\"/HrgvYW2sIYK93YXqd46/KhXJ7unoId7vRkAdJmrs7Y=\"\n" +
                "}";





        String msg2 ="{\n" +
                "   \"messageId\":\"a1374655-5eb8-4a0e-9eb5-989521cd1ca8\",\n" +
                "   \"timestamp\":\"20200925183412\",\n" +
                "   \"senderId\":\"PSW\",\n" +
                "   \"receiverId\":\"AHB\",\n" +
                "   \"processingCode\":\"101\",\n" +
                "   \"data\":{\n" +
                "      \"gdNumber\":\"KPPI-HC-86-03-09-2020\",\n" +
                "      \"consignmentCategory\":\"Commercial\",\n" +
                "      \"gdStatus\": \"03\",\n" +
                "      \"gdType\":\"Home Consumption\",\n" +
                "      \"collectorate\":\"Qasim International Container Terminal\",\n" +
                "      \"blAwbNumber\":\"BL-24923231\",\n" +
                "      \"blAwbDate\":\"20201012\",\n" +
                "      \"virAirNumber\":\"KEWB-0005-010112020\",\n" +
                "      \"consignorConsigneeInfo\":{\n" +
                "         \"ntnFtn\":\"0425425\",\n" +
                "         \"strn\":\"1700003019489\",\n" +
                "         \"consigneeName\":\"PSW\",\n" +
                "         \"consigneeAddress\":\"PECHS\",\n" +
                "         \"consignorName\":\"M/S. International Jute Traders\",\n" +
                "         \"consignorAddress\":\"95, MOTIJHEEL COMMERCIAL AREA (2ND FLOOR) BANGLADESH.\"\n" +
                "      },\n" +
                "      \"financialInfo\":{\n" +
                "            \"importerIban\": \"PK91DUIB0000000671243001\",\n" +
                "            \"modeOfPayment\": \"303\",\n" +
                "            \"finInsUniqueNumber\": \"SBP-IMP-000001-23042033\",\n" +
                "            \"currency\": \"USD\",\n" +
                "            \"invoiceNumber\": \"AS1234567\",\n" +
                "            \"invoiceDate\": \"20200223\",\n" +
                "            \"totalDeclaredValue\": 5000.0002,\n" +
                "            \"deliveryTerm\": \"CFR\",\n" +
                "            \"fobValueUsd\": 1.0000,\n" +
                "            \"freightUsd\": 1.0000,\n" +
                "            \"cfrValueUsd\": 1.0000,\n" +
                "            \"insuranceUsd\": 0.0000,\n" +
                "            \"landingChargesUsd\": 100.0000,\n" +
                "            \"assessedValueUsd\": 50.0000,\n" +
                "            \"otherCharges\": 0.0000,\n" +
                "            \"exchangeRate\": 158.0000\n" +
                "\n" +
                "      },\n" +
                "      \"generalInformation\":{\n" +
                "         \"packagesInformation\":[\n" +
                "            {\n" +
                "               \"numberOfPackages\":100,\n" +
                "               \"packageType\":\"Box\"\n" +
                "            },\n" +
                "            {\n" +
                "               \"numberOfPackages\":100,\n" +
                "               \"packageType\":\"BULK\"\n" +
                "            }\n" +
                "         ],\n" +
                "         \"containerVehicleInformation\":[\n" +
                "            {\n" +
                "               \"containerOrTruckNumber\":\"ASF9999991\",\n" +
                "               \"sealNumber\":\"SL2674\",\n" +
                "               \"containerType\":\"20FT\"\n" +
                "            },\n" +
                "            {\n" +
                "               \"containerOrTruckNumber\":\"ASF9999992\",\n" +
                "               \"sealNumber\":\"SL2674\",\n" +
                "               \"containerType\":\"40FT\"\n" +
                "            }\n" +
                "         ],\n" +
                "         \"netWeight\":\"1.89400 MT\",\n" +
                "         \"grossWeight\":\"1.11400 MT\",\n" +
                "         \"portOfShipment\":\"AUCHN\",\n" +
                "         \"portOfDelivery\":\"AUCHN\",\n" +
                "         \"portOfDischarge\":\"AUCHN\",\n" +
                "         \"terminalLocation\":\"Qasim International Container Terminal\"\n" +
                "      },\n" +
                "      \"itemInformation\":[\n" +
                "         {\n" +
                "            \"hsCode\":\"8446.1000\",\n" +
                "            \"quantity\":6.0000,\n" +
                "            \"unitPrice\":20,\n" +
                "            \"totalValue\":120,\n" +
                "            \"importValue\":120,\n" +
                "            \"uom\":\"KG\"\n" +
                "         },\n" +
                "         {\n" +
                "            \"hsCode\":\"8446.1000\",\n" +
                "            \"quantity\":6.0000,\n" +
                "            \"unitPrice\":20,\n" +
                "            \"totalValue\":120,\n" +
                "            \"importValue\":120,\n" +
                "            \"uom\":\"KG\"\n" +
                "         }\n" +
                "      ]\n" +
                "   },\n" +
                "   \"signature\":\"nfWqwxvzJL6rRhcP3Ys+5Bk9ewapEAeUfI6c3z5UyJg=\"\n" +
                "}";

//
//        String[] abc =  reqBodyStr.split(",");
//
//        for (String st : abc){
//            if(st.startsWith("data")){
//                System.out.println(st);
//            }
//        }


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



//        JsonNode neoJsonNode2 = objectMapper.readTree(json1);
//        JsonNode data2 = neoJsonNode2.get("data");
//
//
//        System.out.println("-------------2"+data2.toString());
//        System.out.println("Signature is:"+AppUtility.buildSignature(data2.toString()));


        JSONObject obj = new JSONObject(sb.toString());
        String data = obj.getJSONObject("data").toString();

        System.out.println("-------------Data "+data.toString());
        System.out.println("Signature is:"+AppUtility.buildSignature(data.toString()));



//        JSONObject obj = new JSONObject(reqBodyStr);
//        String data = obj.getJSONObject("data").toString();

        //System.out.println("---------Data:"+data);
//        System.out.println("---------Data2:"+springParser.parseMap(reqBodyStr).get("data"));




        // TestMain.ReadMSG();
        //testRandomUUID();

//        String msg =  "MSG TYPE!UNIQ ID!Importer NTN|Importer Name|Importer IBAN|Mode of Payment|InstumentNo|BENEFICIARY Name|BENEFICIARY ADDRESS|SHIP PORT|223232.22|INSTR CURR|2.200|INSTUMENT NO\n" +
//                "|^HS CODE|HS QUANTITY|GOODS DESC|^HS CODE|HS QUANTITY|GOODS DESC|^HS CODE|HS QUANTITY|GOODS DESC";



//  5.2.1        MSG.TYPE!UNIQUE.ID!Exporter NTN|Exporter Name|Exporter IBAN|Mode of Payment|Financial Instrument Unique No|Delivery Terms|Financial Instrument Currency|Financial Instrument Value|Financial Instrument Expiry Date|HS Code|Goods Description| Quantity


//
//        String msg = "PSW513!PSW513TF16249431810620210202!|COMMERZ BANK AG FRANKFURT|PK13SAUD0099982000002723|TF162494318106|13239.90||13239.90|EUR||||13239.90|1588788.00|LC-3101^LC-3101^LC-3101^LC-3101|EXPSRVCHG  PKR  2381.00  B^WHTEXP1PC  PKR  15875.00  B^EXPDOCCOLL  PKR  500.00  B^EPRC1YR  PKR  300.00  B|";
//
//        String msg523 = "PSW523!PSW523TF16249431810620210202!|COMMERZ BANK AG FRANKFURT|PK13SAUD0099982000002723|301|TF162494318106|169502ECSF0070605||||13239.90|||13239.90|1588788.00|";
//
//        MqUtility.MqMessage replyMessage = MqUtility.parseReplyMessage(msg523);
//
//
//
//        System.out.println("-------"+replyMessage.getReqResStr());
//
//        MQMessageParser parser = new MQMessageParser();
//
//        //System.out.println("=========="+parser.parseAndBuildBDAInfoImport(replyMessage.getReqResStr()));
//
//        System.out.println("=========="+parser.parseAndBuildBCAExport(replyMessage.getReqResStr()));

  //      String[] acctDtlAry = msg.getReqResStr().split("\\|\\^");
       /* for (String str:acctDtlAry){
            System.out.println("-------"+str);

        }*/




    }


    public static void testRandomUUID(){
        final String uuid = UUID.randomUUID().toString().replace("-", "").substring(0,18);
        System.out.println("uuid = " +  UUID.randomUUID().toString().replace("-", "").substring(0,18));

        HashMap<String,String> map = new HashMap();

        for (int i = 0 ; i < 1000; i++) {
            String uid = AppUtility.generateRandomUniqString();
            System.out.println(i + "-uuid = " +uid );
            System.out.println(map.size()+"--"+map.get(uid));
            map.put(uid,uid);

        }
    }


    public static void ReadMSG()
    {
        Hashtable<String, Object> props = new Hashtable<String, Object>();
        props.put(MQConstants.CHANNEL_PROPERTY, "FBTI.SVRCONN");
        props.put(MQConstants.PORT_PROPERTY, 1415);
        props.put(MQConstants.HOST_NAME_PROPERTY, "AhsanJa-NB");
        String qManager = "TIMQMGR";
        String queueName = "PSW.IN";

        MQQueueManager qMgr = null;
        MQMessage receiveMsg = null;
        int msgCount = 0;
        boolean getMore = true;

        try {
            qMgr = new MQQueueManager(qManager, props);
            int openOptions = MQConstants.MQOO_OUTPUT | MQConstants.MQOO_INPUT_AS_Q_DEF;
            MQGetMessageOptions gmo = new MQGetMessageOptions();
            MQQueue queue = qMgr.accessQueue(queueName, openOptions);
            MQPutMessageOptions pmo = new MQPutMessageOptions();
            pmo.options = MQConstants.MQPMO_ASYNC_RESPONSE;
            while(getMore)
            {
                receiveMsg = new MQMessage();
                queue.get(receiveMsg, gmo);
                byte[] b = null;
                try {
                    b = new byte[receiveMsg.getMessageLength()];
                    receiveMsg.readFully(b);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                System.out.println(new String(b));


            }

        } catch (MQException e) {
            e.printStackTrace();
            if ( (e.completionCode == CMQC.MQCC_FAILED) &&
                    (e.reasonCode == CMQC.MQRC_NO_MSG_AVAILABLE) )
            {
                // All messages read.
                getMore = false;
                //break;
            }
        }

    }
}
