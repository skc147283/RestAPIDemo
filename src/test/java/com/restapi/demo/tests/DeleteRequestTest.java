package com.restapi.demo.tests;

import com.restapi.demo.base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Test class demonstrating DELETE request operations using REST Assured.
 * Target API: https://jsonplaceholder.typicode.com/posts/{id}
 */
public class DeleteRequestTest extends BaseTest {

    @Test(description = "Verify that DELETE /posts/{id} returns HTTP 200")
    public void testDeletePost() {
        int postId = 1;

        given()
                .spec(requestSpec)
        .when()
                .delete("/posts/{id}", postId)
        .then()
                .statusCode(200);
    }

    @Test(description = "Verify that DELETE /posts/{id} returns an empty response body")
    public void testDeletePostResponseBody() {
        int postId = 2;

        Response response = given()
                .spec(requestSpec)
        .when()
                .delete("/posts/{id}", postId)
        .then()
                .statusCode(200)
                .extract()
                .response();

        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.trim().equals("{}") || responseBody.trim().isEmpty(),
                "Delete response body should be empty or an empty JSON object");
    }

    @Test(description = "Verify that DELETE /posts/{id} response time is within acceptable limit")
    public void testDeletePostResponseTime() {
        int postId = 3;

        Response response = given()
                .spec(requestSpec)
        .when()
                .delete("/posts/{id}", postId)
        .then()
                .statusCode(200)
                .extract()
                .response();

        long responseTimeMs = response.getTime();
        Assert.assertTrue(responseTimeMs < 5000,
                "Response time should be less than 5000ms, but was: " + responseTimeMs + "ms");
    }
}
