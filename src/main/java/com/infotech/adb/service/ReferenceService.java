package com.infotech.adb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.api.consumer.PSWAPIConsumer;
import com.infotech.adb.dto.BCADTO;
import com.infotech.adb.dto.BDADTO;
import com.infotech.adb.dto.FinancialTransactionExportDTO;
import com.infotech.adb.dto.FinancialTransactionImportDTO;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.model.entity.*;
import com.infotech.adb.model.repository.*;
import com.infotech.adb.util.AppConstants;
import com.infotech.adb.util.AppUtility;
import com.infotech.adb.util.OpenCsvUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class ReferenceService {

    @Autowired
    private PSWAPIConsumer consumer;

    @Autowired
    private AccountDetailRepository accountDetailRepository;

    @Autowired
    private BankNegtiveListRepository bankNegtiveListRepository;

    @Autowired
    private FinancialTransactionRepository financialTransactionRepository;

    @Autowired
    private BDARepository bdaRepository;

    @Autowired
    private BCARepository bcaRepository;

    @Autowired
    private GDRepository gdRepository;

    @Autowired
    private GDExportRepository gdExportRepository;


    @Transactional
    public void parseCSVAndSaveAccountDetails(InputStream file) throws CustomException {
        // Using ApacheCommons Csv Utils to parse CSV file
        List<AccountDetail> acctDetailList = OpenCsvUtil.parseAccountDetailsFile(file);

        if (AppUtility.isEmpty(acctDetailList)) {
            throw new NoDataFoundException("No Data Found, No Valid Object/Empty in CVS File");
        } else {
            acctDetailList.forEach((acct -> {
                acct.setCreatedOn(ZonedDateTime.now());
                acct.setUpdatedOn(ZonedDateTime.now());

                acct.setIban(acct.getIban().replaceAll("\\s+",""));
                if (!AppUtility.isEmpty(acct.getAuthPMImport())) {
                    acct.setAuthPMImport(acct.getAuthPMImport().replace("|", ","));
                }
                if (!AppUtility.isEmpty(acct.getAuthPMExport())) {
                    acct.setAuthPMExport(acct.getAuthPMExport().replace("|", ","));
                }
            }
            ));
            // Save to database
            accountDetailRepository.saveAll(acctDetailList);
        }
    }

    @Transactional
    public void parseCSVAndSaveBankNegList(InputStream file) throws CustomException {
        // Using ApacheCommons Csv Utils to parse CSV file
        List<BankNegativeList> bankNegativeLists = OpenCsvUtil.parseBankNegListFile(file);

        if (AppUtility.isEmpty(bankNegativeLists)) {
            throw new NoDataFoundException("No Data Found, No Valid Object/Empty in CVS File");
        } else {
            bankNegativeLists.forEach((negList -> {

                negList.setBankCode(AppConstants.AD_ID);
                if (!AppUtility.isEmpty(negList.getRestrictedCommoditiesForExport())) {
                    negList.setRestrictedCommoditiesForExport(negList.getRestrictedCommoditiesForExport().replace("|", ","));
                }
                if (!AppUtility.isEmpty(negList.getRestrictedCommoditiesForImport())) {
                    negList.setRestrictedCommoditiesForImport(negList.getRestrictedCommoditiesForImport().replace("|", ","));
                }

                if (!AppUtility.isEmpty(negList.getRestrictedCountriesForExport())) {
                    negList.setRestrictedCountriesForExport(negList.getRestrictedCountriesForExport().replace("|", ","));
                }
                if (!AppUtility.isEmpty(negList.getRestrictedCountriesForImport())) {
                    negList.setRestrictedCountriesForImport(negList.getRestrictedCountriesForImport().replace("|", ","));
                }

                if (!AppUtility.isEmpty(negList.getRestrictedSuppliersForExport())) {
                    negList.setRestrictedSuppliersForExport(negList.getRestrictedSuppliersForExport().replace("|", ","));
                }
                if (!AppUtility.isEmpty(negList.getRestrictedSuppliersForImport())) {
                    negList.setRestrictedSuppliersForImport(negList.getRestrictedSuppliersForImport().replace("|", ","));
                }
            }
            ));

            // save to DB
            bankNegtiveListRepository.saveAll(bankNegativeLists);
        }
    }



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


    public FinancialTransaction updateFTImportAndShare(FinancialTransactionImportDTO dto) throws JsonProcessingException {
        FinancialTransaction ft = this.updateFinancialTransaction(dto.convertToEntity());
        consumer.shareFinancialInformationImport(dto);
        return ft;
    }

    public FinancialTransaction updateFTExportAndShare(FinancialTransactionExportDTO dto) throws JsonProcessingException {
        FinancialTransaction ft = this.updateFinancialTransaction(dto.convertToEntity());
        consumer.shareFinancialInformationExport(dto);
        return ft;
    }

    @Transactional
    public FinancialTransaction updateFinancialTransaction(FinancialTransaction ftEntity) {
        log.info("updateFinancialTransaction method called..");
        return financialTransactionRepository.save(ftEntity);
    }

    /*************************************
     * BDA METHODS
     **************************************/
    public List<BDA> getAllBDA() {
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

    public BDA updateBDAAndShare(BDADTO bdadto) throws JsonProcessingException {
        BDA entity = this.updateBDA(bdadto.convertToEntity());
        consumer.shareBDAInformationImport(bdadto);
        return entity;
    }

    @Transactional
    public BDA updateBDA(BDA entity) {
        log.info("updateBDA method called..");
        return bdaRepository.save(entity);
    }

    /*************************************
     * BCA METHODS
     **************************************/
    public List<BCA> getAllBCA() {
        log.info("getAllBCA method called..");
        List<BCA> refList = null;
        refList= this.bcaRepository.findAll();
        return refList;
    }

    public BCA getBCAById(Long id) {
        log.info("getBCAById method called..");
        Optional<BCA> ref = bcaRepository.findById(id);
        return ref.get();
    }


    public BCA updateBCAAndShare(BCADTO bcadto) throws JsonProcessingException {
        BCA bca = this.updateBCA(bcadto.convertToEntity());
        consumer.shareBCAInformationExport(bcadto);
        return bca;
    }

    @Transactional
    public BCA updateBCA(BCA entity) {
        log.info("updateBCA method called..");
        return bcaRepository.save(entity);
    }


    /*************************************
     * GD  METHODS
     **************************************/
    public List<GD> getAllGD(String type) {
        log.info("getAllGD method called..");
        List<GD> refList = null;
        refList= this.gdRepository.findAll();
        return refList;
    }

    public GD getGDById(Long id) {
        log.info("getGDById method called..");
        Optional<GD> ref = gdRepository.findById(id);
        return ref.get();
    }

    @Transactional
    public GD updateGD(GD entity) {
        log.info("updateGD method called..");
        return gdRepository.save(entity);
    }


    /*************************************
     * GD  METHODS
     **************************************/
    public List<GDExport> getAllGDExport() {
        log.info("getAllGDExport method called..");
        List<GDExport> refList = null;
        refList= this.gdExportRepository.findAll();
        return refList;
    }

    public GDExport getGDExportById(Long id) {
        log.info("getGDExportById method called..");
        Optional<GDExport> ref = gdExportRepository.findById(id);
        return ref.get();
    }

    @Transactional
    public GDExport updateGDExport(GDExport entity) {
        log.info("updateGDExport method called..");
        return gdExportRepository.save(entity);
    }
}
