package com.infotech.adb.service;

import com.infotech.adb.model.entity.AccountDetail;
import com.infotech.adb.model.repository.AccountDetailRepository;
import com.infotech.adb.util.AppUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class ReferenceService {
    @Autowired
    private AccountDetailRepository accountDetailRepository;

    public List<AccountDetail> getAllAccountDetails() {
        log.info("getAllAccountDetails method called..");
        return accountDetailRepository.findAll();
    }

    public AccountDetail getAccountDetailByIban(String Iban) {
        log.info("getBankById method called..");
        if (!AppUtility.isEmpty(Iban)) {
            return accountDetailRepository.findByIban(Iban);
        }
        return null;
    }
}
