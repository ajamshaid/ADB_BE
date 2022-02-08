package com.infotech.adb.service;

import com.infotech.adb.dto.AccountDetailDTO;
import com.infotech.adb.dto.IBANVerificationRequest;
import com.infotech.adb.model.entity.AccountDetail;
import com.infotech.adb.model.repository.AccountDetailRepository;
import com.infotech.adb.silkbank.jms.MQUtility;
import com.infotech.adb.silkbank.jms.QueueIN;
import com.infotech.adb.util.AppConstants;
import com.infotech.adb.util.AppUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

@Service
@Log4j2
public class MQServices {

    @Autowired
    private AccountDetailRepository accountDetailRepository;

    @Autowired
    private QueueIN queueIN;

    // Store Csv File's data to database

    public boolean isAccountVerified(IBANVerificationRequest req) {
        log.info("isAccountDetailExists method called..");
        boolean isVerified = false;
        try {
            MQUtility.MqMessage replyMessage = queueIN.putMessage(MQUtility.buildAccountVerificationMessage(req));
            if (AppConstants.PSWResponseCodes.VERIFIED.equals(replyMessage.getReqResStr())) {
                isVerified = true;
            }
        } catch (JMSException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return isVerified;//accountDetailRepository.isExistAccountDetail(req.getIban(),req.getEmail(),req.getMobileNumber(),req.getNtn());
    }


    public AccountDetailDTO getAccountDetailsByIban(String iban) {
        log.info("getAccountDetailsByIban method called..");

        AccountDetailDTO dto = null;
        MQUtility.MqMessage replyMessage = null;
        try {
//            replyMessage = new MQUtility.MqMessage();
//            replyMessage.setReqResStr("PK88SAUD0000032000486666|BBJ PIPE INDUSTRIES LIMITED|2000486666|600|1535184|3520149834481");
            replyMessage =  queueIN.putMessage(MQUtility.buildGetAccountDetailsMessage(iban));
        } catch (JMSException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        if (!AppUtility.isEmpty(replyMessage) && !AppUtility.isEmpty(replyMessage.getReqResStr())) {
            //  Parse format as  IBAN|ACC_TITLE|ACCT_NUM|ACCT_STATUS|NTN|CNIC
            String[] acctDtlAry = replyMessage.getReqResStr().split(MQUtility.DELIMETER_DATA);
            if (!AppUtility.isEmpty(acctDtlAry) && acctDtlAry.length >= 6) {
                dto = new AccountDetailDTO();
                dto.setIban(acctDtlAry[0]);
                dto.setAccountTitle(acctDtlAry[1]);
                dto.setAccountNumber(acctDtlAry[2]);
                dto.setAccountStatus(acctDtlAry[3]);
                dto.setNtn(acctDtlAry[4]);
                dto.setCnic(acctDtlAry[5]);

                // Get and Fill AuthPM from DB

                AccountDetail accountDetail = accountDetailRepository.findByIban(iban);
                if (!AppUtility.isEmpty(accountDetail)) {
                    String autPM = accountDetail.getAuthPMImport();
                    HashSet autPMImportMap = new HashSet();
                    if (!AppUtility.isEmpty(autPM)) {
                        autPMImportMap = new HashSet<>(Arrays.asList(autPM.split(",")));

                    }
                    dto.setAuthorizedPaymentModesForImport(autPMImportMap);

                    // Export
                    autPM = accountDetail.getAuthPMExport();
                    HashSet autPMExportMap = new HashSet();
                    if (!AppUtility.isEmpty(autPM)) {
                        autPMExportMap = new HashSet<>(Arrays.asList(autPM.split(",")));
                    }
                    dto.setAuthorizedPaymentModesForExport(autPMExportMap);
                }
            }
        }
        return dto;
    }
}
