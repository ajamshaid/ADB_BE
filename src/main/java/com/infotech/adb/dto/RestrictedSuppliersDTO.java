package com.infotech.adb.dto;

import com.infotech.adb.model.entity.AccountDetail;
import com.infotech.adb.model.entity.RestrictedSuppliers;
import com.infotech.adb.util.AppConstants;
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

    public RestrictedSuppliersDTO(AccountDetail accountDetail) {
        accountDetail.getRestrictedSuppliersSet();
        convertToDTO(accountDetail,true);
    }
    @Override
    public AccountDetail convertToEntity() {
        return null;
    }

    @Override
    public void convertToDTO(AccountDetail entity, boolean partialFill) {

        this.setIban(entity.getIban());
        fillRestrictedList(entity.getRestrictedSuppliersSet());
    }

    @Override
    public RestrictedSuppliersDTO convertToNewDTO(AccountDetail accountDetail, boolean partialFill) {
        RestrictedSuppliersDTO accountDetailDTO = new RestrictedSuppliersDTO();
        accountDetailDTO.convertToDTO(accountDetail, partialFill);
        return accountDetailDTO;
    }

    private void fillRestrictedList(Set<RestrictedSuppliers> negativeSet) {
        Set<String> exports = new HashSet<>();
        Set<String> imports = new HashSet<>();
        for (RestrictedSuppliers neg : negativeSet) {
            if(AppConstants.PAYMENT_MODE_TYPE_IMPORT.equals(neg.getType())){
                imports.add(neg.getNAME());
            }else if(AppConstants.PAYMENT_MODE_TYPE_EXPORT.equals(neg.getType())){
                exports.add(neg.getNAME());
            }
        }
        this.setRestrictedSuppliersForImport(imports);
        this.setRestrictedSuppliersForExport(exports);
    }
}
