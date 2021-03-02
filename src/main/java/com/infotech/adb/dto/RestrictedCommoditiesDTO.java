package com.infotech.adb.dto;

import com.infotech.adb.model.entity.*;
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

    @Override
    public AccountDetail convertToEntity() {
        return null;
    }

    @Override
    public void convertToDTO(AccountDetail entity, boolean partialFill) {
        this.setIban(entity.getIban());
        this.setRestrictedCommoditiesForExport(getCommodityCodes(entity.getRCommodityExport()));
        this.setRestrictedCommoditiesForImport(getCommodityCodes(entity.getRCommodityImport()));
    }

    @Override
    public RestrictedCommoditiesDTO convertToNewDTO(AccountDetail accountDetail, boolean partialFill) {
        RestrictedCommoditiesDTO accountDetailDTO = new RestrictedCommoditiesDTO();
        accountDetailDTO.convertToDTO(accountDetail, partialFill);
        return accountDetailDTO;
    }

    private Set<String> getCommodityCodes(Set<Commodity> commodities) {
        Set<String> commoditiesCodes = new HashSet<>();
        for (Commodity commodity : commodities) {
            commoditiesCodes.add(commodity.getCode());
        }
        return commoditiesCodes;
    }
}