package com.file2chart.service.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.file2chart.exceptions.custom.JsonConversionException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JsonConverter {
    private final ObjectMapper objectMapper;

    public <T> T toObject(JsonNode jsonNode, Class<T> clazz) {
        try {
            return objectMapper.treeToValue(jsonNode, clazz);
        } catch (JsonProcessingException e) {
            throw new JsonConversionException("Error converting JSON to class: " + clazz.getSimpleName(), e);
        }
    }

    public <T> T toObject(byte[] content, Class<T> clazz) {
        String s = new String(content);
        JsonNode json = toJSON(s);
        return toObject(json, clazz);
    }

    public <T> T toObject(String content, Class<T> clazz) {
        JsonNode json = toJSON(content);
        return toObject(json, clazz);
    }

    public String toJSON(Object object, boolean prettyPrint) {
        try {
            return prettyPrint
                    ? objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object)
                    : objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JsonConversionException("Error converting object to JSON string", e);
        }
    }

    public JsonNode toJSON(String content) {
        try {
            return objectMapper.readTree(content);
        } catch (JsonProcessingException e) {
            throw new JsonConversionException("Error converting string to JSON", e);
        }
    }

    public JsonNode emptyJSON() {
        return objectMapper.createObjectNode();
    }

    public boolean isValidJSON(String content) {
        boolean valid = true;
        try {
            toJSON(content);
        } catch (Exception e) {
            valid = false;
        }
        return valid;
    }

    public <T> boolean isValidJSON(String content, Class<T> clazz) {
        boolean valid;
        try {
            valid = isValidJSON(content);
            toObject(content, clazz);
        } catch (Exception e) {
            valid = false;
        }
        return valid;
    }
}
