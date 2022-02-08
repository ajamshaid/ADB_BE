package com.infotech.adb.silkbank.jms;

import com.infotech.adb.model.entity.*;
import com.infotech.adb.service.ReferenceService;
import com.infotech.adb.util.AppConstants;
import com.infotech.adb.util.AppUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


@Log4j2
@Component
public class QueueOutListner {

    @Autowired
    ReferenceService referenceService;

    @Autowired
    MQMessageParser mqMessageParser;



    @JmsListener(destination = "QOUT_PSW")
    public void receiveMessage(String msg) {
        log.info("\n-------------Message Received from MQ [QOUT_PSW]: "
                    +"\n-------------"+msg
                    +"\n=================================================");

        String name = Thread.currentThread().getName();
        MQUtility.MqMessage replyMessage = MQUtility.parseReplyMessage(msg);
        if (!AppUtility.isEmpty(replyMessage)) { //IF Valid Expected Format Message. Else Ignore
            if (MQUtility.MSG_TYPE_FIN_TRANS_IMPORT.equals(replyMessage.getType())) {
                // IF Message 5.1.1  Import Share Financial Transaction Data with PSW

                try {
                    FinancialTransaction ft = mqMessageParser.parseAndBuildFTImport(replyMessage.getReqResStr());
                    ft.setLastModifiedBy(AppConstants.MQ_USER);
                    ft.setLastModifiedDate(AppUtility.getCurrentTimeStamp());
                    referenceService.updateFinancialTransaction(ft);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else if (MQUtility.MSG_TYPE_BDA_IMPORT.equals(replyMessage.getType())) {
                // IF Message 5.1.3  BDA Import
                try {
                    BDA bda = mqMessageParser.parseAndBuildBDAInfoImport(replyMessage.getReqResStr());
                    bda.setLastModifiedBy(AppConstants.MQ_USER);
                    bda.setLastModifiedDate(AppUtility.getCurrentTimeStamp());
                    referenceService.updateBDA(bda);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }  else if (MQUtility.MSG_TYPE_FIN_TRANS_EXPORT.equals(replyMessage.getType())) {
                // IF Message 5.2.1  Export Share Financial Transaction Data with PSW
                try {
                    FinancialTransaction ft = mqMessageParser.parseAndBuildFTExport(replyMessage.getReqResStr());
                    ft.setLastModifiedBy(AppConstants.MQ_USER);
                    ft.setLastModifiedDate(AppUtility.getCurrentTimeStamp());
                    referenceService.updateFinancialTransaction(ft);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else if (MQUtility.MSG_TYPE_BCA_EXPORT.equals(replyMessage.getType())) {
                // IF Message 5.2.3  BCA Export
                try {
                    BCA bca = mqMessageParser.parseAndBuildBCAExport(replyMessage.getReqResStr());
                    bca.setLastModifiedBy(AppConstants.MQ_USER);
                    bca.setLastModifiedDate(AppUtility.getCurrentTimeStamp());
                    referenceService.updateBCA(bca);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }else if (MQUtility.MSG_TYPE_CANCELLATION_OF_FT.equals(replyMessage.getType())) {
                // IF Message 8.1  Cancellation of FT
                try {
                    CancellationOfFT cft = mqMessageParser.parseAndBuildCFT(replyMessage.getReqResStr());
                    cft.setLastModifiedBy(AppConstants.MQ_USER);
                    cft.setLastModifiedDate(AppUtility.getCurrentTimeStamp());
                    referenceService.updateCancellationOfFT(cft);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }else if (MQUtility.MSG_TYPE_REVERSAL_OF_BDA_BCA.equals(replyMessage.getType())) {
                // IF Message 9.1 Reversal of BDA/BCA
                try {
                    ReversalOfBdaBca entity = mqMessageParser.parseAndBuildRevBDABCA(replyMessage.getReqResStr());
                    entity.setLastModifiedBy(AppConstants.MQ_USER);
                    entity.setLastModifiedDate(AppUtility.getCurrentTimeStamp());
                    referenceService.updateReversalBDABCA(entity);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                MQUtility.MqMessage reqMessage = MQUtility.objectLockingMap.get(replyMessage.getId());
                if (AppUtility.isEmpty(reqMessage)) {
                    log.debug("\n+++++ NO Object Found in ObjectLockingMap for Incoming message:" + replyMessage);
                } else {
                    try {
                        Thread.sleep(1000);

                        synchronized (reqMessage) {
                            // Replace Request Message with Reply Message on Map to be get from Waiter Thread...
                            MQUtility.objectLockingMap.put(reqMessage.getId(), replyMessage);
                            log.debug("\n+++++ "+ name + "-> Notifying back to Waiting Thread at MessageId:" + reqMessage.getId());
                            reqMessage.notify();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
