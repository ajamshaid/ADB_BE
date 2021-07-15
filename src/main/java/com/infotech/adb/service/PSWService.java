package com.infotech.adb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.dto.*;
import com.infotech.adb.model.repository.AccountDetailRepository;
import com.infotech.adb.psw.consumer.PSWAPIConsumerService;
import com.infotech.adb.util.ResponseUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class PSWService {

    @Autowired
    PSWAPIConsumerService pswapiConsumerService;

    @Autowired
    AccountDetailRepository accountDetailRepository;

    @Transactional
    public ResponseUtility.APIResponse shareUpdatedAuthPMs(AccountPMDTO dto) {
        ResponseUtility.APIResponse pswResponse = null;

        String authPMImport = String.join(",", dto.getAuthorizedPaymentModesForImport());
        String authPMExp = String.join(",", dto.getAuthorizedPaymentModesForExport());
        accountDetailRepository.updateAuthPMByIBAN(dto.getIban(), authPMImport, authPMExp);
        try {
            pswResponse = pswapiConsumerService.updateAccountAndPMInPWS(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return pswResponse;
    }

    @Transactional
    public ResponseUtility.APIResponse updateTraderProfileStatus(TraderProfileStatusDTO dto) {
        ResponseUtility.APIResponse pswResponse = null;

        accountDetailRepository.updateStatusByIBAN(dto.getIban(), dto.getAccountStatus());
        try {
            pswResponse = pswapiConsumerService.updateTraderProfileAccountStatus(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return pswResponse;
    }

    public ResponseUtility.APIResponse shareNegativeListOfCountries(BankNegativeCountriesDTO dto) {
        ResponseUtility.APIResponse pswResponse = null;

        try {
            pswResponse = pswapiConsumerService.shareNegativeListOfCountries(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
       return pswResponse;
    }

    public ResponseUtility.APIResponse shareNegativeListOfCommodities(BankNegativeCommoditiesDTO dto) {
        ResponseUtility.APIResponse pswResponse = null;

        try {
            pswResponse = pswapiConsumerService.shareNegativeListOfCommodities(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return  pswResponse;
    }

    public ResponseUtility.APIResponse shareNegativeListOfSuppliers(BankNegativeSuppliersDTO dto) {
        ResponseUtility.APIResponse pswResponse = null;

        try {
            pswResponse = pswapiConsumerService.shareNegativeListOfSuppliers(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return pswResponse;
    }
}
