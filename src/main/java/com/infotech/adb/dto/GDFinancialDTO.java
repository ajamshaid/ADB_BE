package com.infotech.adb.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.model.entity.GDFinancial;
import com.infotech.adb.util.JsonUtils;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.HashMap;

public class GDFinancialDTO extends HashMap<String, Object> implements BaseDTO<GDFinancialDTO, GDFinancial> {

    public static String GD_TYPE_IMPORT = "IMPORT";
    public static String GD_TYPE_EXPORT = "EXPORT";

    private String gdType;

    public String getGDNumber(){
        return (String) get("gdNumber");
    }

    @Override
    public GDFinancial convertToEntity() throws JsonProcessingException {
        GDFinancial gdFinancial = new GDFinancial();
        gdFinancial.setGdNumber(this.getGDNumber());
        gdFinancial.setGdType(gdType);
        gdFinancial.setGdfObjectJson(JsonUtils.objectToJson(this));
        gdFinancial.setCreatedOn(ZonedDateTime.now());
        return gdFinancial;
    }

    @Override
    public void convertToDTO(GDFinancial entity, boolean partialFill) {

    }

    @Override
    public GDFinancialDTO convertToNewDTO(GDFinancial entity, boolean partialFill) {
        return null;
    }

    @JsonIgnore
    public String getGdType() {
        return gdType;
    }

    public void setGdType(String gdType) {
        this.gdType = gdType;
    }
}
