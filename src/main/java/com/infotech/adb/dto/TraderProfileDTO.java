package com.infotech.adb.dto;

import com.infotech.adb.model.entity.AccountDetail;
import lombok.Data;

@Data
public class TraderProfileDTO implements BaseDTO<TraderProfileDTO, AccountDetail> {

    private String ntn;
    private String traderName;
    private String iban;
    private String email;
    private String mobileNumber;

    public TraderProfileDTO() {
    }

    public TraderProfileDTO(AccountDetail accountDetail) {
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
            this.setEmail(entity.getEmail());
            this.setMobileNumber(entity.getMobileNumber());
            this.setNtn(entity.getNtn());
            this.setTraderName(entity.getAccountTitle());
        }
    }


    @Override
    public TraderProfileDTO convertToNewDTO(AccountDetail accountDetail, boolean partialFill) {
        TraderProfileDTO accountDetailDTO = new TraderProfileDTO();
        accountDetailDTO.convertToDTO(accountDetail, partialFill);
        return accountDetailDTO;
    }
}