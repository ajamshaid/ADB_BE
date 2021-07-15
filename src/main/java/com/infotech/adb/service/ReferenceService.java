package com.infotech.adb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.dto.*;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.model.entity.*;
import com.infotech.adb.model.repository.*;
import com.infotech.adb.psw.consumer.PSWAPIConsumerService;
import com.infotech.adb.util.AppConstants;
import com.infotech.adb.util.AppUtility;
import com.infotech.adb.util.OpenCsvUtil;
import com.infotech.adb.util.ResponseUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class ReferenceService {

    @Autowired
    private PSWAPIConsumerService pswAPIConsumerService;

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

    @Autowired
    private ChangeBankRepository cobRepository;

    @Autowired
    private GDClearanceRepository gdClearanceRepository;

    @Autowired
    private CancellationOfFTRepository cancellationOfFTRepository;

    @Autowired
    private ReversalOfBdaBcaRepository reversalOfBdaBcaRepository;

    @Autowired
    private SettlementOfFIRepository settlementOfFIRepository;

    @Autowired
    private COBGdFtRepository cobGdFtRepository;


    @Transactional
    public void parseCSVAndSaveAccountDetails(InputStream file) throws CustomException {
        // Using ApacheCommons Csv Utils to parse CSV file
        List<AccountDetail> acctDetailList = OpenCsvUtil.parseAccountDetailsFile(file);

        if (AppUtility.isEmpty(acctDetailList)) {
            throw new NoDataFoundException("No Data Found, No Valid Object/Empty in CVS File");
        } else {
            acctDetailList.forEach((acct -> {
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


    public ResponseUtility.APIResponse updateFTImportAndShare(FinancialTransactionImportDTO dto) throws JsonProcessingException {
        FinancialTransaction ft = this.updateFinancialTransaction(dto.convertToEntity());
        return pswAPIConsumerService.shareFinancialInformationImport(dto);
    }

    public ResponseUtility.APIResponse updateFTExportAndShare(FinancialTransactionExportDTO dto) throws JsonProcessingException {
        FinancialTransaction ft = this.updateFinancialTransaction(dto.convertToEntity());
        return pswAPIConsumerService.shareFinancialInformationExport(dto);
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

    public ResponseUtility.APIResponse updateBDAAndShare(BDADTO bdadto) throws JsonProcessingException {
        BDA entity = this.updateBDA(bdadto.convertToEntity());
        return pswAPIConsumerService.shareBDAInformationImport(bdadto);
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


    public ResponseUtility.APIResponse updateBCAAndShare(BCADTO bcadto) throws JsonProcessingException {
        BCA bca = this.updateBCA(bcadto.convertToEntity());
        return pswAPIConsumerService.shareBCAInformationExport(bcadto);
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
     * GD Export METHODS
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

    /*************************************
     * COB  METHODS
     **************************************/
    public List<ChangeOfBank> getAllCOB() {
        log.info("getAllCOB method called..");
        List<ChangeOfBank> refList = null;
        refList= this.cobRepository.findAll();
        return refList;
    }

    public ChangeOfBank getCOBById(Long id) {
        log.info("getCOBById method called..");
        Optional<ChangeOfBank> ref = cobRepository.findById(id);
        return ref.get();
    }

    public ResponseUtility.APIResponse updateCOBAndShare(ChangeBankRequestDTO dto) throws JsonProcessingException {
        this.updateCOB(dto.convertToEntity());
        return pswAPIConsumerService.shareCOBApprovalRejectionMsg(dto);
    }

    @Transactional
    public ChangeOfBank updateCOB(ChangeOfBank entity) {
        log.info("updateCOB method called..");
        return cobRepository.save(entity);
    }

    /*************************************
     * GD Clearance  METHODS
     **************************************/

    public List<GDClearance> getAllGDClearance() {
        log.info("getAllCOB method called..");
        List<GDClearance> refList = null;
        refList= this.gdClearanceRepository.findAll();
        return refList;
    }

    public GDClearance getGDClearanceById(Long id) {
        log.info("getCOBById method called..");
        Optional<GDClearance> ref = gdClearanceRepository.findById(id);
        return ref.get();
    }


    public ResponseUtility.APIResponse updateGDClearanceAndShare(GDClearanceDTO dto) throws JsonProcessingException {
        this.updateGDClearance(dto.convertToEntity());
        return pswAPIConsumerService.shareGDClearanceMsg(dto);
    }

    @Transactional
    public GDClearance updateGDClearance(GDClearance entity) {
        log.info("updateCOB method called..");
        return gdClearanceRepository.save(entity);
    }

    /*************************************
     * Cancellation of FT  METHODS
     **************************************/
    public List<CancellationOfFT> getAllCancellationOfFT() {
        log.info("getAllCOB method called..");
        List<CancellationOfFT> refList = null;
        refList= this.cancellationOfFTRepository.findAll();
        return refList;
    }

    public CancellationOfFT getCancellationOfFTById(Long id) {
        log.info("getCOBById method called..");
        Optional<CancellationOfFT> ref = cancellationOfFTRepository.findById(id);
        return ref.get();
    }

    public ResponseUtility.APIResponse updateCancellationOfFTAndShare(CancellationOfFTDTO dto) throws JsonProcessingException {
        this.updateCancellationOfFT(dto.convertToEntity());
        return pswAPIConsumerService.cancellationOfFinancialTransaction(dto);
    }

    @Transactional
    public CancellationOfFT updateCancellationOfFT(CancellationOfFT entity) {
        log.info("updateCOB method called..");
        return cancellationOfFTRepository.save(entity);
    }

    /*************************************
     * Reversal of BDA/BCA  METHODS
     **************************************/
    public List<ReversalOfBdaBca> getAllReversal() {
        log.info("getAllReversal method called..");
        List<ReversalOfBdaBca> refList = null;
        refList= this.reversalOfBdaBcaRepository.findAll();
        return refList;
    }

    public ReversalOfBdaBca getReversalById(Long id) {
        log.info("getReversalById method called..");
        Optional<ReversalOfBdaBca> ref = reversalOfBdaBcaRepository.findById(id);
        return ref.get();
    }

    public ResponseUtility.APIResponse updateReveralBCABDAAndShare(ReversalOfBdaBcaDTO dto) throws JsonProcessingException {
        this.updateReversalBDABCA(dto.convertToEntity());
        return pswAPIConsumerService.reversalOfBdaBca(dto);
    }

    @Transactional
    public ReversalOfBdaBca updateReversalBDABCA(ReversalOfBdaBca entity) {
        log.info("updateReversal method called..");
        return reversalOfBdaBcaRepository.save(entity);
    }

    /*************************************
     * Settlement Of FI  METHODS
     **************************************/
    public List<SettelmentOfFI> getAllSettlementOfFI() {
        log.info("getAllCOB method called..");
        List<SettelmentOfFI> refList = null;
        refList= this.settlementOfFIRepository.findAll();
        return refList;
    }

    public SettelmentOfFI getSettlementOfFIById(Long id) {
        log.info("getCOBById method called..");
        Optional<SettelmentOfFI> ref = settlementOfFIRepository.findById(id);
        return ref.get();
    }

    public ResponseUtility.APIResponse updateSettlementOfFIAndShare(SettelmentOfFIDTO dto) throws JsonProcessingException {
        this.updateSettlementOfFI(dto.convertToEntity());
        return pswAPIConsumerService.settlementOfFinInstrument(dto);
    }

    @Transactional
    public SettelmentOfFI updateSettlementOfFI(SettelmentOfFI entity) {
        log.info("updateCOB method called..");
        return settlementOfFIRepository.save(entity);
    }

    /*************************************
     * COB GD and FT  METHODS
     **************************************/
    public List<COBGdFt> getAllCOBGdFt() {
        log.info("getAllCOB method called..");
        List<COBGdFt> refList = null;
        refList= this.cobGdFtRepository.findAll();
        return refList;
    }

    public COBGdFt getCOBGdFtById(Long id) {
        log.info("getCOBById method called..");
        Optional<COBGdFt> ref = cobGdFtRepository.findById(id);
        return ref.get();
    }

    public ResponseUtility.APIResponse updateCOBGdFtAndShare(COBGdFtDTO dto) throws JsonProcessingException {
        this.updateCOBGdFt(dto.convertToEntity());
        return null;
    }

    @Transactional
    public COBGdFt updateCOBGdFt(COBGdFt entity) {
        log.info("updateCOB method called..");
        return cobGdFtRepository.save(entity);
    }
}
