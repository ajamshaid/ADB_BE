package com.infotech.adb.dto;

import com.infotech.adb.model.entity.COBGdFt;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class  COBGdFtDTO implements BaseDTO<COBGdFtDTO, COBGdFt>{
    public   Long id;
    public String cobUniqueIdNumber;
    public String tradeTranType;
    public String iban;
    public String cobStatus;
    public String status;

    private String lastModifiedBy;
    private Date lastModifiedDate;

    @Override
    public COBGdFt convertToEntity() {
        return null;
    }

    @Override
    public void convertToDTO(COBGdFt entity, boolean partialFill) {

        this.id = entity.getId();
        this.cobUniqueIdNumber = entity.getCobUniqueIdNumber();
        this.tradeTranType = entity.getTradeTranType();
        this.iban = entity.getNewBankIBAN();
        this.cobStatus = entity.getCobStatus();
        this.cobStatus = entity.getStatus();


        this.setLastModifiedBy(entity.getLastModifiedBy());
        this.setLastModifiedDate(entity.getLastModifiedDate());


    }
    @Override
    public COBGdFtDTO convertToNewDTO(COBGdFt entity, boolean partialFill) {
        COBGdFtDTO dto = new COBGdFtDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }
    }