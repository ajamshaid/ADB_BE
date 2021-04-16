package com.infotech.adb.dto;

import com.infotech.adb.model.entity.ItemInformation;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ItemInformationExportDTO implements BaseDTO<ItemInformationExportDTO, ItemInformation> {
    private String hsCode;
    private Integer quantity;
    private String uom;

    private BigDecimal unitPrice;
    private BigDecimal totalValue;
    private BigDecimal exportValue;

    public ItemInformationExportDTO(ItemInformation entity) {
        convertToDTO(entity, true);
    }

    @Override
    public ItemInformation convertToEntity() {
        ItemInformation entity = new ItemInformation();
        return entity;
    }

    @Override
    public void convertToDTO(ItemInformation entity, boolean partialFill) {

        if (entity != null) {
            this.setHsCode(entity.getHsCode());
            this.setQuantity(entity.getQuantity());
            this.setUom(entity.getUom());
            this.setUnitPrice(entity.getUnitPrice());
            this.setTotalValue(entity.getUnitPrice().multiply(new BigDecimal(entity.getQuantity())));
            this.setExportValue(entity.getImportOrExportValue());

        }
    }

    @Override
    public ItemInformationExportDTO convertToNewDTO(ItemInformation entity, boolean partialFill) {
        ItemInformationExportDTO dto = new ItemInformationExportDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }
}