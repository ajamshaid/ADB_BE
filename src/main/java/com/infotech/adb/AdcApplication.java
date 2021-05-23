package com.infotech.adb;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@SpringBootApplication
@EnableJms
@EnableTransactionManagement
public class AdcApplication extends SpringBootServletInitializer {


    @Value("${psw.base.url}")
    private static String PSW_BASE_URL;


    static void printStarted() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("ADB Application started.");
        System.out.println("========================================");
    }
    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(AdcApplication.class, args);
        printStarted();
        System.out.println("--------------"+PSW_BASE_URL);
//        WMQRequestor wmqRequestor = context.getBean(WMQRequestor.class);
//
//        try {
//            wmqRequestor.sendMessageToPWSQIN();
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }

    }

    // Construct a Transaction Manager that will control local transactions.
//    @Bean
//    public JmsTransactionManager transactionManager(ConnectionFactory connectionFactory) {
//        JmsTransactionManager transactionManager = new JmsTransactionManager(connectionFactory);
//        return transactionManager;
//    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AdcApplication.class);
    }

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(false);
        loggingFilter.setIncludeHeaders(false);
        return loggingFilter;
    }
}
