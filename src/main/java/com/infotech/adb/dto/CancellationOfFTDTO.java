package com.infotech.adb.dto;

import com.infotech.adb.model.entity.CancellationOfFT;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class CancellationOfFTDTO implements BaseDTO<CancellationOfFTDTO, CancellationOfFT> {

    private Long id;
    private String tradeTranType;
    private String traderNTN;
    private String traderName;
    private String Iban;
    private String finInsUniqueNumber;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    public String status;


    @Override
    public CancellationOfFT convertToEntity() {
        CancellationOfFT entity = new CancellationOfFT();

        entity.setId(this.getId());
        entity.setTradeType(this.getTradeTranType());
        entity.setTraderNTN(this.getTraderNTN());
        entity.setTraderName(this.getTraderName());
        entity.setIban(this.getIban());
        entity.setFinInsUniqueNumber(this.getFinInsUniqueNumber());
        entity.setLastModifiedBy(this.getLastModifiedBy());
        entity.setLastModifiedDate(this.getLastModifiedDate());
        entity.setStatus(this.status);



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
            this.setStatus(entity.getStatus());
            this.setLastModifiedBy(entity.getLastModifiedBy());
            this.setLastModifiedDate(entity.getLastModifiedDate());


        }
    }

    @Override
    public CancellationOfFTDTO convertToNewDTO(CancellationOfFT entity, boolean partialFill) {
        CancellationOfFTDTO dto = new CancellationOfFTDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }
}
