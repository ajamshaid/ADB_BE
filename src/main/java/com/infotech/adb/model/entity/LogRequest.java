package com.infotech.adb.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "LOG_REQUEST")
@JsonIgnoreProperties(ignoreUnknown = true)
public class LogRequest extends BaseEntity {

    @Column(name = "RESPONSE_MESSAGE", nullable = false)
    private String responseMessage;
    @Column(name = "RESPONSE_CODE", nullable = false)
    private Integer responseCode;

    @Column(name = "MSG_IDENTIFIER", nullable = false)
    private String msgIdentifier;

    @Column(name = "REQUEST_METHOD", nullable = false)
    private String requestMethod;

    @Column(name = "REQUEST_PAYLOAD", length = 4000)
    private String requestPayload;

    @Column(name = "RESPONSE_PAYLOAD", length = 4000)
    private String responsePayload;

    @Column(name = "REQUEST_TIME", columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private ZonedDateTime requestTime;

    @Column(name = "RESPONSE_TIME", columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private ZonedDateTime responseTime;

    public LogRequest() {
    }

}