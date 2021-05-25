package com.infotech.adb.jms;

import com.infotech.adb.util.AppUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;

@Log4j2
@Component
public class QinPSW {
    static final String qName = "QIN_PSW"; // A queue from the default MQ Developer container config
    static final long timeOut = 2 * 60 * 1000; // x * 1000 = x seconds

    @Autowired
    JmsTemplate jmsTemplate;
    //@TODO JMSTemplate is not closing Que Connection. Please check.

    public MqUtility.MqMessage putMessage(MqUtility.MqMessage message) throws JMSException {

        // Create the JMS Template object to control connections and sessions.
        //JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
        // jmsTemplate.setReceiveTimeout(15 * 1000); // How long to wait for a reply - milliseconds

        MqUtility.objectLockingMap.put(message.getId(),message);

        log.info("Placing Message["+message.getType()+"] on Queue["+qName+"] with MessageID="+message.getId());
        jmsTemplate.convertAndSend(qName, message.getReqResStr());
        log.debug("Message Placed Successfully");

        String threadName = Thread.currentThread().getName();
        try {

            synchronized (message) {
                log.info("Locked on :"+message);
                log.info(threadName+"-> waiting to get notified at time:"+AppUtility.getCurrentTimeStampString());
                log.info("Timeout is set to "+timeOut+" milliSeconds");
                message.wait(timeOut);

            }
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            message.setDesc("Request Interrupted/Timeout");
        }

        message = MqUtility.objectLockingMap.remove(message.getId());

        log.debug(threadName+"-> Notified at time:"+AppUtility.getCurrentTimeStampString());
        log.debug(threadName+"-> Returned Message is: "+message);
        return message;
    }
}
