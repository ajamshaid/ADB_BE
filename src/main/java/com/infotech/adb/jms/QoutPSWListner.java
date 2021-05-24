package com.infotech.adb.jms;

import com.infotech.adb.util.AppUtility;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class QoutPSWListner {
    static boolean warned = false;


    //@TODO JMSTemplate is not closing Que Connection. Please check.




    @JmsListener(destination = "QOUT_PSW")
    public void receiveMessage(String msg) {
  //      infinityWarning();

        System.out.println();
        System.out.println("========================================");
        System.out.println("Received message is: " + msg);
        System.out.println("========================================");


        String name = Thread.currentThread().getName();
        System.out.println(name+" started");
        try {
            Thread.sleep(1000);

            final String messageId = msg;
            WMQMessage message = (WMQMessage) AppUtility.objectLockingMap.get(messageId);
            synchronized (message) {
                message.setMsg(name+"-> Notifying at Message:"+messageId);
                message.notify();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void infinityWarning() {
        if (!warned) {
            warned = true;
            System.out.println();
            System.out.println("========================================");
            System.out.println("MQ JMS Listener started for queue: " + "QOUT_PSW");
            System.out.println("NOTE: This program does not automatically end - it continues to wait");
            System.out.println("      for more messages, so you may need to hit BREAK to end it.");
            System.out.println("========================================");
        }
    }

}
