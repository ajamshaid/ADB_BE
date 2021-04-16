package com.infotech.adb.dto;

import com.infotech.adb.model.entity.ItemInformation;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ItemInformationImportDTO implements BaseDTO<ItemInformationImportDTO, ItemInformation> {
    private String hsCode;
    private String goodsDescription;
    private Integer quantity;
    private String uom;
    private String countryOfOrigin;
    private String sample;
    private String sampleValue;

    private BigDecimal unitPrice;
    private BigDecimal totalValue;
    private BigDecimal importValue;
    private BigDecimal importValueInvoice;

    public ItemInformationImportDTO(ItemInformation entity) {
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
            this.setGoodsDescription(entity.getGoodsDescription());
            this.setQuantity(entity.getQuantity());
            this.setUom(entity.getUom());
            this.setCountryOfOrigin(entity.getCountryOfOrigin());
            this.setSample(entity.getSample());
            this.setSampleValue(entity.getSampleValue());
            this.setUnitPrice(entity.getUnitPrice());
            this.setTotalValue(entity.getUnitPrice().multiply(new BigDecimal(entity.getQuantity())));
            this.setImportValue(entity.getImportOrExportValue());

        }
    }

    @Override
    public ItemInformationImportDTO convertToNewDTO(ItemInformation entity, boolean partialFill) {
        ItemInformationImportDTO dto = new ItemInformationImportDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }
}