package com.example.university.tests;


import com.example.university.config.ApiConfig;
import com.example.university.listeners.ExtentReportListener;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners(ExtentReportListener.class)
public class UniversityNegativeTest {

	  @Test(description = "Question (b): Verify 422 Unprocessable Entity when required parameter is missing")
	    @Description("Verifies that DELETE /university returns 422 when the required 'universityName' query param is omitted")
	    public void verifyMissingRequiredParameterReturns422() {
	        RestAssured.given()
	                .spec(ApiConfig.getRequestSpec())
	                .when()
	                .delete("/university")
	                .then()
	                .statusCode(422);
	    }
	  
	  @Test(description = "Question (c): Verify 401 Unauthorized when API key is missing or invalid")
	    @Description("Verifies that calling the endpoint without valid authentication returns HTTP 401")
	    public void verifyInvalidApiKeyReturns401() {
	        RestAssured.given()
	                .baseUri(ApiConfig.BASE_URL)
	                .when()
	                .get("/universities")
	                .then()
	                .statusCode(401);
	    }
	  
	  @Test(description = "Question (c): Verify 401 Unauthorized on GET /university{universityID} when API key is missing")
	  @Description("Verifies that requesting a university by ID without the api_key header returns HTTP 401")
	  public void verifyMissingApiKeyOnGetUniversityByIdReturns401() {
	      RestAssured.given()
	              .baseUri(ApiConfig.BASE_URL)
	              .pathParam("universityID", 978)
	              .when()
	              .get("/university{universityID}")
	              .then()
	              .statusCode(401);
	  }
	  
	   @Test(description = "Question (d): Verify Missing Auth with server mismatch returns 404")
	    @Description("curl -X 'GET' 'http://127.0.0.1:4010/universities?__server=https://zafin-interview.io/api'")
	    public void verifyNotFoundResponseWhenAuthenticationIsMissing() {
	        RestAssured.given()
	                .baseUri(ApiConfig.BASE_URL)
	                .queryParam("__server", "https://zafin-interview.io/api")
	                .when()
	                .get("/universities")
	                .then()
	                .statusCode(404);
	    }
	  
    @Test(description = "Question (e): Verify that an invalid URL path returns HTTP status 404")
    @Description("Verifies that the API returns HTTP status 404 when a request is made to a non-existent endpoint")
    public void verifyInvalidEndpointReturns404() {
        RestAssured.given()
                .spec(ApiConfig.getRequestSpec())
                .when()
                .get("/invalid_endpoint_path")
                .then()
                .statusCode(404);
    }


    @Test(description = "Question (f): Verify behavior when URL encoding is disabled for query parameters")
    @Description("Verifies API handling when unencoded query parameter values are transmitted")
    public void verifyUnencodedQueryParameterHandling() {
        RestAssured.given()
                .spec(ApiConfig.getRequestSpec())
                .urlEncodingEnabled(false)
                .queryParam("universityName", "University_of_Toronto")
                .when()
                .get("/university")
                .then()
                .statusCode(200);
    }
}