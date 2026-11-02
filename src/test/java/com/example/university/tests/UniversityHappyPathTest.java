package com.example.university.tests;


import com.example.university.config.ApiConfig;
import com.example.university.listeners.ExtentReportListener;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


import java.util.Random;
import java.util.UUID;


import static org.hamcrest.Matchers.notNullValue;


@Feature("University API - Happy Path Tests")
@Listeners(ExtentReportListener.class)
public class UniversityHappyPathTest {


    private int dynamicUniversityId;
    private String dynamicUniversityName;
    private String dynamicLocation;


    @BeforeClass
    public void setupTestData() {
        // Dynamically generate distinct test data for every test run
        Random random = new Random();
        dynamicUniversityId = 1000 + random.nextInt(9000); // Random 4-digit ID
        dynamicUniversityName = "Automated Test University " + UUID.randomUUID().toString().substring(0, 6);
        dynamicLocation = "Campus City " + UUID.randomUUID().toString().substring(0, 4);
    }


    private String buildDynamicUniversityPayload(int id, String name, String location) {
        return """
                {
                  "UniversityID": %d,
                  "UniversityName": "%s",
                  "UniversityLocation": "%s",
                  "UniversityFounded": 1990,
                  "KeyPeople": [
                    {
                      "name": "Dr. Alex Taylor",
                      "age": 48,
                      "title": "Dean"
                    }
                  ]
                }
                """.formatted(id, name, location);
    }


    @Test(priority = 1, description = "Verify university creation via POST /university")
    @Description("Validates that POST /university returns HTTP status 201 when creating a record dynamically")
    public void testCreateUniversity() {
        String payload = buildDynamicUniversityPayload(dynamicUniversityId, dynamicUniversityName, dynamicLocation);


        RestAssured.given()
                .spec(ApiConfig.getRequestSpec())
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/university")
                .then()
                .statusCode(201);
    }


    @Test(priority = 2, description = "Verify successful retrieval of a university by name via GET /university")
    @Description("Validates that GET /university with dynamic query param returns HTTP status 200")
    public void testGetUniversityByName() {
        RestAssured.given()
                .spec(ApiConfig.getRequestSpec())
                .queryParam("universityName", dynamicUniversityName)
                .when()
                .get("/university")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }


    @Test(priority = 3, description = "Verify university retrieval by ID via GET /university{universityID}")
    @Description("Validates that GET /university{universityID} with dynamic path param returns HTTP status 200")
    public void testGetUniversityById() {
        RestAssured.given()
                .spec(ApiConfig.getRequestSpec())
                .pathParam("universityID", dynamicUniversityId)
                .when()
                .get("/university{universityID}")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }


    @Test(priority = 4, description = "Verify university update via PUT /university{universityID}")
    @Description("Validates that PUT /university{universityID} with dynamic path param and payload returns HTTP status 201")
    public void testUpdateUniversity() {
        String updatePayload = buildDynamicUniversityPayload(dynamicUniversityId, dynamicUniversityName + " - Modified", dynamicLocation);


        RestAssured.given()
                .spec(ApiConfig.getRequestSpec())
                .pathParam("universityID", dynamicUniversityId)
                .contentType(ContentType.JSON)
                .body(updatePayload)
                .when()
                .put("/university{universityID}")
                .then()
                .statusCode(201);
    }


    @Test(priority = 5, description = "Verify university deletion via DELETE /university")
    @Description("Validates that DELETE /university with dynamic query param returns HTTP status 204")
    public void testDeleteUniversity() {
        RestAssured.given()
                .spec(ApiConfig.getRequestSpec())
                .queryParam("universityName", dynamicUniversityName)
                .when()
                .delete("/university")
                .then()
                .statusCode(204);
    }


    @Test(priority = 6, description = "Verify retrieval of all universities via GET /universities")
    @Description("Validates that GET /universities returns HTTP status 200")
    public void testGetAllUniversities() {
        RestAssured.given()
                .spec(ApiConfig.getRequestSpec())
                .when()
                .get("/universities")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }
}