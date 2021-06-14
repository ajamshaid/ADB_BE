package com.infotech.adb.util;

import com.ibm.mq.*;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.constants.MQConstants;
import com.infotech.adb.jms.MqUtility;
import com.infotech.adb.model.entity.FinancialTransaction;
import com.infotech.adb.model.entity.ItemInformation;
import com.infotech.adb.model.entity.PaymentInformation;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class TestMain {
    public static final int SET_SIZE_REQUIRED = 10;
    public static final int NUMBER_RANGE = 100;

    public static void main(String args[]) throws CsvException, FileNotFoundException {


        // TestMain.ReadMSG();
        //testRandomUUID();

//        String msg =  "MSG TYPE!UNIQ ID!Importer NTN|Importer Name|Importer IBAN|Mode of Payment|InstumentNo|BENEFICIARY Name|BENEFICIARY ADDRESS|SHIP PORT|223232.22|INSTR CURR|2.200|INSTUMENT NO\n" +
//                "|^HS CODE|HS QUANTITY|GOODS DESC|^HS CODE|HS QUANTITY|GOODS DESC|^HS CODE|HS QUANTITY|GOODS DESC";


        String msg = "PSW511!PSW511TF210336834320210202!0711833-3|RAWAL TEXTILE MILL L|PK57SAUD0000132000795097|301|TF2103368343|KANAI JUYO KOGYO CO., LTD.|4-1 OKUHATA, ITAMI, HYOGO 664-0025 JAPAN. ||1296500|JPY||TF2103368343|8448.3190^8448.3110|^|KANAI METALLIC CARD CLOTHING AND TOP FLATS:  FOR CROSSROLL MK-4 CARD: 04 SET TOP FLATS NVS-450 X 101PCS 1016MM AT THE RATE OF JPY 160,700/SET. 30 KGS TAKER IN WIRE TC 50 KH 1.5MM ES AT THE RATE OF JPY 2,160/SET. FOR TRUZCHLER DK-740 CARD: 03 SETS CYLINDER WIRE, CC65 NPI ES 50 INCHES AT THE RATE OF JPY 129,300/SET. 03 SETS DOFFER WIRE, DU39-0 ES  MR 27 INCHES AT THE RATE OF JPY 67,000/SET. CFR KARACHI, PAKISTAN AS PER INDENT NO. KP/217R4/2020 DATED 06 JANUARY, 2021.";


        MqUtility.MqMessage replyMessage = MqUtility.parseReplyMessage(msg);

        System.out.println("-------"+replyMessage.getReqResStr());


        System.out.println("=========="+parseAndBuildFTImport(replyMessage.getReqResStr()));

  //      String[] acctDtlAry = msg.getReqResStr().split("\\|\\^");
       /* for (String str:acctDtlAry){
            System.out.println("-------"+str);

        }*/




    }
    private static  FinancialTransaction parseAndBuildFTImport(String data) {
        FinancialTransaction ft = new FinancialTransaction();
        if (!AppUtility.isEmpty(data)) {
//            String[] dataAry = data.split(MqUtility.DELIMETER_MULTIPLE_DATA);
            String[] ftDetailsAry = data.split(MqUtility.DELIMETER_DATA);

            ft = new FinancialTransaction();
            ft.setNtn(ftDetailsAry[0]);

            ft.setName(ftDetailsAry[1]);
            ft.setIban(ftDetailsAry[2]);
            ft.setModeOfPayment(ftDetailsAry[3]);
            ft.setFinInsUniqueNumber(ftDetailsAry[4]);

            PaymentInformation pi = new PaymentInformation();
            pi.setBeneficiaryName(ftDetailsAry[5]);
            pi.setBeneficiaryAddress(ftDetailsAry[6]);
            pi.setPortOfShipment(ftDetailsAry[7]);
            if(AppUtility.isBigDecimal(ftDetailsAry[8])) {
                pi.setFinancialInstrumentValue(new BigDecimal(ftDetailsAry[8]));
            }
            pi.setFinancialInstrumentCurrency(ftDetailsAry[9]);
            if(AppUtility.isBigDecimal(ftDetailsAry[10])) {
                pi.setExchangeRate(new BigDecimal(ftDetailsAry[10]));
            }
            pi.setLcContractNo(ftDetailsAry[11]);


            String[] hsCodeAry = ftDetailsAry[12].split(MqUtility.DELIMETER_MULTIPLE_DATA);
            String[] qtyAry = ftDetailsAry[13].split(MqUtility.DELIMETER_MULTIPLE_DATA);
            String[] descAry = ftDetailsAry[14].split(MqUtility.DELIMETER_MULTIPLE_DATA);

            Set<ItemInformation> itemInformationSet = new HashSet<>(hsCodeAry.length);
            for (int index = 0; index < hsCodeAry.length; index++) {
//                String[] itemStrAry = hsCodeAry[index].split(MqUtility.DELIMETER_DATA);

                ItemInformation itemInfo = new ItemInformation();
                itemInfo.setHsCode(hsCodeAry[index]);

                if(qtyAry.length > index && AppUtility.isBigDecimal(qtyAry[index])) {
                    itemInfo.setQuantity(new BigDecimal(qtyAry[index]));
                }

                if(descAry.length > index ) {
                    itemInfo.setGoodsDescription(descAry[index].length() > 99 ? descAry[index].substring(0, 96)+"...": descAry[index]);
                }

                itemInfo.setFinancialTransaction(ft);

                itemInformationSet.add(itemInfo);

            }
            pi.setFinancialTransaction(ft);
            ft.setPaymentInformation(pi);
            ft.setItemInformationSet(itemInformationSet);

            System.out.println("FT-<>" + ft);
        }
        return ft;
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
