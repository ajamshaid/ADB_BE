package com.infotech.adb.dto;

import com.infotech.adb.model.entity.AccountDetail;
import com.infotech.adb.model.entity.RestrictedCountries;
import com.infotech.adb.util.AppConstants;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class RestrictedCountriesDTO implements BaseDTO<RestrictedCountriesDTO, AccountDetail> {

    private String iban;

    private Set<String> restrictedCountriesForImport;
    private Set<String> restrictedCountriesForExport;


    public RestrictedCountriesDTO() {
    }

    public RestrictedCountriesDTO(AccountDetail accountDetail) {
        accountDetail.getRestrictedCountriesSet();
        convertToDTO(accountDetail,true);
    }
    @Override
    public AccountDetail convertToEntity() {
        return null;
    }

    @Override
    public void convertToDTO(AccountDetail entity, boolean partialFill) {
        this.setIban(entity.getIban());
        this.fillRestrictedCountriesList(entity.getRestrictedCountriesSet());
    }

    @Override
    public RestrictedCountriesDTO convertToNewDTO(AccountDetail accountDetail, boolean partialFill) {
        RestrictedCountriesDTO accountDetailDTO = new RestrictedCountriesDTO();
        accountDetailDTO.convertToDTO(accountDetail, partialFill);
        return accountDetailDTO;
    }


    private void fillRestrictedCountriesList(Set<RestrictedCountries> restrictedCoutriesSet) {
        Set<String> exports = new HashSet<>();
        Set<String> imports = new HashSet<>();
        for (RestrictedCountries reCountry : restrictedCoutriesSet) {
            if(AppConstants.PAYMENT_MODE.TYPE_IMPORT.equals(reCountry.getType())){
                imports.add(reCountry.getCountry().getCode());
            }else if(AppConstants.PAYMENT_MODE.TYPE_EXPORT.equals(reCountry.getType())){
                exports.add(reCountry.getCountry().getCode());
            }
        }
        this.setRestrictedCountriesForImport(imports);
        this.setRestrictedCountriesForExport(exports);
    }
}