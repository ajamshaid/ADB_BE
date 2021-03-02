package com.infotech.adb.dto;

import com.infotech.adb.model.entity.AccountDetail;
import com.infotech.adb.model.entity.AuthorizedPMExport;
import com.infotech.adb.model.entity.AuthorizedPMImport;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
//@JsonFilter("assetModelFilter")
public class AccountDetailDTO implements BaseDTO<AccountDetailDTO, AccountDetail> {

    private String iban;
    private String email;
    private String mobileNumber;
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
        accountDetail.setEmail(this.email);
        accountDetail.setMobileNumber(this.mobileNumber);
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

        this.setIban(entity.getIban());
        this.setEmail(entity.getEmail());
        this.setMobileNumber(entity.getMobileNumber());
        this.setAccountTitle(entity.getAccountTitle());
        this.setAccountNumber(entity.getAccountNumber());
        this.setAccountStatus(entity.getAccountStatus());
        this.setAccountType(entity.getAccountType());
        this.setCnic(entity.getCnic());
        this.setNtn(entity.getNtn());
        this.setAuthorizedPaymentModesForExports(getAPMExport(entity.getAuthorizedPMExports()));
        this.setAuthorizedPaymentModesForImport(getAPMImport(entity.getAuthorizedPMImport()));
    }

    private Set<String> getAPMExport(Set<AuthorizedPMExport> authorizedPMExports) {
        Set<String> apmExports = new HashSet<>();
        for (AuthorizedPMExport pmExport: authorizedPMExports) {
            apmExports.add(pmExport.getCode());
        }
        return apmExports;
    }

    private Set<String> getAPMImport(Set<AuthorizedPMImport> authorizedPMImports) {
        Set<String> apmImports = new HashSet<>();
        for (AuthorizedPMImport pmImport: authorizedPMImports) {
            apmImports.add(pmImport.getCode());
        }
        return apmImports;
    }

    @Override
    public AccountDetailDTO convertToNewDTO(AccountDetail accountDetail, boolean partialFill) {
        AccountDetailDTO accountDetailDTO = new AccountDetailDTO();
        accountDetailDTO.convertToDTO(accountDetail, partialFill);
        return accountDetailDTO;
    }
}