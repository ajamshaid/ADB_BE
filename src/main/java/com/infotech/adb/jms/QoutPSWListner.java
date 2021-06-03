package com.infotech.adb.jms;

import com.infotech.adb.model.entity.FinancialTransaction;
import com.infotech.adb.model.entity.ItemInformation;
import com.infotech.adb.model.entity.PaymentInformation;
import com.infotech.adb.service.ReferenceService;
import com.infotech.adb.util.AppUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


@Log4j2
@Component
public class QoutPSWListner {

    @Autowired
    ReferenceService referenceService;

    @JmsListener(destination = "QOUT_PSW")
    public void receiveMessage(String msg) {
        System.out.println("========================================");
        System.out.println("Received message string is: " + msg);
        System.out.println("========================================");

        String name = Thread.currentThread().getName();
        MqUtility.MqMessage replyMessage = MqUtility.parseReplyMessage(msg);
        if (!AppUtility.isEmpty(replyMessage)) { //IF Valid Expected Format Message. Else Ignore
            if (MqUtility.MSG_TYPE_FIN_TRANS_IMPORT.equals(replyMessage.getType())) {
                // IF Message 5.1.1  Share Financial Transaction Data with PSW

                try {
                    FinancialTransaction ft = this.parseAndBuildFTImport(replyMessage.getReqResStr());
                    referenceService.updateFinancialTransaction(ft);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                MqUtility.MqMessage reqMessage = MqUtility.objectLockingMap.get(replyMessage.getId());
                if (AppUtility.isEmpty(reqMessage)) {
                    System.out.println("NO Object Found in ObjectLockingMap for Incoming message:" + replyMessage);
                } else {
                    try {
                        Thread.sleep(1000);

                        synchronized (reqMessage) {
                            // Replace Request Message with Reply Message on Map to be get from Waiter Thread...
                            MqUtility.objectLockingMap.put(reqMessage.getId(), replyMessage);
                            System.out.println(name + "-> Notifying back to Waiting Thread at MessageId:" + reqMessage.getId());
                            reqMessage.notify();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    private FinancialTransaction parseAndBuildFTImport(String data) {
        FinancialTransaction ft = null;
        if (!AppUtility.isEmpty(data)) {
            String[] dataAry = data.split(MqUtility.DELIMETER_MULTIPLE_DATA);
            String[] ftDetailsAry = dataAry[0].split(MqUtility.DELIMETER_DATA);

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
            pi.setFinancialInstrumentValue(new BigDecimal(ftDetailsAry[8]));
            pi.setFinancialInstrumentCurrency(ftDetailsAry[9]);
            pi.setExchangeRate(new BigDecimal(ftDetailsAry[10]));
            pi.setLcContractNo(ftDetailsAry[11]);


            Set<ItemInformation> itemInformationSet = new HashSet<>(dataAry.length-1);
            for (int index = 1; index < dataAry.length; index++) {
               String[] itemStrAry = dataAry[index].split(MqUtility.DELIMETER_DATA);

                ItemInformation itemInfo = new ItemInformation();
                itemInfo.setHsCode(itemStrAry[0]);
                itemInfo.setQuantity(new BigDecimal(itemStrAry[1]));
                itemInfo.setGoodsDescription(itemStrAry[2]);

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
}
