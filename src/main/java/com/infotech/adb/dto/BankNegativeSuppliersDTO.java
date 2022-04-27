package com.infotech.adb.dto;

import com.infotech.adb.model.entity.BankNegativeList;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class BankNegativeSuppliersDTO {
    private Long id;
    private String bankCode;
    private Set<String> restrictedSuppliersForImport;
    private Set<String> restrictedSuppliersForExport;

    public BankNegativeSuppliersDTO(BankNegativeList entity) {
        convertToDTO(entity, true);
    }

    public BankNegativeList convertToEntity() {
        BankNegativeList entity = new BankNegativeList();

        entity.setBankCode(this.getBankCode());
        entity.setId(this.getId());

        return entity;
    }

    private void convertToDTO(BankNegativeList entity, boolean partialFill) {
        if (entity != null) {
            this.setBankCode(entity.getBankCode());
            this.setId(entity.getId());
        }
    }

    public BankNegativeSuppliersDTO convertToNewDTO(BankNegativeList entity, boolean partialFill) {
        BankNegativeSuppliersDTO dto = new BankNegativeSuppliersDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }
}