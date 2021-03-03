package com.infotech.adb.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

public class GDFinancialDTO extends HashMap<String, Object> {

    public String getGDNumber(){
        return (String) get("gdNumber");
    }
}
