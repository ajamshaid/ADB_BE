package com.infotech.adb.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.infotech.adb.model.entity.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class RestrictedSuppliersDTO implements BaseDTO<RestrictedSuppliersDTO, AccountDetail> {

    private String iban;
    private Set<String> restrictedSuppliersForImport;
    private Set<String> restrictedSuppliersForExport;

    public RestrictedSuppliersDTO() {
    }

    @Override
    public AccountDetail convertToEntity() {
        return null;
    }

    @Override
    public void convertToDTO(AccountDetail entity, boolean partialFill) {

        this.setIban(entity.getIban());
        this.setRestrictedSuppliersForExport(getCountryCodes(entity.getRSupplierExport()));
        this.setRestrictedSuppliersForImport(getCountryCodes(entity.getRSupplierImport()));
    }

    @Override
    public RestrictedSuppliersDTO convertToNewDTO(AccountDetail accountDetail, boolean partialFill) {
        RestrictedSuppliersDTO accountDetailDTO = new RestrictedSuppliersDTO();
        accountDetailDTO.convertToDTO(accountDetail, partialFill);
        return accountDetailDTO;
    }

    private Set<String> getCountryCodes(Set<Supplier> suppliers) {
        Set<String> supplierCodes = new HashSet<>();
        for (Supplier supplier : suppliers) {
            supplierCodes.add(supplier.getCode());
        }
        return supplierCodes;
    }
}
