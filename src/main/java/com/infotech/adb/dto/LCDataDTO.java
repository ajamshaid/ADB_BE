package com.infotech.adb.dto;

import com.infotech.adb.model.entity.LCData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class LCDataDTO implements BaseDTO<LCDataDTO, LCData> {
    private BigDecimal advPayPercentage;
    private BigDecimal sightPercentage;
    private BigDecimal usancePercentage;
    private Integer days;
    private BigDecimal totalPercentage;

    public LCDataDTO(LCData entity) {
        convertToDTO(entity, true);
    }

    @Override
    public LCData convertToEntity() {
        LCData entity = new LCData();
        return entity;
    }

    @Override
    public void convertToDTO(LCData entity, boolean partialFill) {

        if (entity != null) {
            this.setAdvPayPercentage(entity.getAdvPayPercentage());
            this.setSightPercentage(entity.getSightPercentage());
            this.setUsancePercentage(entity.getUsancePercentage());
            this.setDays(entity.getDays());
            this.setTotalPercentage(entity.getTotalPercentage());
        }
    }

    @Override
    public LCDataDTO convertToNewDTO(LCData entity, boolean partialFill) {
        LCDataDTO dto = new LCDataDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }
}