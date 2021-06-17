package com.infotech.adb.service;

import com.infotech.adb.model.entity.AccountDetail;
import com.infotech.adb.model.entity.BDA;
import com.infotech.adb.model.entity.BankNegativeList;
import com.infotech.adb.model.entity.FinancialTransaction;
import com.infotech.adb.model.repository.AccountDetailRepository;
import com.infotech.adb.model.repository.BDARepository;
import com.infotech.adb.model.repository.BankNegtiveListRepository;
import com.infotech.adb.model.repository.FinancialTransactionRepository;
import com.infotech.adb.util.AppUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class ReferenceService {

    @Autowired
    private AccountDetailRepository accountDetailRepository;

    @Autowired
    private BankNegtiveListRepository bankNegtiveListRepository;

    @Autowired
    private FinancialTransactionRepository financialTransactionRepository;

    @Autowired
    private BDARepository bdaRepository;

    public List<AccountDetail> getAllAccountDetails() {
        log.info("getAllAccountDetails method called..");
        return accountDetailRepository.findAll();
    }

    public AccountDetail getAccountDetailByIban(String Iban) {
        log.info("getBankById method called..");
        AccountDetail accountDetail = null;
        if (!AppUtility.isEmpty(Iban)) {
            accountDetail = accountDetailRepository.findByIban(Iban);
        }
        return accountDetail;
    }
    /*************************************
     * BANK NEGATIVE LIST METHODS
     **************************************/
    public BankNegativeList getBankNegativeListByCode(String bankCode) {
        log.info("getBankNegativeListByCode method called..");
        BankNegativeList ref = null;
        if (!AppUtility.isEmpty(bankCode)) {
            ref = bankNegtiveListRepository.findDistinctByBankCode(bankCode);
        }
        return ref;
    }

    /*************************************
     * Financial Transaction METHODS
     **************************************/
    public List<FinancialTransaction> getAllFinancialTransactionByType(String type) {
        log.info("getAllFinancialTransactionByType method called..");
        List<FinancialTransaction> refList = null;
        refList= this.financialTransactionRepository.findByType(type);
        return refList;
    }

    public FinancialTransaction getAllFinancialTransactionById(Long id) {
        log.info("getAllFinancialTransactionById method called..");
        Optional<FinancialTransaction> ref = financialTransactionRepository.findById(id);
        return ref.get();
    }

    @Transactional
    public FinancialTransaction updateFinancialTransaction(FinancialTransaction ftEntity) {
        log.info("updateFinancialTransaction method called..");
        return financialTransactionRepository.save(ftEntity);
    }

    /*************************************
     * BDA METHODS
     **************************************/
    public List<BDA> getAllBDA(String type) {
        log.info("getAllBDA method called..");
        List<BDA> refList = null;
        refList= this.bdaRepository.findAll();
        return refList;
    }

    public BDA getBDAById(Long id) {
        log.info("getBDAById method called..");
        Optional<BDA> ref = bdaRepository.findById(id);
        return ref.get();
    }

    @Transactional
    public BDA updateBDA(BDA entity) {
        log.info("updateFinancialTransaction method called..");
        return bdaRepository.save(entity);
    }
}
