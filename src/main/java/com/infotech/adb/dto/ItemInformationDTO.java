package com.infotech.adb.dto;

import com.infotech.adb.model.entity.ItemInformation;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemInformationDTO implements BaseDTO<ItemInformationDTO, ItemInformation> {
    private String hsCode;
    private String goodsDescription;
    private Integer quantity;
    private String uom;
    private String countryOfOrigin;
    private String sample;
    private String sampleValue;

    public ItemInformationDTO(ItemInformation entity) {
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

        }
    }

    @Override
    public ItemInformationDTO convertToNewDTO(ItemInformation entity, boolean partialFill) {
        ItemInformationDTO dto = new ItemInformationDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }
}