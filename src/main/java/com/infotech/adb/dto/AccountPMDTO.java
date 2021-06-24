package com.infotech.adb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
//@JsonFilter("assetModelFilter")
public class AccountPMDTO {

    private String iban;
    private Set<String> authorizedPaymentModesForImport;
    private Set<String> authorizedPaymentModesForExports;
}