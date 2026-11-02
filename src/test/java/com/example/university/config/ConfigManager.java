package com.example.university.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;




public class ConfigManager {


    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IllegalStateException("config.properties file not found under src/test/resources");
            }
            properties.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load config.properties file", ex);
        }
    }




 
    public static String getRequiredProperty(String key) {
        String value = System.getProperty(key, properties.getProperty(key));
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Missing required configuration key in config.properties or System properties: " + key);
        }
        return value.trim();
    }
}