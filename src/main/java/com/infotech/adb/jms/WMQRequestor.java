package com.infotech.adb.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.Date;


@Component
public class WMQRequestor {

    @Autowired
    private ApplicationContext context;

    static JmsTransactionManager tm = new JmsTransactionManager();
    static final String qName = "QIN_PSW"; // A queue from the default MQ Developer container config

    static String correlID = null;
    static TextMessage message;

    // Construct a Transaction Manager that will control local transactions.
//    @Bean
//    public JmsTransactionManager transactionManager(ConnectionFactory connectionFactory) {
//        JmsTransactionManager transactionManager = new JmsTransactionManager(connectionFactory);
//        return transactionManager;
//    }

public void sendMessageToPWSQIN() throws JMSException {

// Create the JMS Template object to control connections and sessions.
    JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
    jmsTemplate.setReceiveTimeout(5 * 1000); // How long to wait for a reply - milliseconds

    // Create a single message with a timestamp
    String payload = "Hello from IBM MQ at " + new Date();

    // Send the message and wait for a reply for up to the specified timeout
    Message replyMsg = jmsTemplate.sendAndReceive(qName, new MessageCreator() {
        @Override
        public Message createMessage(Session session) throws JMSException {
            message = session.createTextMessage(payload);
            System.out.println("Sending message: " + message.getText());
            return message;
        }
    });


    if (replyMsg != null) {
        if (replyMsg instanceof TextMessage) {
            System.out.println("Reply message is: " + ((TextMessage) replyMsg).getText());
        }
        else {
            System.out.println("Reply message is: " + replyMsg.toString());
        }
    }
    else {
        System.out.println("No reply received");
    }

    System.out.println("Done.");
}

/*
    public static void main(String[] args) {

        // Variables
        Connection connection = null;
        Session session = null;
        Destination destination = null;
        Destination tempDestination = null;
        MessageProducer producer = null;
        MessageConsumer consumer = null;

        try {
            // Create a connection factory
            JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
            JmsConnectionFactory cf = ff.createConnectionFactory();

            // Set the properties
            cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, "localhost");
            cf.setIntProperty(WMQConstants.WMQ_PORT, 1414);
            cf.setStringProperty(WMQConstants.WMQ_CHANNEL, "SYSTEM.DEF.SVRCONN");
            cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
            cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, "QM1");

            // Create JMS objects
            connection = cf.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("queue:///Q1");
            producer = session.createProducer(destination);
            tempDestination = session.createTemporaryQueue();
            consumer = session.createConsumer(tempDestination);

            long uniqueNumber = System.currentTimeMillis() % 1000;
            TextMessage message = session
                    .createTextMessage("SimpleRequestor: Your lucky number today is " + uniqueNumber);

            // Set the JMSReplyTo
            message.setJMSReplyTo(tempDestination);

            // Start the connection
            connection.start();

            // And, send the request
            producer.send(message);
            System.out.println("Sent message:\n" + message);

            // Now, receive the reply
            Message receivedMessage = consumer.receive(15000); // in ms or 15 seconds
            System.out.println("\nReceived message:\n" + receivedMessage);

            recordSuccess();
        }
        catch (JMSException jmsex) {
            recordFailure(jmsex);
        }
        finally {
            if (producer != null) {
                try {
                    producer.close();
                }
                catch (JMSException jmsex) {
                    System.out.println("Producer could not be closed.");
                    recordFailure(jmsex);
                }
            }
            if (consumer != null) {
                try {
                    consumer.close();
                }
                catch (JMSException jmsex) {
                    System.out.println("Consumer could not be closed.");
                    recordFailure(jmsex);
                }
            }

            if (session != null) {
                try {
                    session.close();
                }
                catch (JMSException jmsex) {
                    System.out.println("Session could not be closed.");
                    recordFailure(jmsex);
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                }
                catch (JMSException jmsex) {
                    System.out.println("Connection could not be closed.");
                    recordFailure(jmsex);
                }
            }
        }
        System.exit(status);
        return;
    } // end main()
 */
}
