package com.infotech.adb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.api.consumer.PSWAPIConsumer;
import com.infotech.adb.dto.*;
import com.infotech.adb.model.repository.AccountDetailRepository;
import com.infotech.adb.util.AppConstants;
import com.infotech.adb.util.AppUtility;
import com.infotech.adb.util.ResponseUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class PSWService {

    @Autowired
    PSWAPIConsumer consumer;

    @Autowired
    AccountDetailRepository accountDetailRepository;

    @Transactional
    public ResponseUtility.APIResponse shareUpdatedAuthPMs(AccountPMDTO dto) {
        ResponseUtility.APIResponse pswResponse = null;

        String authPMImport = String.join("|", dto.getAuthorizedPaymentModesForImport());
        String authPMExp = String.join("|", dto.getAuthorizedPaymentModesForExport());
        accountDetailRepository.updateAuthPMByIBAN(dto.getIban(), authPMImport, authPMExp);
        try {
            pswResponse = consumer.updateAccountAndPMInPWS(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (AppUtility.isEmpty(pswResponse)) {
            pswResponse = new ResponseUtility.APIResponse();
            pswResponse.setMessage(ResponseUtility.Message.getDBUpdateButPSWRequestFaildMsg());
        }
        return pswResponse;
    }

    @Transactional
    public ResponseUtility.APIResponse updateTraderProfileStatus(TraderProfileStatusDTO dto) {
        ResponseUtility.APIResponse pswResponse = null;

        accountDetailRepository.updateStatusByIBAN(dto.getIban(), dto.getAccountStatus());
        try {
            pswResponse = consumer.updateTraderProfileAccountStatus(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (AppUtility.isEmpty(pswResponse)) {
            pswResponse = new ResponseUtility.APIResponse();
            pswResponse.setMessage(ResponseUtility.Message.getDBUpdateButPSWRequestFaildMsg());
        }

        return pswResponse;
    }

    public ResponseUtility.APIResponse shareNegativeListOfCountries(BankNegativeCountriesDTO dto) {
        ResponseUtility.APIResponse pswResponse = null;

        try {
            pswResponse = consumer.shareNegativeListOfCountries(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (!AppUtility.isEmpty(pswResponse)) {
            if (AppConstants.PSWResponseCodes.OK.equals(pswResponse.getMessage().getCode())) {
                //@TODO // update in DB
            }
        }
        return pswResponse;
    }

    public ResponseUtility.APIResponse shareNegativeListOfCommodities(BankNegativeCommoditiesDTO dto) {
        ResponseUtility.APIResponse pswResponse = null;

        try {
            pswResponse = consumer.shareNegativeListOfCommodities(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (!AppUtility.isEmpty(pswResponse)) {
            if (AppConstants.PSWResponseCodes.OK.equals(pswResponse.getMessage().getCode())) {
                //@TODO // update in DB
            }
        }
        return pswResponse;
    }

    public ResponseUtility.APIResponse shareNegativeListOfSuppliers(BankNegativeSuppliersDTO dto) {
        ResponseUtility.APIResponse pswResponse = null;

        try {
            pswResponse = consumer.shareNegativeListOfSuppliers(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (!AppUtility.isEmpty(pswResponse)) {
            if (AppConstants.PSWResponseCodes.OK.equals(pswResponse.getMessage().getCode())) {
                //@TODO // update in DB
            }
        }
        return pswResponse;
    }


}
