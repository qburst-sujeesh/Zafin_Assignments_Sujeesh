package com.example.university.models;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UniversityPayload {

    public static Map<String, Object> createDynamicPayload() {
        String uniqueSuffix = UUID.randomUUID().toString().substring(0, 8);
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", "Test University " + uniqueSuffix);
        payload.put("country", "Canada");
        payload.put("alpha_two_code", "CA");
        payload.put("state_province", "Ontario");
        payload.put("domains", Collections.singletonList("testuni-" + uniqueSuffix + ".ca"));
        payload.put("web_pages", Collections.singletonList("https://www.testuni-" + uniqueSuffix + ".ca"));
        return payload;
    }

    public static Map<String, Object> createUpdatePayload(String existingName) {
        String uniqueSuffix = UUID.randomUUID().toString().substring(0, 8);
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", existingName + " - Updated");
        payload.put("country", "Canada");
        payload.put("alpha_two_code", "CA");
        payload.put("state_province", "Quebec");
        payload.put("domains", Collections.singletonList("updated-" + uniqueSuffix + ".ca"));
        payload.put("web_pages", Collections.singletonList("https://www.updated-" + uniqueSuffix + ".ca"));
        return payload;
    }
}