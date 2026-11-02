package com.example.university.clients;


import com.example.university.config.ApiConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


import java.util.Map;


public class UniversityApi {


    public UniversityApi() {
    }


    public Response getUniversities(Map<String, ?> queryParams) {
        RequestSpecification spec = RestAssured.given().spec(ApiConfig.getRequestSpec());
        if (queryParams != null && !queryParams.isEmpty()) {
            spec.queryParams(queryParams);
        }
        return spec.when().get("/universities");
    }


    public Response getUniversitiesWithoutAuth(Map<String, ?> queryParams) {
        RequestSpecification spec = RestAssured.given().spec(ApiConfig.getRequestSpecWithoutAuth());
        if (queryParams != null && !queryParams.isEmpty()) {
            spec.queryParams(queryParams);
        }
        return spec.when().get("/universities");
    }


    public Response getUniversityById(String id) {
        return RestAssured.given()
                .spec(ApiConfig.getRequestSpec())
                .pathParam("id", id)
                .when()
                .get("/universities/{id}");
    }


    public Response createUniversity(Map<String, Object> payload) {
        return RestAssured.given()
                .spec(ApiConfig.getRequestSpec())
                .body(payload)
                .when()
                .post("/universities");
    }


    public Response updateUniversity(String id, Map<String, Object> payload) {
        return RestAssured.given()
                .spec(ApiConfig.getRequestSpec())
                .pathParam("id", id)
                .body(payload)
                .when()
                .put("/universities/{id}");
    }


    public Response deleteUniversity(String id) {
        return RestAssured.given()
                .spec(ApiConfig.getRequestSpec())
                .pathParam("id", id)
                .when()
                .delete("/universities/{id}");
    }


    public Response executeCustomGet(String rawPath, Map<String, ?> headers, Map<String, ?> queryParams, boolean urlEncoding) {
        RequestSpecification spec = RestAssured.given()
                .baseUri(ApiConfig.BASE_URL)
                .urlEncodingEnabled(urlEncoding);


        if (headers != null) {
            spec.headers(headers);
        }
        if (queryParams != null) {
            spec.queryParams(queryParams);
        }


        return spec.when().get(rawPath);
    }
}