package com.infotech.adb.jms;

import com.infotech.adb.dto.IBANVerificationRequest;
import com.infotech.adb.util.AppUtility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Objects;

@Log4j2
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
        MqMessage msg = null;
        String[] messageAry = message.split(DELIMETER);
        if(AppUtility.isEmpty(messageAry) || messageAry.length < 3){
            log.debug("Invalid Message ");
        }else {
            msg = new MqMessage();
            msg.setType(AppUtility.isEmpty(messageAry[0]) ? "" : messageAry[0]);
            msg.setId(AppUtility.isEmpty(messageAry[1]) ? "" : messageAry[1]);
            msg.setReqResStr(AppUtility.isEmpty(messageAry[2]) ? "" : messageAry[2]);
        }

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
