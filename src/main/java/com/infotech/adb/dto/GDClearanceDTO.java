package com.infotech.adb.dto;

import com.infotech.adb.model.entity.GDClearance;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GDClearanceDTO implements BaseDTO<GDClearanceDTO, GDClearance> {

    private Long id;
    private String tradeType;
    private String traderNTN;
    private String traderName;
    private String Iban;
    private String gdNumber;
    private String clearanceStatus;

    public GDClearanceDTO(String id , String tradeType, String traderNTN, String traderName, String iban, String gdNumber) {

        this.tradeType = tradeType;
        this.traderNTN = traderNTN;
        this.traderName = traderName;
        Iban = iban;
        this.gdNumber = gdNumber;
    }


    @Override
    public GDClearance convertToEntity() {
        GDClearance entity = new GDClearance();

        entity.setId(this.getId());
        entity.setTradeType(this.getTradeType());
        entity.setTraderNTN(this.getTraderNTN());
        entity.setTraderName(this.getTraderName());
        entity.setIban(this.getIban());
        entity.setGdNumber(this.getGdNumber());
        entity.setClearanceStatus(this.getClearanceStatus());


        return entity;
    }

    @Override
    public void convertToDTO(GDClearance entity, boolean partialFill) {
        if(entity != null){

            this.setId(entity.getId());
            this.setIban(entity.getIban());
            this.setTradeType(entity.getTradeType());
            this.setTraderName(entity.getTraderName());
            this.setTraderNTN(entity.getTraderNTN());
            this.setGdNumber(entity.getGdNumber());
            this.setClearanceStatus(entity.getClearanceStatus());

        }
    }

    @Override
    public GDClearanceDTO convertToNewDTO(GDClearance entity, boolean partialFill) {
        GDClearanceDTO dto = new GDClearanceDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }
}
