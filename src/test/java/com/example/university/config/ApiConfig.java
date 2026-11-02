package com.example.university.config;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;


public class ApiConfig {


   
    public static final String BASE_URL = ConfigManager.getRequiredProperty("base.url");
    public static final String API_KEY = ConfigManager.getRequiredProperty("api.key");
    public static final String AUTH_HEADER = ConfigManager.getRequiredProperty("api.key.header");


    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .addHeader(AUTH_HEADER, API_KEY)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }


    public static RequestSpecification getRequestSpecWithoutAuth() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }
}