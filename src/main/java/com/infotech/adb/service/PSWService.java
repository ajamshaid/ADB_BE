package com.infotech.adb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.api.consumer.PSWAPIConsumer;
import com.infotech.adb.dto.AccountDetailDTO;
import com.infotech.adb.util.AppConstants;
import com.infotech.adb.util.AppUtility;
import com.infotech.adb.util.ResponseUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PSWService {

    @Autowired
    PSWAPIConsumer consumer;

    public ResponseUtility.APIResponse shareUpdatedAuthPMs(AccountDetailDTO dto) {
        ResponseUtility.APIResponse pswResponse = null;

        try {
            pswResponse  =  consumer.updateAccountAndPMInPWS(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if(!AppUtility.isEmpty(pswResponse)){
            if(AppConstants.PSWResponseCodes.OK .equals(pswResponse.getMessage().getCode())){
                //@TODO // update in DB
            }
        }
        return pswResponse;
    }
}