package com.infotech.adb.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.infotech.adb.model.entity.*;
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
        this.setRestrictedCountriesForExport(getCountryCodes(entity.getRCountryExport()));
        this.setRestrictedCountriesForImport(getCountryCodes(entity.getRCountryImport()));
    }

    @Override
    public RestrictedCountiesDTO convertToNewDTO(AccountDetail accountDetail, boolean partialFill) {
        RestrictedCountiesDTO accountDetailDTO = new RestrictedCountiesDTO();
        accountDetailDTO.convertToDTO(accountDetail, partialFill);
        return accountDetailDTO;
    }

    private Set<String> getCountryCodes(Set<Country> reCountry) {
        Set<String> countryCodes = new HashSet<>();
        for (Country country : reCountry) {
            countryCodes.add(country.getCode());
        }
        return countryCodes;
    }
}