package com.infotech.adb.service;

import com.infotech.adb.dto.IBANVerificationRequest;
import com.infotech.adb.dto.RequestParameter;
import com.infotech.adb.model.entity.AccountDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
//@Transactional(value = "masterTransactionManager", rollbackFor = {Throwable.class})
public class ADService {

    @Autowired
    private AccountService accountService;

    public Integer verifyAccount(RequestParameter<IBANVerificationRequest> requestParameter) {
        Optional<AccountDetail> accountDetail = getAccountDetailByIbanEmailAndMobile(requestParameter);

        return accountDetail.isPresent() ? 207 : 208;
    }

    private Optional<AccountDetail> getAccountDetailByIbanEmailAndMobile(RequestParameter<IBANVerificationRequest> requestParameter) {
        Optional<AccountDetail> accountDetail = accountService.getAccountByIbanAndEmailAndMobileNumber(requestParameter.getData().getIban(),
                requestParameter.getData().getEmail(), requestParameter.getData().getMobileNumber());
        return accountDetail;
    }

    public AccountDetail getAccountByIban(RequestParameter<IBANVerificationRequest> requestBody) {
        return accountService.getAccountByIban(requestBody.getData().getIban());
    }
}
