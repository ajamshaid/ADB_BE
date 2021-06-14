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
    public static final String MSG_TYPE_ACCT_DETAILS = "PSW002";
    public static final String MSG_TYPE_FIN_TRANS_IMPORT = "PSW511";
    public static final String DELIMETER_MSG = "!";
    public static final String DELIMETER_DATA = "\\|";
    public static final String DELIMETER_MULTIPLE_DATA = "\\^";

    public static HashMap<String, MqUtility.MqMessage> objectLockingMap = new HashMap<>();


    // Message 4.1 Acct Verification Message
    public static MqMessage buildAccountVerificationMessage(IBANVerificationRequest req) {
        MqMessage msg = new MqMessage();
        msg.setId(AppUtility.generateRandomUniqString());
        msg.setType(MSG_TYPE_ACCT_VERIFICATION);

        StringBuffer sb = new StringBuffer();
        sb.append(msg.getType()).append(DELIMETER_MSG).append(msg.getId())
                .append(DELIMETER_MSG).append(req.getNtn())
                .append(DELIMETER_MSG).append(req.getIban())
                .append(DELIMETER_MSG).append(req.getEmail())
                .append(DELIMETER_MSG).append(req.getMobileNumber());
        msg.setReqResStr(sb.toString());
        return msg;
    }

    // Message 4.2  Get Acct Details Message
    public static MqMessage buildGetAccountDetailsMessage(String IBAN ){
        MqMessage msg = new MqMessage();

        msg.setId(AppUtility.generateRandomUniqString());
        msg.setType(MSG_TYPE_ACCT_DETAILS);

        StringBuffer sb = new StringBuffer();
        sb.append(msg.getType()).append(DELIMETER_MSG).append(msg.getId())
                .append(DELIMETER_MSG).append(IBAN);
        msg.setReqResStr(sb.toString());
        return msg;
    }

    public static MqMessage parseReplyMessage(String message) {
        MqMessage msg = null;
        String[] messageAry = message.split(DELIMETER_MSG);
        if(AppUtility.isEmpty(messageAry) || messageAry.length < 2){
            log.debug("Invalid Message ");
        }else {
            msg = new MqMessage();
            msg.setType(AppUtility.isEmpty(messageAry[0]) ? "" : messageAry[0]);
            msg.setId(AppUtility.isEmpty(messageAry[1]) ? "" : messageAry[1]);
            msg.setReqResStr((messageAry.length > 2 && !AppUtility.isEmpty(messageAry[2]) )? messageAry[2] : "" );
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
