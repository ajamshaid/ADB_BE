package com.infotech.adb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.dto.*;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DataValidationException;
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
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.*;

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
    private TransCounterRepository transCounterRepository;

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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemInformationRepository itemInformationRepository;


    private synchronized Integer getNextCounter(String tranType) {

        Integer counter = 1;
        TransCounter tc = transCounterRepository.findDistinctByBankCode(AppConstants.AD_ID);

        if ("IMP".equalsIgnoreCase(tranType)) {
            counter = tc.getImp();
            tc.setImp(tc.getImp() + 1);
        } else if ("EXP".equalsIgnoreCase(tranType)) {
            counter = tc.getExp();
            tc.setExp(tc.getExp() + 1);
        } else if ("BDA".equalsIgnoreCase(tranType)) {
            counter = tc.getBda();
            tc.setBda(tc.getBda() + 1);
        } else if ("BCA".equalsIgnoreCase(tranType)) {
            counter = tc.getBca();
            tc.setBca(tc.getBca() + 1);
        } else if ("COB".equalsIgnoreCase(tranType)) {
            counter = tc.getCob();
            tc.setCob(tc.getCob() + 1);
        }
        transCounterRepository.save(tc);
        return counter;
    }

    @Transactional
    public void parseCSVAndSaveAccountDetails(InputStream file) throws CustomException {
        // Using ApacheCommons Csv Utils to parse CSV file
        List<AccountDetail> acctDetailList = OpenCsvUtil.parseAccountDetailsFile(file);
        Map<String, AccountDetail> acctMap = new HashMap<>();

        if (AppUtility.isEmpty(acctDetailList)) {
            throw new NoDataFoundException("No Data Found, No Valid Object/Empty in CVS File");
        } else {
            acctDetailList.forEach((acct -> {
                if(AppUtility.isEmpty(accountDetailRepository.findByIban(acct.getIban()))){
                    //if record doesn't exist in DB then Add
                    acct.setIban(acct.getIban().replaceAll("\\s+", ""));
                    if (!AppUtility.isEmpty(acct.getAuthPMImport())) {
                        acct.setAuthPMImport(acct.getAuthPMImport().replace("|", ","));
                    }
                    if (!AppUtility.isEmpty(acct.getAuthPMExport())) {
                        acct.setAuthPMExport(acct.getAuthPMExport().replace("|", ","));
                    }
                    acctMap.put(acct.getIban(),acct);
                }
            }
            ));
            // Save to database
            if(!acctMap.isEmpty()) {
                accountDetailRepository.saveAll(acctMap.values());
            }
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
    public List<FinancialTransaction> searchFT(String ftType, String iban, String name, String fromDate, String toDate, String status) throws ParseException {
        log.info("searchLogs method called..");
        if (AppUtility.isEmpty(name)) {
            name = "%";
        }
        Date date1 = null, date2 = null;

        if (!AppUtility.isEmpty(fromDate)) {
            date1 = new SimpleDateFormat("dd-MM-yyyy").parse(fromDate);
        } else {
            date1 = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1970");
        }
        if (!AppUtility.isEmpty(toDate)) {
            date2 = new SimpleDateFormat("dd-MM-yyyy").parse(toDate);
        } else {
            date2 = new Date();
        }
        return financialTransactionRepository.searchFT(ftType,iban, name, date1, date2,status);
    }

    public List<FinancialTransaction> getAllFinancialTransactionByType(String type, String status) {
        log.info("getAllFinancialTransactionByType method called..");
        List<FinancialTransaction> refList = null;
        refList = this.financialTransactionRepository.findByTypeAndStatusOrderByLastModifiedDateDesc (type,status );
        return refList;
    }

    public FinancialTransaction getAllFinancialTransactionById(Long id) {
        log.info("getAllFinancialTransactionById method called..");
        Optional<FinancialTransaction> ref = financialTransactionRepository.findById(id);
        return ref.get();
    }

    public ItemInformation saveItemInfo(Long id, ItemInformationExportDTO dto) {
        ItemInformation itemInformation= dto.convertToEntity();
        itemInformation.setFinancialTransaction(new FinancialTransaction(id));
        return itemInformationRepository.save(itemInformation);
    }

    public void deleteItemById(Long id) {
        log.info("deleteItemById method called..");
        itemInformationRepository.deleteById(id);
    }

    @Transactional
    public ResponseUtility.APIResponse updateFTImportAndShare(FinancialTransactionImportDTO dto) throws JsonProcessingException {

        String finUniqNo = "";
        if(AppUtility.isEmpty(dto.getFinInsUniqueNumber())) {
            finUniqNo = AppUtility.generateUniqPSWNumberFormat("IMP", this.getNextCounter("IMP"));
            dto.setFinInsUniqueNumber(finUniqNo);
        }

        FinancialTransaction ft = this.updateFinancialTransaction(dto.convertToEntity());

       // ResponseUtility.APIResponse pswResponse =   ResponseUtility.TestAPISuccessResponse();
        ResponseUtility.APIResponse pswResponse =   pswAPIConsumerService.shareFinancialInformationImport(dto);
        String respCode = pswResponse.getMessage().getCode();
        if (respCode.equals("" + HttpStatus.OK.value())) {
            financialTransactionRepository.updateStatus(ft.getId(), AppConstants.RecordStatuses.PUSHED_TO_PSW);
        }

        return pswResponse;
    }

    @Transactional
    public ResponseUtility.APIResponse updateFTExportAndShare(FinancialTransactionExportDTO dto) throws JsonProcessingException {

        String finUniqNo = "";
        if(AppUtility.isEmpty(dto.getFinInsUniqueNumber())) {
            finUniqNo = AppUtility.generateUniqPSWNumberFormat("EXP", this.getNextCounter("EXP"));
            dto.setFinInsUniqueNumber(finUniqNo);
        }
        FinancialTransaction ft = this.updateFinancialTransaction(dto.convertToEntity());

     //   ResponseUtility.APIResponse pswResponse =  ResponseUtility.TestAPISuccessResponse();
        ResponseUtility.APIResponse pswResponse =  pswAPIConsumerService.shareFinancialInformationExport(dto);
        String respCode = pswResponse.getMessage().getCode();
        if (respCode.equals("" + HttpStatus.OK.value())) {
            financialTransactionRepository.updateStatus(ft.getId(), AppConstants.RecordStatuses.PUSHED_TO_PSW);
        }
        return pswResponse;
    }

    @Transactional
    public FinancialTransaction updateFinancialTransaction(FinancialTransaction ftEntity) {
        log.info("updateFinancialTransaction method called..");
        return financialTransactionRepository.save(ftEntity);
    }

    /*************************************
     * BDA METHODS
     **************************************/
    public List<BDA> getAllBDALastModified() {
        log.info("getAllBDA method called..");
        List<BDA> refList = null;
        refList = this.bdaRepository.findAllByOrderByLastModifiedDateDesc();
        return refList;
    }

    public BDA getBDAById(Long id) {
        log.info("getBDAById method called..");
        Optional<BDA> ref = bdaRepository.findById(id);
        return ref.get();
    }
@Transactional
    public ResponseUtility.APIResponse updateBDAAndShare(BDADTO bdadto) throws JsonProcessingException {

        String uniqNo = AppUtility.generateUniqPSWNumberFormat("BDA", this.getNextCounter("BDA"));
        //     dto.setFinInsUniqueNumber(finUniqNo);
        bdadto.setBdaUniqueIdNumber(uniqNo);
        BDA entity = this.updateBDA(bdadto.convertToEntity());

      //     ResponseUtility.APIResponse pswResponse =  ResponseUtility.TestAPISuccessResponse();
        ResponseUtility.APIResponse pswResponse =  pswAPIConsumerService.shareBDAInformationImport(bdadto);
        String respCode = pswResponse.getMessage().getCode();
        if (respCode.equals("" + HttpStatus.OK.value())) {
            bdaRepository.updateStatus(entity.getId(), AppConstants.RecordStatuses.PUSHED_TO_PSW);
        }

        return pswResponse;
    }

    @Transactional
    public BDA updateBDA(BDA entity) {
        log.info("updateBDA method called..");
        return bdaRepository.save(entity);
    }

    /*************************************
     * BCA METHODS
     **************************************/
    public List<BCA> getAllBCALastModified() {
        log.info("getAllBCA method called..");
        List<BCA> refList = null;
        refList = this.bcaRepository.findAllByOrderByLastModifiedDateDesc();
        return refList;
    }

    public BCA getBCAById(Long id) {
        log.info("getBCAById method called..");
        Optional<BCA> ref = bcaRepository.findById(id);
        return ref.get();
    }

    @Transactional
    public ResponseUtility.APIResponse updateBCAAndShare(BCADTO bcadto) throws JsonProcessingException {

        String uniqNo = AppUtility.generateUniqPSWNumberFormat("BCA", this.getNextCounter("BCA"));
        bcadto.setBcaUniqueIdNumber(uniqNo);

        BCA entity = this.updateBCA(bcadto.convertToEntity());

     //     ResponseUtility.APIResponse pswResponse =  ResponseUtility.TestAPISuccessResponse();
        ResponseUtility.APIResponse pswResponse =  pswAPIConsumerService.shareBCAInformationExport(bcadto);
        String respCode = pswResponse.getMessage().getCode();
        if (respCode.equals("" + HttpStatus.OK.value())) {
            bcaRepository.updateStatus(entity.getId(), AppConstants.RecordStatuses.PUSHED_TO_PSW);
        }
        return pswResponse;
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
        refList = this.gdRepository.findAll();
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
        refList = this.gdExportRepository.findAll();
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
    public List<ChangeOfBank> getAllLastModifiedCOB() {
        log.info("getAllCOB method called..");
        List<ChangeOfBank> refList = null;
        refList = this.cobRepository.findAllByOrderByLastModifiedDateDesc();
        return refList;
    }

    public ChangeOfBank getCOBById(Long id) {
        log.info("getCOBById method called..");
        Optional<ChangeOfBank> ref = cobRepository.findById(id);
        return ref.get();
    }

    public ResponseUtility.APIResponse updateCOBAndShare(ChangeBankRequestDTO dto) throws JsonProcessingException {

     //   String uniqNo = AppUtility.generateUniqPSWNumberFormat("COB", this.getNextCounter("COB"));
   //     dto.setCobUniqueIdNumber(uniqNo);

        this.updateCOB(dto.convertToEntity());
       // return pswAPIConsumerService.shareCOBApprovalRejectionMsg(dto);
        return pswAPIConsumerService.shareCOBApprovalRejectionMsgByOldAD(dto);
    }

    @Transactional
    public ChangeOfBank updateCOB(ChangeOfBank entity) {
        log.info("updateCOB method called..");
        return cobRepository.save(entity);
    }

    /*************************************
     * GD Clearance  METHODS
     **************************************/

    public List<GDClearance> getLastModifiedGDClearance() {
        log.info("getAllCOB method called..");
        List<GDClearance> refList = null;
        refList = this.gdClearanceRepository.findAllByOrderByLastModifiedDateDesc();
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
    public List<CancellationOfFT> getLastModifiedCancellationOfFT() {
        log.info("getAllCOB method called..");
        List<CancellationOfFT> refList = null;
        refList = this.cancellationOfFTRepository.findAllByOrderByLastModifiedDateDesc();
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
    public List<ReversalOfBdaBca> getLastModifiedReversal() {
        log.info("getAllReversal method called..");
        List<ReversalOfBdaBca> refList = null;
        refList = this.reversalOfBdaBcaRepository.findAllByOrderByLastModifiedDateDesc();
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
    public List<SettelmentOfFI> getLastModifiedSettlementOfFI() {
        log.info("getAllCOB method called..");
        List<SettelmentOfFI> refList = null;
        refList = this.settlementOfFIRepository.findAllByOrderByLastModifiedDateDesc();
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
    public List<COBGdFt> getAllLastModifiedCOBGdFt() {
        log.info("getAllCOB method called..");
        List<COBGdFt> refList = null;
        refList = this.cobGdFtRepository.findAllByOrderByLastModifiedDateDesc();
        return refList;
    }

    public COBGdFt getCOBGdFtById(Long id) {
        log.info("getCOBById method called..");
        Optional<COBGdFt> ref = cobGdFtRepository.findById(id);
        return ref.get();
    }

    @Transactional
    public COBGdFt updateCOBGdFt(COBGdFt entity) {
        log.info("updateCOB method called..");
        return cobGdFtRepository.save(entity);
    }

    /*************************************
     * USER METHODS
     **************************************/

    public List<User> getAllUser() {
        log.info("getAllUser method called..");
        List<User> refList = null;
        refList = this.userRepository.findAll();
        return refList;
    }

    public User getUserById(Long id) {
        log.info("getUserById method called..");
        Optional<User> ref = userRepository.findById(id);
        return ref.get();
    }

    public User getUserByUserName(String userName) {
        log.info("getUserById method called..");
        User u = userRepository.findByUsername(userName);
        return u;
    }
    @Transactional
    public User createUpdateUser(User entity) {
        log.info("updateUser method called..");

        if(AppUtility.isEmpty(entity.getId())){ // if new User then Encrypt Password.
            entity.setPassword(new BCryptPasswordEncoder().encode(entity.getPassword()));
        }

        return userRepository.save(entity);
    }

    @Transactional
    public User resetPassword(User newUser) throws DataValidationException, NoDataFoundException {
        log.info("resetPassword method called..");
        Optional<User> userOptional = userRepository.findById(newUser.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
           // if (!BCrypt.checkpw(user.getPassword(), newUser.getPassword())) {
                user.setPassword(new BCryptPasswordEncoder().encode(newUser.getPassword()));
                userRepository.save(user);
//            } else {
//                throw new DataValidationException(AppUtility.getResourceMessage("user.password.match"));
//            }
        } else {
            throw new NoDataFoundException(AppUtility.getResourceMessage("user.not.found"));
        }
        return userOptional.get();
    }
}
