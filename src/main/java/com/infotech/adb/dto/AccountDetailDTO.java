package com.infotech.adb.dto;

import com.infotech.adb.model.entity.AccountDetail;
import com.infotech.adb.model.entity.AuthorizedPaymentModes;
import com.infotech.adb.util.AppConstants;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
//@JsonFilter("assetModelFilter")
public class AccountDetailDTO implements BaseDTO<AccountDetailDTO, AccountDetail> {

    private String iban;
//    private String email;
//    private String mobileNumber;
    private String accountTitle;
    private String accountNumber;
    private String accountStatus;
    private String accountType;
    private String ntn;
    private String cnic;

    private Set<String> authorizedPaymentModesForImport;
    private Set<String> authorizedPaymentModesForExports;

    public AccountDetailDTO() {
    }

    @Override
    public AccountDetail convertToEntity() {
        AccountDetail accountDetail = new AccountDetail();
        accountDetail.setIban(this.iban);
//        accountDetail.setEmail(this.email);
//        accountDetail.setMobileNumber(this.mobileNumber);
        accountDetail.setAccountTitle(this.accountTitle);
        accountDetail.setAccountNumber(this.accountNumber);
        accountDetail.setAccountStatus(this.accountStatus);
        accountDetail.setAccountType(this.accountType);
        accountDetail.setCnic(this.cnic);
        accountDetail.setNtn(this.ntn);
        return accountDetail;
    }

    @Override
    public void convertToDTO(AccountDetail entity, boolean partialFill) {

        if(entity != null) {
            this.setIban(entity.getIban());
//            this.setEmail(entity.getEmail());
//            this.setMobileNumber(entity.getMobileNumber());
            this.setAccountTitle(entity.getAccountTitle());
            this.setAccountNumber(entity.getAccountNumber());
            this.setAccountStatus(entity.getAccountStatus());
            this.setAccountType(entity.getAccountType());
            this.setCnic(entity.getCnic());
            this.setNtn(entity.getNtn());
            fillPaymentModes(entity.getAuthorizedPaymentModesSet());
        }
    }

    private void fillPaymentModes(Set<AuthorizedPaymentModes> authorizedPaymentModes) {
        Set<String> apmExports = new HashSet<>();
        Set<String> apmImports = new HashSet<>();
        for (AuthorizedPaymentModes apm: authorizedPaymentModes) {
            if(AppConstants.PAYMENT_MODE_TYPE_IMPORT.equals(apm.getType())){
                apmImports.add(apm.getCode());
            }else if(AppConstants.PAYMENT_MODE_TYPE_EXPORT.equals(apm.getType())){
                apmExports.add(apm.getCode());
            }
        }
        this.setAuthorizedPaymentModesForExports(apmExports);
        this.setAuthorizedPaymentModesForImport(apmImports);
    }

    @Override
    public AccountDetailDTO convertToNewDTO(AccountDetail accountDetail, boolean partialFill) {
        AccountDetailDTO accountDetailDTO = new AccountDetailDTO();
        accountDetailDTO.convertToDTO(accountDetail, partialFill);
        return accountDetailDTO;
    }
}