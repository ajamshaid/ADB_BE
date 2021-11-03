package com.infotech.adb.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class JsonUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public String toFilterJson(Object object, String... requiredProps) {
        String result = null;
        FilterProvider filter = new SimpleFilterProvider().addFilter("assetModelFilter", SimpleBeanPropertyFilter.filterOutAllExcept(requiredProps));
        try {
            result = objectMapper.writer(filter).writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <T> T jsonToObject(String strObject, Class<T> valueType) {
        T object = null;
        try {
            object = objectMapper.readValue(strObject, valueType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static String objectToJson(Object object) throws JsonProcessingException {
        ObjectWriter objectWriter = objectMapper.writer();
        return objectWriter.writeValueAsString(object);
    }

    public static String removeExtraWhitespacesFromJson(String json) {

        boolean quoted = false;
        boolean escaped = false;
        String out = "";
        for (Character c : json.toCharArray()) {
            if (escaped) {
                out += c;
                escaped = false;
                continue;
            }
            if (c == '"') {
                quoted = !quoted;
            } else if (c == '\\') {
                escaped = true;
            }
            if (c == ' ' & !quoted) {
                continue;
            }
            out += c;
        }
        return out.replaceAll("\\n","");
    }
}
