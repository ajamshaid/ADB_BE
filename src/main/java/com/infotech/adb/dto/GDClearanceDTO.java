package com.infotech.adb.dto;

import com.infotech.adb.model.entity.GDClearance;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class GDClearanceDTO implements BaseDTO<GDClearanceDTO, GDClearance> {

    private Long id;
    private String tradeTranType;
    private String traderNTN;
    private String traderName;
    private String Iban;
    private String gdNumber;
    private String clearanceStatus;

    private String lastModifiedBy;
    private Date lastModifiedDate;



    @Override
    public GDClearance convertToEntity() {
        GDClearance entity = new GDClearance();

        entity.setId(this.getId());
        entity.setTradeType(this.getTradeTranType());
        entity.setTraderNTN(this.getTraderNTN());
        entity.setTraderName(this.getTraderName());
        entity.setIban(this.getIban());
        entity.setGdNumber(this.getGdNumber());
        entity.setClearanceStatus(this.getClearanceStatus());
        entity.setLastModifiedBy(this.getLastModifiedBy());
        entity.setLastModifiedDate(this.getLastModifiedDate());



        return entity;
    }

    @Override
    public void convertToDTO(GDClearance entity, boolean partialFill) {
        if(entity != null){

            this.setId(entity.getId());
            this.setIban(entity.getIban());
            this.setTradeTranType(entity.getTradeType());
            this.setTraderName(entity.getTraderName());
            this.setTraderNTN(entity.getTraderNTN());
            this.setGdNumber(entity.getGdNumber());
            this.setClearanceStatus(entity.getClearanceStatus());


            this.setLastModifiedBy(entity.getLastModifiedBy());
            this.setLastModifiedDate(entity.getLastModifiedDate());


        }
    }

    @Override
    public GDClearanceDTO convertToNewDTO(GDClearance entity, boolean partialFill) {
        GDClearanceDTO dto = new GDClearanceDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }
}
