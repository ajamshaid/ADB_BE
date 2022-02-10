package com.infotech.adb.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.dto.RequestParameter;
import com.infotech.adb.util.AppUtility;
import com.infotech.adb.util.ResponseUtility;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "MQ_LOG")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MqLog {

    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MESSAGE_ID", nullable = false)
    private String msgId;

    @Column(name = "MESSAGE_TYPE", nullable = false)
    private String msgType;

    @Column(name = "SENDER_ID", nullable = false)
    private String senderId;

    @Column(name = "RECEIVER_ID", nullable = false)
    private String receiverId;

    @Column(name = "MESSAGE", length = 4000)
    private String message;

    @Column(name = "DATE_TIME", columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private Date dateTime;

    public MqLog() {
    }

}