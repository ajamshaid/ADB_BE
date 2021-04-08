package com.infotech.adb.dto;

import com.infotech.adb.model.entity.CCData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CCDataDTO implements BaseDTO<CCDataDTO, CCData> {
    private BigDecimal advPayPercentage;
    private BigDecimal docAgainstPayPercentage;
    private BigDecimal docAgainstAcceptancePercentage;
    private Integer days;
    private BigDecimal totalPercentage;

    public CCDataDTO(CCData entity) {
        convertToDTO(entity, true);
    }

    @Override
    public CCData convertToEntity() {
        CCData entity = new CCData();
        return entity;
    }

    @Override
    public void convertToDTO(CCData entity, boolean partialFill) {

        if (entity != null) {
            this.setAdvPayPercentage(entity.getAdvPayPercentage());
            this.setDocAgainstPayPercentage(entity.getDocAgainstPayPercentage());
            this.setDocAgainstAcceptancePercentage(entity.getDocAgainstAcceptancePercentage());
            this.setDays(entity.getDays());
            this.setTotalPercentage(entity.getTotalPercentage());
        }
    }

    @Override
    public CCDataDTO convertToNewDTO(CCData entity, boolean partialFill) {
        CCDataDTO dto = new CCDataDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }
}