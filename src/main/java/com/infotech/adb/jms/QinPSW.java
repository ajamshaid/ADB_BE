package com.infotech.adb.jms;

import com.infotech.adb.util.AppUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;


@Component
public class QinPSW {
    static final String qName = "QIN_PSW"; // A queue from the default MQ Developer container config
    static final long timeOut = 280 * 1000; // x * 1000 = x seconds

    @Autowired
    JmsTemplate jmsTemplate;

    public WMQMessage putMessage(WMQMessage message) throws JMSException {
// Create the JMS Template object to control connections and sessions.
        //JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
        // jmsTemplate.setReceiveTimeout(15 * 1000); // How long to wait for a reply - milliseconds

        AppUtility.objectLockingMap.put(message.getId(),message);


        System.out.println("MessageID="+message.getId());
        jmsTemplate.convertAndSend(qName, message.getMsg());
        System.out.println("Message placed to queue: " + qName);


        String threadName = Thread.currentThread().getName();
        try {

            synchronized (message) {
                System.out.println(" Locked on :"+message);
                //System.out.println(threadName+"-> waiting to get notified at time:"+System.currentTimeMillis());
                message.wait(timeOut);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();

            message.setMsg("Request Interrupted/Timeout");
        }

        message = AppUtility.objectLockingMap.remove(message.getId());
      ///  System.out.println(threadName+"-> Notified at time:"+System.currentTimeMillis());
        System.out.println(threadName+"-> Returned Message is: "+message);
        return message;

    }
}
