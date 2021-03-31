package com.infotech.adb.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.infotech.adb.model.entity.*;
import com.infotech.adb.util.AppConstants;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class RestrictedCountiesDTO implements BaseDTO<RestrictedCountiesDTO, AccountDetail> {

    private String iban;

    private Set<String> restrictedCountriesForImport;
    private Set<String> restrictedCountriesForExport;


    public RestrictedCountiesDTO() {
    }

    @Override
    public AccountDetail convertToEntity() {
        return null;
    }

    @Override
    public void convertToDTO(AccountDetail entity, boolean partialFill) {
        this.setIban(entity.getIban());
        this.fillRestrictedCountriesList(entity.getRestrictedCoutriesSet());
    }

    @Override
    public RestrictedCountiesDTO convertToNewDTO(AccountDetail accountDetail, boolean partialFill) {
        RestrictedCountiesDTO accountDetailDTO = new RestrictedCountiesDTO();
        accountDetailDTO.convertToDTO(accountDetail, partialFill);
        return accountDetailDTO;
    }


    private void fillRestrictedCountriesList(Set<RestrictedCoutries> restrictedCoutriesSet) {
        Set<String> exports = new HashSet<>();
        Set<String> imports = new HashSet<>();
        for (RestrictedCoutries reCountry : restrictedCoutriesSet) {
            if(AppConstants.PAYMENT_MODE_TYPE_IMPORT.equals(reCountry.getType())){
                imports.add(reCountry.getCountry().getCode());
            }else if(AppConstants.PAYMENT_MODE_TYPE_EXPORT.equals(reCountry.getType())){
                exports.add(reCountry.getCountry().getCode());
            }
        }
        this.setRestrictedCountriesForImport(imports);
        this.setRestrictedCountriesForExport(exports);
    }
}