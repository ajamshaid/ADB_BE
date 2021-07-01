package com.infotech.adb.dto;

import com.infotech.adb.model.entity.CancellationOfFT;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CancellationOfFTDTO implements BaseDTO<CancellationOfFTDTO, CancellationOfFT> {

    private Long id;
    private String tradeTranType;
    private String traderNTN;
    private String traderName;
    private String Iban;
    private String finInsUniqueNumber;

    public CancellationOfFTDTO(String id ,  String tradeType, String traderNTN, String traderName, String iban, String finInsUniqueNumber) {
        this.tradeTranType = tradeType;
        this.traderNTN = traderNTN;
        this.traderName = traderName;
        Iban = iban;
        this.finInsUniqueNumber = finInsUniqueNumber;

    }


    @Override
    public CancellationOfFT convertToEntity() {
        CancellationOfFT entity = new CancellationOfFT();

        entity.setId(this.getId());
        entity.setTradeType(this.getTradeTranType());
        entity.setTraderNTN(this.getTraderNTN());
        entity.setTraderName(this.getTraderName());
        entity.setIban(this.getIban());
        entity.setFinInsUniqueNumber(this.getFinInsUniqueNumber());


        return entity;
    }

    @Override
    public void convertToDTO(CancellationOfFT entity, boolean partialFill) {
        if(entity != null){

            this.setId(entity.getId());
            this.setIban(entity.getIban());
            this.setFinInsUniqueNumber(entity.getFinInsUniqueNumber());
            this.setTradeTranType(entity.getTradeType());
            this.setTraderName(entity.getTraderName());
            this.setTraderNTN(entity.getTraderNTN());

        }
    }

    @Override
    public CancellationOfFTDTO convertToNewDTO(CancellationOfFT entity, boolean partialFill) {
        CancellationOfFTDTO dto = new CancellationOfFTDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }
}
