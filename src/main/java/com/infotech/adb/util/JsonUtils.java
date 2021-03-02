package com.infotech.adb.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class JsonUtils {

    public String toFilterJson(Object object, String... requiredProps) {
        String result = null;
        FilterProvider filter = new SimpleFilterProvider().addFilter("assetModelFilter", SimpleBeanPropertyFilter.filterOutAllExcept(requiredProps));
        try {
            result = new ObjectMapper().writer(filter).writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <T> T jsonToObject(String strObject, Class<T> valueType) {
        T object = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            object = objectMapper.readValue(strObject, valueType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return object;
    }
}
