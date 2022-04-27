package com.infotech.adb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.dto.*;
import com.infotech.adb.model.entity.BankNegativeList;
import com.infotech.adb.model.repository.AccountDetailRepository;
import com.infotech.adb.model.repository.BankNegtiveListRepository;
import com.infotech.adb.psw.consumer.PSWAPIConsumerService;
import com.infotech.adb.util.AppConstants;
import com.infotech.adb.util.AppUtility;
import com.infotech.adb.util.ResponseUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class PSWService {

    @Autowired
    PSWAPIConsumerService pswapiConsumerService;

    @Autowired
    AccountDetailRepository accountDetailRepository;

    @Autowired
    private BankNegtiveListRepository bankNegtiveListRepository;

    @Transactional
    public ResponseUtility.APIResponse shareUpdatedAuthPMs(AccountPMDTO dto) {
        ResponseUtility.APIResponse pswResponse = null;

        String authPMImport = AppUtility.isEmpty(dto.getAuthorizedPaymentModesForImport()) ? "" : String.join(",", dto.getAuthorizedPaymentModesForImport());
        String authPMExp = AppUtility.isEmpty(dto.getAuthorizedPaymentModesForExport()) ? "" : String.join(",", dto.getAuthorizedPaymentModesForExport());
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

    @Transactional
    public ResponseUtility.APIResponse shareNegativeListOfCountries(BankNegativeCountriesDTO dto) {
        ResponseUtility.APIResponse pswResponse = null;

        try {
            pswResponse = pswapiConsumerService.shareNegativeListOfCountries(dto);
            String respCode = pswResponse.getMessage().getCode();
            if (respCode.equals("" + HttpStatus.OK.value())) {
                String impStr = String.join(",", dto.getRestrictedCountriesForImport());
                String expStr = String.join(",", dto.getRestrictedCountriesForExport());

                bankNegtiveListRepository.updateNegativeBankListCountries(expStr, impStr, dto.getId());
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return pswResponse;
    }

    @Transactional
    public ResponseUtility.APIResponse shareNegativeListOfCommodities(BankNegativeCommoditiesDTO dto) {
        ResponseUtility.APIResponse pswResponse = null;

        try {
            pswResponse = pswapiConsumerService.shareNegativeListOfCommodities(dto);
            String respCode = pswResponse.getMessage().getCode();
            if (respCode.equals("" + HttpStatus.OK.value())) {
                String impStr = String.join(",", dto.getRestrictedCommoditiesForImport());
                String expStr = String.join(",", dto.getRestrictedCommoditiesForExport());

                bankNegtiveListRepository.updateNegativeBankListCommodities(expStr, impStr, dto.getId());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return pswResponse;
    }

    @Transactional
    public ResponseUtility.APIResponse shareNegativeListOfSuppliers(BankNegativeSuppliersDTO dto) {
        ResponseUtility.APIResponse pswResponse = null;

        try {
            pswResponse = pswapiConsumerService.shareNegativeListOfSuppliers(dto);
            String respCode = pswResponse.getMessage().getCode();
            if (respCode.equals("" + HttpStatus.OK.value())) {
                String impStr = String.join(",", dto.getRestrictedSuppliersForImport());
                String expStr = String.join(",", dto.getRestrictedSuppliersForExport());

                bankNegtiveListRepository.updateNegativeBankListSuppliers(expStr, impStr, dto.getId());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return pswResponse;
    }
}
