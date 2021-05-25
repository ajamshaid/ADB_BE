package com.infotech.adb.jms;

import com.infotech.adb.util.AppUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


@Log4j2
@Component
public class QoutPSWListner {
    @JmsListener(destination = "QOUT_PSW")
    public void receiveMessage(String msg) {
        System.out.println("========================================");
        System.out.println("Received message string is: " + msg);
        System.out.println("========================================");

        String name = Thread.currentThread().getName();
        MqUtility.MqMessage replyMessage = MqUtility.parseReplyMessage(msg);
        if (!AppUtility.isEmpty(replyMessage)) { //IF Valid Expected Format Message. Else Ignore
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
