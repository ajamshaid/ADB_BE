package com.infotech.adb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class BankNegativeCountriesDTO {
    private String bankCode;
    private Set<String> restrictedCountriesForImport;
    private Set<String> restrictedCountriesForExport;
}