package com.infotech.adb.service;

import com.infotech.adb.dto.IBANVerificationRequest;
import com.infotech.adb.model.entity.AccountDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
//@Transactional(value = "masterTransactionManager", rollbackFor = {Throwable.class})
public class ADService {

    @Autowired
    private AccountService accountService;

    public Integer verifyAccount(IBANVerificationRequest requestParameter) {
        Optional<AccountDetail> accountDetail = getAccountDetailByIbanEmailAndMobile(requestParameter);

        return accountDetail.isPresent() ? 207 : 208;
    }

    private Optional<AccountDetail> getAccountDetailByIbanEmailAndMobile(IBANVerificationRequest requestParameter) {
        Optional<AccountDetail> accountDetail = accountService.getAccountByIbanAndEmailAndMobileNumber(requestParameter.getIban(),
                requestParameter.getEmail(), requestParameter.getMobileNumber());
        return accountDetail;
    }

    public AccountDetail getAccountByIban(IBANVerificationRequest requestBody) {
        return accountService.getAccountByIban(requestBody.getIban());
    }
}
