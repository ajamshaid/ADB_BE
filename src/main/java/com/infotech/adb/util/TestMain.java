package com.infotech.adb.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.mq.*;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.constants.MQConstants;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.UUID;

public class TestMain {
    public static final int SET_SIZE_REQUIRED = 10;
    public static final int NUMBER_RANGE = 100;

    public static void main(String args[]) throws CsvException, FileNotFoundException, JsonProcessingException {

        String a = "{\"mobileNumber\":\"0300 123\",\"iban\":\"PK123\",\"ntn\":\"ntn 123\",\"email\":\"123@psw.pk123\"}";
        String aa = "{\"mobileNumber\":\"0300 123\",\"iban\":\"PK123\",\"ntn\":\"ntn 123\",\"email\":\"123@psw.pk123\"}";
        String b = "{\"email\": \"123@psw.pk123\",\"iban\": \"PK123\",\"mobileNumber\": \"0300 123\",\"ntn\": \"ntn 123\"}";



        System.out.println(AppUtility.buildSignature(a));
        System.out.println(AppUtility.buildSignature(aa));
        System.out.println(AppUtility.buildSignature(b));


        String reqBodyStr = "{\n" +
                "  \"data\":{\"iban\": \"PK123\",\"email\": \"123@psw.pk123\",\"mobileNumber\": \"0300 123\",\"ntn\": \"ntn 123\"},\n" +
                "  \"messageId\": \"a1374655-5eb8-4a0e-9eb5-989521cd1ca8\",\n" +
                "  \"processingCode\": \"301\",\n" +
                "  \"receiverId\": \"SCBL\",\n" +
                "  \"senderId\": \"PSW\",\n" +
                "  \"signature\": \"9dx0B7RdGlhsPWvWSbOSpDL6Zfp/1JdGombWxOnKT8U=\",\n" +
                "  \"timestamp\": \"20200925183412\"\n" +
                "}";

        String reqBodyStr1 = "{\n" +
                "  \"data\":{\"email\": \"123@psw.pk123\",\"iban\": \"PK123\",\"mobileNumber\": \"0300 123\",\"ntn\": \"ntn 123\"},\n" +
                "  \"messageId\": \"a1374655-5eb8-4a0e-9eb5-989521cd1ca8\",\n" +
                "  \"processingCode\": \"301\",\n" +
                "  \"receiverId\": \"SCBL\",\n" +
                "  \"senderId\": \"PSW\",\n" +
                "  \"signature\": \"9dx0B7RdGlhsPWvWSbOSpDL6Zfp/1JdGombWxOnKT8U=\",\n" +
                "  \"timestamp\": \"20200925183412\"\n" +
                "}";


        String reqBodyStr2 = "{\n" +

                "  \"messageId\": \"a1374655-5eb8-4a0e-9eb5-989521cd1ca8\",\n" +
                "  \"processingCode\": \"301\",\n" +
                "  \"receiverId\": \"SCBL\",\n" +
                "  \"data\":{\"mobileNumber\": \"0300 123\",\"ntn\": \"ntn 123\",\"iban\": \"PK123\",\"email\": \"123@psw.pk123\"},\n" +
                "  \"senderId\": \"PSW\",\n" +
                "  \"signature\": \"9dx0B7RdGlhsPWvWSbOSpDL6Zfp/1JdGombWxOnKT8U=\",\n" +
                "  \"timestamp\": \"20200925183412\"\n" +
                "}";

//
//        String[] abc =  reqBodyStr.split(",");
//
//        for (String st : abc){
//            if(st.startsWith("data")){
//                System.out.println(st);
//            }
//        }


        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode neoJsonNode = objectMapper.readTree(reqBodyStr);
        JsonNode data = neoJsonNode.get("data");


        JsonNode neoJsonNode1 = objectMapper.readTree(reqBodyStr1);
        JsonNode data1 = neoJsonNode1.get("data");


        JsonNode neoJsonNode2 = objectMapper.readTree(reqBodyStr2);
        JsonNode data2 = neoJsonNode2.get("data");


        System.out.println("-------------"+data.toString());
        System.out.println("-------------1"+data1.toString());
        System.out.println("-------------2"+data2.toString());


        System.out.println(AppUtility.buildSignature(data.toString()));
        System.out.println(AppUtility.buildSignature(data1.toString()));
        System.out.println(AppUtility.buildSignature(data2.toString()));

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
