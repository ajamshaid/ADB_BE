package com.infotech.adb.dto;

import com.infotech.adb.model.entity.ItemInformation;
import com.infotech.adb.util.AppUtility;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ItemInformationImportDTO implements BaseDTO<ItemInformationImportDTO, ItemInformation> {
    private Long id;
    private String hsCode;
    private String goodsDescription;
    private BigDecimal quantity;
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

        entity.setId(this.getId());
        entity.setHsCode(this.getHsCode());
        entity.setGoodsDescription(this.getGoodsDescription());
        entity.setQuantity(this.getQuantity());
        entity.setUom(this.getUom());
        entity.setCountryOfOrigin(this.getCountryOfOrigin());
        entity.setSample(this.getSample());
        entity.setSampleValue(this.getSampleValue());
        entity.setUnitPrice(this.getUnitPrice());
//        entity.setTotalValue(this.getUnitPrice().multiply(entity.getQuantity()));
        entity.setImportOrExportValue(this.getImportValue());

        return entity;
    }

    @Override
    public void convertToDTO(ItemInformation entity, boolean partialFill) {

        if (entity != null) {
            this.setId(entity.getId());
            this.setHsCode(entity.getHsCode());
            this.setGoodsDescription(entity.getGoodsDescription());
            this.setQuantity(entity.getQuantity());
            this.setUom(entity.getUom());
            this.setCountryOfOrigin(entity.getCountryOfOrigin());
            this.setSample(entity.getSample());
            this.setSampleValue(entity.getSampleValue());
            this.setUnitPrice(AppUtility.isEmpty(entity.getUnitPrice()) ? new BigDecimal("0" ) : entity.getUnitPrice());
            this.setTotalValue(this.getUnitPrice().multiply(entity.getQuantity()));
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