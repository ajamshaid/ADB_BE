package com.infotech.adb.dto;

import com.infotech.adb.model.entity.ReversalOfBdaBca;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    private String lastModifiedBy;
    private Date lastModifiedDate;


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
        entity.setLastModifiedBy(this.getLastModifiedBy());
        entity.setLastModifiedDate(this.getLastModifiedDate());


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

            this.setLastModifiedBy(entity.getLastModifiedBy());
            this.setLastModifiedDate(entity.getLastModifiedDate());


        }
    }

    @Override
    public ReversalOfBdaBcaDTO convertToNewDTO(ReversalOfBdaBca entity, boolean partialFill) {
        ReversalOfBdaBcaDTO dto = new ReversalOfBdaBcaDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }
}
