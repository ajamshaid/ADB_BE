package com.infotech.adb.dto;

import com.infotech.adb.model.entity.COBGdFt;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class  COBGdFtDTO implements BaseDTO<COBGdFtDTO, COBGdFt>{
    public   Long id;
    public String cobUniqueIdNumber;
    public String tradeTranType;

    @Override
    public COBGdFt convertToEntity() {
        return null;
    }

    @Override
    public void convertToDTO(COBGdFt entity, boolean partialFill) {

        this.id = entity.getId();
        this.cobUniqueIdNumber = entity.getCobUniqueIdNumber();
        this.tradeTranType = entity.getTradeTranType();

    }
    @Override
    public COBGdFtDTO convertToNewDTO(COBGdFt entity, boolean partialFill) {
        COBGdFtDTO dto = new COBGdFtDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }
    }