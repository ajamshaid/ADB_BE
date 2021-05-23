package com.infotech.adb.dto;

import com.infotech.adb.model.entity.AccountDetail;
import com.infotech.adb.model.entity.RestrictedCommodities;
import com.infotech.adb.util.AppConstants;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class RestrictedCommoditiesDTO implements BaseDTO<RestrictedCommoditiesDTO, AccountDetail> {

    private String iban;
    private Set<String> restrictedCommoditiesForImport;
    private Set<String> restrictedCommoditiesForExport;

    public RestrictedCommoditiesDTO() {
    }
    public RestrictedCommoditiesDTO(AccountDetail accountDetail) {
    //    accountDetail.getRestrictedCommoditiesSet();
        convertToDTO(accountDetail,true);
    }
    @Override
    public AccountDetail convertToEntity() {
        return null;
    }

    @Override
    public void convertToDTO(AccountDetail entity, boolean partialFill) {
        this.setIban(entity.getIban());
  //      fillRestrictedCommoditiesList(entity.getRestrictedCommoditiesSet());
    }

    @Override
    public RestrictedCommoditiesDTO convertToNewDTO(AccountDetail accountDetail, boolean partialFill) {
        RestrictedCommoditiesDTO accountDetailDTO = new RestrictedCommoditiesDTO();
        accountDetailDTO.convertToDTO(accountDetail, partialFill);
        return accountDetailDTO;
    }

    private void fillRestrictedCommoditiesList(Set<RestrictedCommodities> restrictedCommoditiesSet) {
        Set<String> exports = new HashSet<>();
        Set<String> imports = new HashSet<>();
        for (RestrictedCommodities com : restrictedCommoditiesSet) {
            if(AppConstants.PAYMENT_MODE.TYPE_IMPORT.equals(com.getType())){
                imports.add(com.getCode());
            }else if(AppConstants.PAYMENT_MODE.TYPE_EXPORT.equals(com.getType())){
                exports.add(com.getCode());
            }
        }
        this.setRestrictedCommoditiesForImport(imports);
        this.setRestrictedCommoditiesForExport(exports);
    }
}