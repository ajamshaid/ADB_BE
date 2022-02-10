package com.infotech.adb.dto;


import com.infotech.adb.model.entity.MqLog;

import lombok.Data;

import java.util.Date;

@Data
public class MqLogDTO implements BaseDTO<MqLogDTO, MqLog> {

    private Long id;

    private String msgId;
    private String msgType;
    private String senderId;
    private String receiverId;
    private String message;
    private Date dateTime;


    public MqLogDTO() {
    }

    public MqLogDTO(Long id) {
        this.id = id;
    }

    @Override
    public MqLog convertToEntity() {
        MqLog mqLog = new MqLog();
        mqLog.setId(this.id);
        mqLog.setMsgId(this.msgId);
        mqLog.setMsgType(this.msgType);
        mqLog.setSenderId(this.senderId);
        mqLog.setReceiverId(this.receiverId);
        mqLog.setMessage(this.message);
        mqLog.setDateTime(this.dateTime);

        return mqLog;
    }

    @Override
    public void convertToDTO(MqLog entity, boolean partialFill) {
        this.id = entity.getId();
        this.msgId = entity.getMsgId();
        this.msgType = entity.getMsgType();
        this.senderId = entity.getSenderId();
        this.receiverId = entity.getReceiverId();
        this.message = entity.getMessage();
        this.dateTime = entity.getDateTime();
    }

    @Override
    public MqLogDTO convertToNewDTO(MqLog mqlog, boolean partialFill) {
        MqLogDTO mqLogDTO = new MqLogDTO();
        mqLogDTO.convertToDTO(mqlog, partialFill);
        return mqLogDTO;
    }
}