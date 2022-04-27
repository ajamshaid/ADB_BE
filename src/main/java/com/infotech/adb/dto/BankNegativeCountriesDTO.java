package com.infotech.adb.dto;

import com.infotech.adb.model.entity.BankNegativeList;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class BankNegativeCountriesDTO {
    private Long id;
    private String bankCode;
    private Set<String> restrictedCountriesForImport;
    private Set<String> restrictedCountriesForExport;

    public BankNegativeCountriesDTO(BankNegativeList entity) {
        convertToDTO(entity, true);
    }

    public BankNegativeList convertToEntity() {
        BankNegativeList entity = new BankNegativeList();

        entity.setBankCode(this.getBankCode());
        entity.setId(this.getId());
//        entity.setRestrictedCountriesForImport(this.getRestrictedCountriesForImport());
//        entity.setRestrictedCountriesForExport(this.getRestrictedCountriesForExport());

        return entity;
    }

    private void convertToDTO(BankNegativeList entity, boolean partialFill) {
        if (entity != null) {
            this.setBankCode(entity.getBankCode());
            this.setId(entity.getId());
//            this.setRestrictedCountriesForImport(entity.getRestrictedCountriesForImport());
//            this.setRestrictedCountriesForExport(entity.getRestrictedCountriesForExport());
        }
    }

    public BankNegativeCountriesDTO convertToNewDTO(BankNegativeList entity, boolean partialFill) {
        BankNegativeCountriesDTO dto = new BankNegativeCountriesDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }
}

