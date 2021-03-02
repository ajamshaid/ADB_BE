package com.infotech.adb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CommonResponse {

    @JsonProperty("Message ID")
    private UUID messageId;
    @JsonProperty("Timestamp")
    private Date timestamp;
    @JsonProperty("Sender ID")
    private String senderId;
    @JsonProperty("Receiver ID")
    private String receiverId;
    @JsonProperty("Signature")
    private String signature;
    @JsonProperty("Response Code")
    private Integer responseCode;

}
