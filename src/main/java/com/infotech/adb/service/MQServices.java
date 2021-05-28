package com.infotech.adb.service;

import com.infotech.adb.dto.AccountDetailDTO;
import com.infotech.adb.dto.IBANVerificationRequest;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.jms.MqUtility;
import com.infotech.adb.jms.QinPSW;
import com.infotech.adb.model.entity.AccountDetail;
import com.infotech.adb.model.repository.AccountDetailRepository;
import com.infotech.adb.util.AppConstants;
import com.infotech.adb.util.AppUtility;
import com.infotech.adb.util.OpenCsvUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
@Log4j2
public class MQServices {

    @Autowired
    private AccountDetailRepository accountDetailRepository;

    @Autowired
    private QinPSW qinPSW;

    // Store Csv File's data to database

    @Transactional
    public void storeCSVToDB(InputStream file) throws CustomException {
        // Using ApacheCommons Csv Utils to parse CSV file
        List<AccountDetail> acctDetailList = OpenCsvUtil.parseCsvFile(file);

        if (AppUtility.isEmpty(acctDetailList)) {
            throw new NoDataFoundException("No Data Found, No Valid Object/Empty in CVS File");
        } else {
            acctDetailList.forEach((acct -> {
                acct.setCreatedOn(ZonedDateTime.now());
                acct.setUpdatedOn(ZonedDateTime.now());

                acct.setIban(acct.getIban().replaceAll("\\s+",""));
                if (!AppUtility.isEmpty(acct.getAuthPMImport())) {
                    acct.setAuthPMImport(acct.getAuthPMImport().replace(MqUtility.DELIMETER_DATA, ","));
                }
                if (!AppUtility.isEmpty(acct.getAuthPMExport())) {
                    acct.setAuthPMExport(acct.getAuthPMExport().replace(MqUtility.DELIMETER_DATA, ","));
                }
            }
            ));
            // Save customers to database
            accountDetailRepository.saveAll(acctDetailList);
        }
    }

    public boolean isAccountVerified(IBANVerificationRequest req) {
        log.info("isAccountDetailExists method called..");
        boolean isVerified = false;
        try {
            MqUtility.MqMessage replyMessage = qinPSW.putMessage(MqUtility.buildAccountVerificationMessage(req));
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
        MqUtility.MqMessage replyMessage = null;
        try {
            replyMessage = qinPSW.putMessage(MqUtility.buildGetAccountDetailsMessage(iban));
        } catch (JMSException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        if (!AppUtility.isEmpty(replyMessage) && !AppUtility.isEmpty(replyMessage.getReqResStr())) {
            //  Parse format as  IBAN|ACC_TITLE|ACCT_NUM|ACCT_STATUS|NTN|CNIC
            String[] acctDtlAry = replyMessage.getReqResStr().split("\\"+MqUtility.DELIMETER_DATA);
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
                    if (!AppUtility.isEmpty(autPM)) {
                        dto.setAuthorizedPaymentModesForImport(new HashSet<>(Arrays.asList(autPM.split(","))));
                    }
                    autPM = accountDetail.getAuthPMExport();
                    if (!AppUtility.isEmpty(autPM)) {
                        dto.setAuthorizedPaymentModesForExports(new HashSet<>(Arrays.asList(autPM.split(","))));
                    }
                }

            }
        }
        return dto;
    }
}
