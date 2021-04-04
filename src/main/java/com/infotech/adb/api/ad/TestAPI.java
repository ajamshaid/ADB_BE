package com.infotech.adb.api.ad;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.api.consumer.PSWClient;
import com.infotech.adb.dto.*;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.model.entity.AccountDetail;
import com.infotech.adb.model.entity.AuthorizedPaymentModes;
import com.infotech.adb.model.entity.User;
import com.infotech.adb.service.AccountService;
import com.infotech.adb.service.UserService;
import com.infotech.adb.util.AppUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    PSWClient pswClient;

    @Autowired
    private AccountService accountService;

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
        accountDetail.setAccountType("701");
        Set<AuthorizedPaymentModes> authorizedPaymentModesSet = new HashSet<>();
        authorizedPaymentModesSet.add(new AuthorizedPaymentModes("305", "IMPORT", accountDetail));
        authorizedPaymentModesSet.add(new AuthorizedPaymentModes("306", "IMPORT", accountDetail));
        authorizedPaymentModesSet.add(new AuthorizedPaymentModes("307", "EXPORT", accountDetail));
        authorizedPaymentModesSet.add(new AuthorizedPaymentModes("301", "EXPORT", accountDetail));

        accountDetail.setAuthorizedPaymentModesSet(authorizedPaymentModesSet);

        return new ResponseEntity<>(pswClient.updateAccountAndPMInPWS(accountDetail), HttpStatus.OK);

    }

    @RequestMapping(value = "/update-res-countries", method = RequestMethod.GET)
    public ResponseEntity<?> getRequestRequestedCountriesTest(@RequestParam String Iban) throws JsonProcessingException {

        log.info("Test .. Request update Restricted Countries");
        AccountDetail acct = accountService.getAccountDetailsByIban(Iban);

        if (!AppUtility.isEmpty(acct)) {
            RestrictedCountriesDTO dto = new RestrictedCountriesDTO(acct);
            return new ResponseEntity<>(pswClient.updateRestrictedListOFCountries(dto), HttpStatus.OK);
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
            return new ResponseEntity<>(pswClient.updateRestrictedListOFCommodities(dto), HttpStatus.OK);
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
            return new ResponseEntity<>(pswClient.updateRestrictedListOFSuppliers(dto), HttpStatus.OK);
        } else {
            throw new NoDataFoundException("No Record found for IBAN:" + Iban);
        }
    }

    @RequestMapping(value = "/update-trader-acct-status", method = RequestMethod.GET)
    public ResponseEntity<?> updateTraderAcctStatusTest(@RequestBody RequestParameter<TraderProfileStatusDTO> requestBody) throws JsonProcessingException, DataValidationException {
        log.info("Test .. Update Trader Acct Status");
        if (RequestParameter.isValidRequest(requestBody, false)) {
            TraderProfileStatusDTO dto = requestBody.getData();
            return new ResponseEntity<>(pswClient.updateTraderProfileAccountStatus(dto), HttpStatus.OK);
        } else {
            throw new NoDataFoundException("No Record found for IBAN:" + requestBody.getData());
        }
    }

    @RequestMapping(value = "/update-trader-email-mob", method = RequestMethod.GET)
    public ResponseEntity<?> updateTraderEmailAndMobTest(@RequestBody RequestParameter<TraderProfileDTO> requestBody) throws JsonProcessingException, DataValidationException {
        log.info("Test .. Update Trader Email and Mobile");
        if (RequestParameter.isValidRequest(requestBody, false)) {
            TraderProfileDTO dto = requestBody.getData();
            return new ResponseEntity<>(pswClient.updateTraderProfile(dto), HttpStatus.OK);
        } else {
            throw new NoDataFoundException("No Record found for IBAN:" + requestBody.getData());
        }

    }
}
