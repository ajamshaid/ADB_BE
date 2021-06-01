package com.infotech.adb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class BankNegativeCommoditiesDTO {
    private String bankCode;
    private Set<String> restrictedCommoditiesForImport;
    private Set<String> restrictedCommoditiesForExport;
}