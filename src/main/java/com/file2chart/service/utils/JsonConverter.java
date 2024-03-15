package com.file2chart.service.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JsonConverter {
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public <T> T toObject(JsonNode jsonNode, Class<T> clazz) {
        return objectMapper.treeToValue(jsonNode, clazz);
    }

    @SneakyThrows
    public <T> T toObject(byte[] content, Class<T> clazz) {
        String s = new String(content);
        JsonNode json = toJSON(s);
        return toObject(json, clazz);
    }

    @SneakyThrows
    public <T> T toObject(String content, Class<T> clazz) {
        JsonNode json = toJSON(content);
        return toObject(json, clazz);
    }

    @SneakyThrows
    public String toJSON(Object object, boolean prettyPrint) {
        return prettyPrint
                ? objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object)
                : objectMapper.writeValueAsString(object);
    }

    @SneakyThrows
    public JsonNode toJSON(String content) {
        return objectMapper.readTree(content);
    }

    @SneakyThrows
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
