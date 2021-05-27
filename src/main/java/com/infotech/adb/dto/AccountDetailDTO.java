package com.infotech.adb.dto;

import com.infotech.adb.model.entity.AuthorizedPaymentModes;
import com.infotech.adb.util.AppConstants;
import com.infotech.adb.util.AppUtility;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
//@JsonFilter("assetModelFilter")
public class AccountDetailDTO {

    private String iban;
//    private String email;
//    private String mobileNumber;
    private String accountTitle;
    private String accountNumber;
    private String accountStatus;
//    private String accountType;
    private String ntn;
    private String cnic;

    private Set<String> authorizedPaymentModesForImport;
    private Set<String> authorizedPaymentModesForExports;

    public AccountDetailDTO() {
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
        this.setAuthorizedPaymentModesForExports(apmExports);
        this.setAuthorizedPaymentModesForImport(apmImports);
    }
}