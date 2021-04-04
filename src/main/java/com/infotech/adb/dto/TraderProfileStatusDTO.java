package com.infotech.adb.dto;

import com.infotech.adb.model.entity.AccountDetail;
import lombok.Data;

@Data
public class TraderProfileStatusDTO implements BaseDTO<TraderProfileStatusDTO, AccountDetail> {

    private String iban;
    private String accountStatus;

    public TraderProfileStatusDTO() {
    }

    public TraderProfileStatusDTO(AccountDetail accountDetail) {
        convertToDTO(accountDetail,true);
    }
    @Override
    public AccountDetail convertToEntity() {
        AccountDetail accountDetail = new AccountDetail();
        return accountDetail;
    }

    @Override
    public void convertToDTO(AccountDetail entity, boolean partialFill) {

        if(entity != null) {
            this.setIban(entity.getIban());
            this.setAccountStatus(entity.getAccountStatus());
        }
    }


    @Override
    public TraderProfileStatusDTO convertToNewDTO(AccountDetail accountDetail, boolean partialFill) {
        TraderProfileStatusDTO accountDetailDTO = new TraderProfileStatusDTO();
        accountDetailDTO.convertToDTO(accountDetail, partialFill);
        return accountDetailDTO;
    }
}