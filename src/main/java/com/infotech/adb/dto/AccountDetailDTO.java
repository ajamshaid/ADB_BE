package com.infotech.adb.dto;

import com.infotech.adb.model.entity.AccountDetail;
import com.infotech.adb.model.entity.AuthorizedPaymentModes;
import com.infotech.adb.util.AppConstants;
import com.infotech.adb.util.AppUtility;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
//@JsonFilter("assetModelFilter")
public class AccountDetailDTO implements BaseDTO<AccountDetailDTO, AccountDetail> {

    private String iban;
    private String accountStatus;
//    private Set<String> authorizedPaymentModesForImport;
//    private Set<String> authorizedPaymentModesForExports;

    private String AuthPMImport;
    private String AuthPMExport;

    public AccountDetailDTO() {
    }

    public AccountDetailDTO(AccountDetail accountDetail) {
     //   accountDetail.getAuthorizedPaymentModesSet();
        convertToDTO(accountDetail,true);
    }
    @Override
    public AccountDetail convertToEntity() {
        AccountDetail accountDetail = new AccountDetail();
        accountDetail.setIban(this.iban);
        accountDetail.setAccountStatus(this.accountStatus);
        accountDetail.setAuthPMImport(this.getAuthPMImport());
        accountDetail.setAuthPMExport(this.getAuthPMExport());
//        accountDetail.setEmail(this.email);
//        accountDetail.setMobileNumber(this.mobileNumber);

        return accountDetail;
    }

    @Override
    public void convertToDTO(AccountDetail entity, boolean partialFill) {

        if(entity != null) {
            this.setIban(entity.getIban());
            this.setAccountStatus(entity.getAccountStatus());
            this.setAuthPMImport(entity.getAuthPMImport());
            this.setAuthPMExport(entity.getAuthPMExport());

          //  fillPaymentModes(entity.getAuthorizedPaymentModesSet());
        }
    }

    private void fillPaymentModes(Set<AuthorizedPaymentModes> authorizedPaymentModes) {
        Set<String> apmExports = new HashSet<>();
        Set<String> apmImports = new HashSet<>();
        if(!AppUtility.isEmpty(authorizedPaymentModes)) {
            for (AuthorizedPaymentModes apm : authorizedPaymentModes) {
                if (AppConstants.PAYMENT_MODE.TYPE_IMPORT.equals(apm.getType())) {
                    apmImports.add(apm.getCode());
                } else if (AppConstants.PAYMENT_MODE.TYPE_EXPORT.equals(apm.getType())) {
                    apmExports.add(apm.getCode());
                }
            }
        }
      //  this.setAuthorizedPaymentModesForExports(apmExports);
    //    this.setAuthorizedPaymentModesForImport(apmImports);
    }

    @Override
    public AccountDetailDTO convertToNewDTO(AccountDetail accountDetail, boolean partialFill) {
        AccountDetailDTO accountDetailDTO = new AccountDetailDTO();
        accountDetailDTO.convertToDTO(accountDetail, partialFill);
        return accountDetailDTO;
    }
}