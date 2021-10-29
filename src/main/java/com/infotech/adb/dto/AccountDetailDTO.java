package com.infotech.adb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
//@JsonFilter("assetModelFilter")
public class AccountDetailDTO {

    private String iban;
    private String accountTitle;
    private String accountNumber;
    private String accountStatus;
    private String ntn;
    private String cnic;

    private Set<String> authorizedPaymentModesForImport;
    private Set<String> authorizedPaymentModesForExport;
}