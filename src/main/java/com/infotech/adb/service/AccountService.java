package com.infotech.adb.service;

import com.infotech.adb.dto.IBANVerificationRequest;
import com.infotech.adb.model.entity.AccountDetail;
import com.infotech.adb.model.repository.AccountDetailRepository;
import com.infotech.adb.util.AppUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
//@Transactional(value = "masterTransactionManager", rollbackFor = {Throwable.class})
@Log4j2
public class AccountService {

    @Autowired
    private AccountDetailRepository accountDetailRepository;


    public boolean isAccountDetailExists(IBANVerificationRequest req) {
        log.info("isAccountDetailExists method called..");
        return accountDetailRepository.isExistAccountDetail(req.getIban(),req.getEmail(),req.getMobileNumber(),req.getNtn());
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
        accountDetail.setUpdatedOn(ZonedDateTime.now());
        return accountDetailRepository.save(accountDetail);
    }

    public void deleteAccountDetailById(Long id) {
        log.info("deleteAccountDetailById method called..");

        accountDetailRepository.deleteById(id);
    }
}
