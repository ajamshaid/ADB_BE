package com.infotech.adb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class BankNegativeSuppliersDTO {
    private String bankCode;
    private Set<String> restrictedSuppliersForImport;
    private Set<String> restrictedSuppliersForExport;
}