package com.infotech.adb.service;

import com.infotech.adb.dto.IBANVerificationRequest;
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
import java.util.List;
import java.util.Optional;

@Service
//@Transactional(value = "masterTransactionManager", rollbackFor = {Throwable.class})
@Log4j2
public class AccountService {

    @Autowired
    private AccountDetailRepository accountDetailRepository;

    @Autowired
    private QinPSW qinPSW;

    // Store Csv File's data to database

    @Transactional
    public void storeCSVToDB(InputStream file) {

            // Using ApacheCommons Csv Utils to parse CSV file
            List<AccountDetail> acctDetailList = OpenCsvUtil.parseCsvFile(file);

            if(AppUtility.isEmpty(acctDetailList)) {
                throw new NoDataFoundException("No Data Found, No Valid Object/Empty in CVS File");
            }else {
                acctDetailList.forEach((acct ->{
                    acct.setCreatedOn(ZonedDateTime.now());
                    acct.setUpdatedOn(ZonedDateTime.now());
                    acct.setAuthPMImport(acct.getAuthPMImport().replace("|", ","));
                    acct.setAuthPMExport(acct.getAuthPMExport().replace("|", ","));
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
            if(AppConstants.PSWResponseCodes.VERIFIED.equals(replyMessage.getReqResStr())){
                isVerified = true;
            }
        } catch (JMSException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return isVerified ;//accountDetailRepository.isExistAccountDetail(req.getIban(),req.getEmail(),req.getMobileNumber(),req.getNtn());
    }

    public AccountDetail getAccountDetailsByIban(String iban) {
        log.info("getAccountDetailsByIban method called..");
        return accountDetailRepository.findByIban(iban);
    }

    public List<AccountDetail> getAllAccountDetails(Boolean isSuspended) {
        log.info("getAllAccountDetails method called..");
        if (AppUtility.isEmpty(isSuspended)) {
            return accountDetailRepository.findAll();
        } else {
            return accountDetailRepository.findAll();
        }
    }

    public Optional<AccountDetail> getAccountDetailById(Long id) {
        log.info("getAccountDetailById method called..");
        if (!AppUtility.isEmpty(id)) {
            return accountDetailRepository.findById(id);
        }
        return Optional.empty();
    }

    public AccountDetail createAccountDetail(AccountDetail accountDetail) {
        log.info("createAccountDetail method called..");
        return accountDetailRepository.save(accountDetail);
    }

    public AccountDetail updateAccountDetail(AccountDetail accountDetail) {
        log.info("updateAccountDetail method called..");
     //   accountDetail.setUpdatedOn(ZonedDateTime.now());
        return accountDetailRepository.save(accountDetail);
    }

    public void deleteAccountDetailById(Long id) {
        log.info("deleteAccountDetailById method called..");
        accountDetailRepository.deleteById(id);
    }
}
