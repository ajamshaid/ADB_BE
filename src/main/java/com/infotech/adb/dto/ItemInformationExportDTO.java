package com.infotech.adb.dto;

import com.infotech.adb.model.entity.ItemInformation;
import com.infotech.adb.util.AppUtility;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ItemInformationExportDTO implements BaseDTO<ItemInformationExportDTO, ItemInformation> {
    private Long iiId;
    private String hsCode;
    private String goodsDescription;
    private BigDecimal quantity;
    private String uom;
    private BigDecimal itemInvoiceValue;
    private String countryOfOrigin;

    public ItemInformationExportDTO(ItemInformation entity) {
        convertToDTO(entity, true);
    }

    @Override
    public ItemInformation convertToEntity() {
        ItemInformation entity = new ItemInformation();
        entity.setId(this.getIiId());
        entity.setHsCode(this.getHsCode());
        entity.setCountryOfOrigin(this.getCountryOfOrigin());
        entity.setUom(this.getUom());
        entity.setImportOrExportValue(this.getItemInvoiceValue());
        entity.setGoodsDescription(this.getGoodsDescription());
        entity.setQuantity(this.getQuantity());

        return entity;
    }

    @Override
    public void convertToDTO(ItemInformation entity, boolean partialFill) {

        if (entity != null) {
            this.setIiId(entity.getId());
            this.setHsCode(entity.getHsCode());
            this.setGoodsDescription(entity.getGoodsDescription());
            this.setQuantity(AppUtility.isEmpty(entity.getQuantity()) ? new BigDecimal("0" ) : entity.getQuantity());
            this.setUom(entity.getUom());
            this.setCountryOfOrigin(entity.getCountryOfOrigin());
            this.setItemInvoiceValue(entity.getImportOrExportValue());
        }
    }

    @Override
    public ItemInformationExportDTO convertToNewDTO(ItemInformation entity, boolean partialFill) {
        ItemInformationExportDTO dto = new ItemInformationExportDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }
}