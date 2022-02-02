package com.infotech.adb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
//@JsonFilter("assetModelFilter")
public class AccountPMDTO {

    private String iban;
    private Set<String> authorizedPaymentModesForImport;
    private Set<String> authorizedPaymentModesForExport;

    public Set<String> getAuthorizedPaymentModesForImport() {
        if(this.authorizedPaymentModesForImport == null){
            authorizedPaymentModesForImport = new HashSet<>();
        }
        return authorizedPaymentModesForImport;
    }

    public void setAuthorizedPaymentModesForImport(Set<String> authorizedPaymentModesForImport) {
        this.authorizedPaymentModesForImport = authorizedPaymentModesForImport;
    }

    public Set<String> getAuthorizedPaymentModesForExport() {
        if(this.authorizedPaymentModesForExport == null){
            authorizedPaymentModesForExport = new HashSet<>();
        }
        return authorizedPaymentModesForExport;
    }

    public void setAuthorizedPaymentModesForExport(Set<String> authorizedPaymentModesForExport) {
        this.authorizedPaymentModesForExport = authorizedPaymentModesForExport;
    }
}