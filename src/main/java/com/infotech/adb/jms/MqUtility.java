package com.infotech.adb.jms;

import com.infotech.adb.dto.IBANVerificationRequest;
import com.infotech.adb.util.AppUtility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Objects;

public class MqUtility {


    public static final String MSG_TYPE_ACCT_VERIFICATION = "PSW001";
    public static final String DELIMETER = "!";

    public static HashMap<String, MqUtility.MqMessage> objectLockingMap = new HashMap<>();

    public static MqMessage buildAccountVerificationMessage(IBANVerificationRequest req) {
        MqMessage msg = new MqMessage();
        msg.setId(AppUtility.generateRandomUniqString());
        msg.setType(MSG_TYPE_ACCT_VERIFICATION);

        StringBuffer sb = new StringBuffer();
        sb.append(MSG_TYPE_ACCT_VERIFICATION)
                .append(DELIMETER).append(msg.getId())
                .append(DELIMETER).append(req.getNtn())
                .append(DELIMETER).append(req.getIban())
                .append(DELIMETER).append(req.getEmail())
                .append(DELIMETER).append(req.getMobileNumber());
        msg.setReqResStr(sb.toString());
        return msg;
    }

    public static MqMessage parseReplyMessage(String message) {
        MqMessage msg = new MqMessage();
        String[] messageAry = message.split(DELIMETER);
        msg.setType(messageAry[0]);
        msg.setId(messageAry[1]);
        msg.setReqResStr(messageAry[2]);
        return msg;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class MqMessage {
        private String id;
        private String type;
        private String reqResStr;
        private String desc;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MqMessage message = (MqMessage) o;
            return Objects.equals(id, message.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}
