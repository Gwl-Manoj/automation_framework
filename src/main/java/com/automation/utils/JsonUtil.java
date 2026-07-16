package com.automation.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;

/**
 * JSON Utility
 * Handles JSON serialization and deserialization
 */
public class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Convert object to JSON string
     * @param object Object to convert
     * @return JSON string
     */
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            logger.error("Failed to convert object to JSON", e);
            return null;
        }
    }

    /**
     * Convert JSON string to object
     * @param json JSON string
     * @param clazz Class to convert to
     * @param <T> Type
     * @return Object instance
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            logger.error("Failed to convert JSON to object", e);
            return null;
        }
    }

    /**
     * Convert JSON string to Map
     * @param json JSON string
     * @return Map representation
     */
    public static Map<String, Object> fromJsonToMap(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            logger.error("Failed to convert JSON to Map", e);
            return null;
        }
    }

    /**
     * Read JSON from file and convert to object
     * @param filePath Path to JSON file
     * @param clazz Class to convert to
     * @param <T> Type
     * @return Object instance
     */
    public static <T> T fromJsonFile(String filePath, Class<T> clazz) {
        try {
            return objectMapper.readValue(new File(filePath), clazz);
        } catch (Exception e) {
            logger.error("Failed to read JSON from file: {}", filePath, e);
            return null;
        }
    }

    /**
     * Write object to JSON file
     * @param object Object to write
     * @param filePath Path to JSON file
     */
    public static void toJsonFile(Object object, String filePath) {
        try {
            objectMapper.writeValue(new File(filePath), object);
            logger.info("Object written to JSON file: {}", filePath);
        } catch (Exception e) {
            logger.error("Failed to write object to JSON file: {}", filePath, e);
        }
    }

    /**
     * Pretty print JSON
     * @param json JSON string
     * @return Formatted JSON string
     */
    public static String prettyPrintJson(String json) {
        try {
            Object object = objectMapper.readValue(json, Object.class);
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (Exception e) {
            logger.error("Failed to pretty print JSON", e);
            return json;
        }
    }

    /**
     * Validate JSON string
     * @param json JSON string to validate
     * @return true if valid JSON
     */
    public static boolean isValidJson(String json) {
        try {
            objectMapper.readTree(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}