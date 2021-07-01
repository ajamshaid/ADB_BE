package com.infotech.adb.dto;

import com.infotech.adb.model.entity.ChangeOfBank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangeBankRequestDTO implements BaseDTO<ChangeBankRequestDTO , ChangeOfBank> {

    private Long id;
    private String cobUniqueIdNumber;
    private String tradeTranType;
    private String traderNTN;
    private String traderName;
    private String Iban;
    private String finInsUniqueNumber;
    private String gdNumber;
    private String cobStatus;

    public ChangeBankRequestDTO(String id ,String cobUniqueIdNumber, String tradeType, String traderNTN, String traderName, String iban, String finInsUniqueNumber, String gdNumber) {
        this.cobUniqueIdNumber = cobUniqueIdNumber;
        this.tradeTranType = tradeType;
        this.traderNTN = traderNTN;
        this.traderName = traderName;
        Iban = iban;
        this.finInsUniqueNumber = finInsUniqueNumber;
        this.gdNumber = gdNumber;
    }


    @Override
    public ChangeOfBank convertToEntity() {
        ChangeOfBank entity = new ChangeOfBank();

        entity.setId(this.getId());
        entity.setCobUniqueIdNumber(this.getCobUniqueIdNumber());
        entity.setTradeType(this.getTradeTranType());
        entity.setTraderNTN(this.getTraderNTN());
        entity.setTraderName(this.getTraderName());
        entity.setIban(this.getIban());
        entity.setFinInsUniqueNumber(this.getFinInsUniqueNumber());
        entity.setCobStatus(this.getCobStatus());

        return entity;
    }

    @Override
    public void convertToDTO(ChangeOfBank entity, boolean partialFill) {
        if(entity != null){

            this.setId(entity.getId());
            this.setCobUniqueIdNumber(entity.getCobUniqueIdNumber());
            this.setIban(entity.getIban());
            this.setFinInsUniqueNumber(entity.getFinInsUniqueNumber());
            this.setTradeTranType(entity.getTradeType());
            this.setTraderName(entity.getTraderName());
            this.setTraderNTN(entity.getTraderNTN());
            this.setCobStatus(entity.getCobStatus());

        }
    }

    @Override
    public ChangeBankRequestDTO convertToNewDTO(ChangeOfBank entity, boolean partialFill) {
        ChangeBankRequestDTO dto = new ChangeBankRequestDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }
}
