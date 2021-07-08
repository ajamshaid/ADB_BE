package com.infotech.adb.dto;

import com.infotech.adb.model.entity.ReversalOfBdaBca;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReversalOfBdaBcaDTO implements BaseDTO<ReversalOfBdaBcaDTO, ReversalOfBdaBca> {

    private Long id;
    private String bcaBdaUniqueIdNumber;
    private String tradeTranType;
    private String traderNTN;
    private String traderName;
    private String Iban;
    private String finInsUniqueNumber;

    public ReversalOfBdaBcaDTO(String id , String cobUniqueIdNumber, String tradeType, String traderNTN, String traderName, String iban, String finInsUniqueNumber, String gdNumber) {
        this.tradeTranType = tradeType;
        this.traderNTN = traderNTN;
        this.traderName = traderName;
        Iban = iban;
        this.finInsUniqueNumber = finInsUniqueNumber;

    }


    @Override
    public ReversalOfBdaBca convertToEntity() {
        ReversalOfBdaBca entity = new ReversalOfBdaBca();

        entity.setId(this.getId());
        entity.setBcaBdaUniqueIdNumber(this.getBcaBdaUniqueIdNumber());
        entity.setTradeType(this.getTradeTranType());
        entity.setTraderNTN(this.getTraderNTN());
        entity.setTraderName(this.getTraderName());
        entity.setIban(this.getIban());
        entity.setFinInsUniqueNumber(this.getFinInsUniqueNumber());

        return entity;
    }

    @Override
    public void convertToDTO(ReversalOfBdaBca entity, boolean partialFill) {
        if(entity != null){

            this.setId(entity.getId());
            this.setBcaBdaUniqueIdNumber(entity.getBcaBdaUniqueIdNumber());
            this.setIban(entity.getIban());
            this.setFinInsUniqueNumber(entity.getFinInsUniqueNumber());
            this.setTradeTranType(entity.getTradeType());
            this.setTraderName(entity.getTraderName());
            this.setTraderNTN(entity.getTraderNTN());

        }
    }

    @Override
    public ReversalOfBdaBcaDTO convertToNewDTO(ReversalOfBdaBca entity, boolean partialFill) {
        ReversalOfBdaBcaDTO dto = new ReversalOfBdaBcaDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }
}
