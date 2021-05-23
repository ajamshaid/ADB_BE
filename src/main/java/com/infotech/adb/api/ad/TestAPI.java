package com.infotech.adb.api.ad;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.api.consumer.PSWAPIConsumer;
import com.infotech.adb.dto.*;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.model.entity.*;
import com.infotech.adb.model.repository.BDARepository;
import com.infotech.adb.model.repository.FinancialTransactionRepository;
import com.infotech.adb.service.AccountService;
import com.infotech.adb.service.UserService;
import com.infotech.adb.util.AppConstants;
import com.infotech.adb.util.AppUtility;
import com.infotech.adb.util.OpenCsvUtil;
import com.infotech.adb.util.ResponseUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/test")
@Log4j2
public class TestAPI {

    @Autowired
    UserService userService;

    @Autowired
    PSWAPIConsumer pswApiConsumer;

    @Autowired
    private AccountService accountService;

    @Autowired
    private FinancialTransactionRepository financialTransactionRepository;

    @Autowired
    private BDARepository bdaRepository;

    @PostMapping("/upload/iban")
    public ResponseEntity<?> uploadSingleCSVFile(@RequestParam("csvfile") MultipartFile csvfile) throws CustomException {

        String message = "";
// Checking the upload-file's name before processing

        if (csvfile.getOriginalFilename().isEmpty()) {
            ResponseUtility.exceptionResponse(new DataValidationException("No selected file to upload! Please do the checking"),"");
        }

        // checking the upload file's type is CSV or NOT

        if(!OpenCsvUtil.isCSVFile(csvfile)) {
            ResponseUtility.exceptionResponse(new DataValidationException("Error: this is not a CSV file!"),"");
        }

        try {
            // save file data to database
            accountService.storeCSVToDB(csvfile.getInputStream());

        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e, AppConstants.DBConstraints.UNIQ_IBAN);
        }
        return new ResponseEntity<>(csvfile.getOriginalFilename()+ " : Upload File Successfully!", HttpStatus.OK);
    }



    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<?> getUsers(UriComponentsBuilder ucBuilder) {

        log.info("Test .. Fetch All Users Method");
        List<User> users = userService.findAllUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/update-info", method = RequestMethod.GET)
    public ResponseEntity<?> getRequestUpdateInfoTest() throws JsonProcessingException {

        log.info("Test .. Request update Info");
        AccountDetail accountDetail = new AccountDetail();
        accountDetail.setIban("PK 123213123");
        accountDetail.setAccountStatus("600");
     //   accountDetail.setAccountType("701");
        Set<AuthorizedPaymentModes> authorizedPaymentModesSet = new HashSet<>();
        authorizedPaymentModesSet.add(new AuthorizedPaymentModes("305", "IMPORT", accountDetail));
        authorizedPaymentModesSet.add(new AuthorizedPaymentModes("306", "IMPORT", accountDetail));
        authorizedPaymentModesSet.add(new AuthorizedPaymentModes("307", "EXPORT", accountDetail));
        authorizedPaymentModesSet.add(new AuthorizedPaymentModes("301", "EXPORT", accountDetail));

      //  accountDetail.setAuthorizedPaymentModesSet(authorizedPaymentModesSet);

        return new ResponseEntity<>(pswApiConsumer.updateAccountAndPMInPWS(accountDetail), HttpStatus.OK);

    }

    @RequestMapping(value = "/update-res-countries", method = RequestMethod.GET)
    public ResponseEntity<?> getRequestRequestedCountriesTest(@RequestParam String Iban) throws JsonProcessingException {

        log.info("Test .. Request update Restricted Countries");
        AccountDetail acct = accountService.getAccountDetailsByIban(Iban);

        if (!AppUtility.isEmpty(acct)) {
            RestrictedCountriesDTO dto = new RestrictedCountriesDTO(acct);
            return new ResponseEntity<>(pswApiConsumer.updateRestrictedListOFCountries(dto), HttpStatus.OK);
        } else {
            throw new NoDataFoundException("No Record found for IBAN:" + Iban);
        }

    }

    @RequestMapping(value = "/update-res-commodities", method = RequestMethod.GET)
    public ResponseEntity<?> getRequestRequestedCommoditiesTest(@RequestParam String Iban) throws JsonProcessingException {

        log.info("Test .. Request update Restricted Commodities");
        AccountDetail acct = accountService.getAccountDetailsByIban(Iban);

        if (!AppUtility.isEmpty(acct)) {
            RestrictedCommoditiesDTO dto = new RestrictedCommoditiesDTO(acct);
            return new ResponseEntity<>(pswApiConsumer.updateRestrictedListOFCommodities(dto), HttpStatus.OK);
        } else {
            throw new NoDataFoundException("No Record found for IBAN:" + Iban);
        }

    }

    @RequestMapping(value = "/update-res-suppliers", method = RequestMethod.GET)
    public ResponseEntity<?> getRequestRequestedSuppliersTest(@RequestParam String Iban) throws JsonProcessingException {

        log.info("Test .. Request update Restricted Suppliers");
        AccountDetail acct = accountService.getAccountDetailsByIban(Iban);

        if (!AppUtility.isEmpty(acct)) {
            RestrictedSuppliersDTO dto = new RestrictedSuppliersDTO(acct);
            return new ResponseEntity<>(pswApiConsumer.updateRestrictedListOFSuppliers(dto), HttpStatus.OK);
        } else {
            throw new NoDataFoundException("No Record found for IBAN:" + Iban);
        }
    }

    @RequestMapping(value = "/update-trader-acct-status", method = RequestMethod.GET)
    public ResponseEntity<?> updateTraderAcctStatusTest(@RequestBody RequestParameter<TraderProfileStatusDTO> requestBody) throws JsonProcessingException, DataValidationException {
        log.info("Test .. Update Trader Acct Status");
        if (RequestParameter.isValidRequest(requestBody, false)) {
            TraderProfileStatusDTO dto = requestBody.getData();
            return new ResponseEntity<>(pswApiConsumer.updateTraderProfileAccountStatus(dto), HttpStatus.OK);
        } else {
            throw new NoDataFoundException("No Record found for IBAN:" + requestBody.getData());
        }
    }

    @RequestMapping(value = "/update-trader-email-mob", method = RequestMethod.GET)
    public ResponseEntity<?> updateTraderEmailAndMobTest(@RequestBody RequestParameter<TraderProfileDTO> requestBody) throws JsonProcessingException, DataValidationException {
        log.info("Test .. Update Trader Email and Mobile");
        if (RequestParameter.isValidRequest(requestBody, false)) {
            TraderProfileDTO dto = requestBody.getData();
            return new ResponseEntity<>(pswApiConsumer.updateTraderProfile(dto), HttpStatus.OK);
        } else {
            throw new NoDataFoundException("No Record found for IBAN:" + requestBody.getData());
        }

    }


    @RequestMapping(value = "/import/share-fin-trans-data", method = RequestMethod.GET)
    public ResponseEntity<?> shareFinTransDataForImportsTest() throws JsonProcessingException{
        log.info("Test .. Share Fin Transaction Data Details");

        FinancialTransaction fn =  financialTransactionRepository.findById(1L).get();

        FinancialTransactionImportDTO dto = new FinancialTransactionImportDTO(fn);
            return new ResponseEntity<>(pswApiConsumer.shareFinancialInformationImport(dto), HttpStatus.OK);

    }


    //Testing for    5.1.3.	Message 3 – Sharing of BDA Information by AD with PSW
    @RequestMapping(value = "/import/share-bda", method = RequestMethod.GET)
    public ResponseEntity<?> shareBDAForImportsTest() throws JsonProcessingException{
        log.info("Test >>>> 5.1.3. Message 3 – Sharing of BDA Information [IMPORT] by AD with PSW ");

        BDA bda =  bdaRepository.findById(1L).get();

        BDAImportDTO dto = new BDAImportDTO();
        dto.convertToDTO(bda,false);

        return new ResponseEntity<>(pswApiConsumer.shareBDAInformationImport(dto), HttpStatus.OK);

    }

    @RequestMapping(value = "/export/share-fin-trans-data", method = RequestMethod.GET)
    public ResponseEntity<?> shareFinTransDataForExportTest() throws JsonProcessingException{
        log.info("Test .. Share Fin Transaction Data Details");

        FinancialTransaction fn =  financialTransactionRepository.findById(2L).get();

        FinancialTransactionExportDTO dto = new FinancialTransactionExportDTO(fn);
        return new ResponseEntity<>(pswApiConsumer.shareFinancialInformationExport(dto), HttpStatus.OK);

    }

    //Testing for    5.2.3.	Message 3 – Sharing of BCA Information Export by AD with PSW
    @RequestMapping(value = "/export/share-bca", method = RequestMethod.GET)
    public ResponseEntity<?> shareBCAForExportTest(
            @RequestBody RequestParameter<BCAExportDTO> requestBody
    ) throws JsonProcessingException{
        log.info("Test >>>> 5.2.3. Message 3 – Sharing of BCA Information [EXPORT] by AD with PSW ");
        BCAExportDTO dto = requestBody.getData();

        return new ResponseEntity<>(pswApiConsumer.shareBCAInformationExport(dto), HttpStatus.OK);
    }

    //Testing for    6.1.	Message 1 – Sharing of Cash Margin Message by AD with PSW
    @RequestMapping(value = "/share-cash-margin", method = RequestMethod.GET)
    public ResponseEntity<?> shareCashMarginTest(
            @RequestBody RequestParameter<CashMarginDTO> requestBody
    ) throws JsonProcessingException{
        log.info("Test >>>> 6.1. Message 1 – Sharing of Cash Margin Message by AD with PSW ");
        CashMarginDTO dto = requestBody.getData();

        return new ResponseEntity<>(pswApiConsumer.shareCashMarginMessage(dto), HttpStatus.OK);
    }

    //Testing for  7.1.3.	Message 1 – Sharing of Change of Bank request approval/rejection message by AD with PSW
    @RequestMapping(value = "/share-cob-status", method = RequestMethod.GET)
    public ResponseEntity<?> shareCOBStatusTest(
            @RequestBody RequestParameter<ChangeBankRequestDTO> requestBody
    ) throws JsonProcessingException{
        log.info("Test >>>> 6.1. Message 1 – Sharing of Cash Margin Message by AD with PSW ");
        ChangeBankRequestDTO dto = requestBody.getData();

        return new ResponseEntity<>(pswApiConsumer.shareCOBApprovalRejectionMsg(dto), HttpStatus.OK);
    }


    //Testing for  9.1.	Message 1 – Cancellation of Financial Transaction by AD (Import/Export)
    @RequestMapping(value = "/cancel-fin-trans", method = RequestMethod.GET)
    public ResponseEntity<?> cancelFinTransTest(
            @RequestBody RequestParameter<TradeTransactionDTO> requestBody
    ) throws JsonProcessingException{
        log.info("Test >>>> 9.1. Message 1 – Cancellation of Financial Transaction by AD (Import/Export) ");
        TradeTransactionDTO dto = requestBody.getData();

        return new ResponseEntity<>(pswApiConsumer.cancelFinancialTransaction(dto), HttpStatus.OK);
    }

    //Testing for  10.1.	Message 1 –– Reversal of BDA/BCA Message by AD to PSW
    @RequestMapping(value = "/reverse-bda-bca", method = RequestMethod.GET)
    public ResponseEntity<?> reverseBdaBcaTest(
            @RequestBody RequestParameter<TradeTransBDAInfoDTO> requestBody
    ) throws JsonProcessingException{
        log.info("Test >>>> 10.1. Message 1 – – Reversal of BDA/BCA Message by AD to PSW ");
        TradeTransBDAInfoDTO dto = requestBody.getData();

        return new ResponseEntity<>(pswApiConsumer.reversalOfBdaBca(dto), HttpStatus.OK);
    }

    //Testing for  11.1.	Message 1 –Settlement of Financial Transaction by AD (Import/Export):
    @RequestMapping(value = "/settlement-fin-trans", method = RequestMethod.GET)
    public ResponseEntity<?> settlementOfFinTransTest(
            @RequestBody RequestParameter<TradeTransSettlementDTO> requestBody
    ) throws JsonProcessingException{
        log.info("Test >>>> 10.1. Message 1 –Settlement of Financial Transaction by AD (Import/Export): ");
        TradeTransSettlementDTO dto = requestBody.getData();

        return new ResponseEntity<>(pswApiConsumer.settlementOfFinTrans(dto), HttpStatus.OK);
    }
}
