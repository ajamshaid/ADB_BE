package com.infotech.adb.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class RequestParameter<T> {

    private UUID messageId;
    private Timestamp timestamp;
    private String senderId;
    private String receiverId;
    private String processingCode;
    private String methodId;
    private String signature;

    private T data;

    public String toJson() throws JsonProcessingException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(this);
    }
}
