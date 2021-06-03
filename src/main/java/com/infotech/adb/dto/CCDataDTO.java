package com.infotech.adb.dto;

import com.infotech.adb.model.entity.CCData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CCDataDTO implements BaseDTO<CCDataDTO, CCData> {
    private Long id;
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
        entity.setId(this.getId());
        entity.setAdvPayPercentage(this.getAdvPayPercentage());
        entity.setDocAgainstPayPercentage(this.getDocAgainstPayPercentage());
        entity.setDocAgainstAcceptancePercentage(this.getDocAgainstAcceptancePercentage());
        entity.setDays(this.getDays());
        entity.setTotalPercentage(this.getTotalPercentage());
        return entity;
    }

    @Override
    public void convertToDTO(CCData entity, boolean partialFill) {

        if (entity != null) {
            this.setId(entity.getId());
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