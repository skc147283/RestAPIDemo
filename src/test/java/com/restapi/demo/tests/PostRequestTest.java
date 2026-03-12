package com.restapi.demo.tests;

import com.restapi.demo.base.BaseTest;
import com.restapi.demo.pojo.Post;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Test class demonstrating POST request operations using REST Assured.
 * Target API: https://jsonplaceholder.typicode.com/posts
 */
public class PostRequestTest extends BaseTest {

    @Test(description = "Verify that POST /posts creates a new resource and returns HTTP 201")
    public void testCreatePost() {
        Post newPost = new Post(1, "Test Title", "Test body content for the demo post.");

        given()
                .spec(requestSpec)
                .body(newPost)
        .when()
                .post("/posts")
        .then()
                .statusCode(201)
                .body("title", equalTo(newPost.getTitle()))
                .body("body", equalTo(newPost.getBody()))
                .body("userId", equalTo(newPost.getUserId()))
                .body("id", notNullValue());
    }

    @Test(description = "Verify that POST /posts response body contains the created resource")
    public void testCreatePostResponseBody() {
        Post newPost = new Post(2, "Another Test Title", "Body content of the second demo post.");

        Post createdPost = given()
                .spec(requestSpec)
                .body(newPost)
        .when()
                .post("/posts")
        .then()
                .statusCode(201)
                .extract()
                .as(Post.class);

        Assert.assertEquals(createdPost.getUserId(), newPost.getUserId(),
                "Created post userId should match request");
        Assert.assertEquals(createdPost.getTitle(), newPost.getTitle(),
                "Created post title should match request");
        Assert.assertEquals(createdPost.getBody(), newPost.getBody(),
                "Created post body should match request");
        Assert.assertTrue(createdPost.getId() > 0, "Created post should have a valid ID");
    }

    @Test(description = "Verify that POST /posts with a raw JSON body returns HTTP 201")
    public void testCreatePostWithRawJson() {
        String requestBody = "{"
                + "\"title\": \"Raw JSON Post\","
                + "\"body\": \"Posted using raw JSON string.\","
                + "\"userId\": 3"
                + "}";

        given()
                .spec(requestSpec)
                .body(requestBody)
        .when()
                .post("/posts")
        .then()
                .statusCode(201)
                .body("title", equalTo("Raw JSON Post"))
                .body("userId", equalTo(3));
    }
}
