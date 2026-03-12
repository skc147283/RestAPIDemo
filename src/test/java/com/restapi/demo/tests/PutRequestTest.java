package com.restapi.demo.tests;

import com.restapi.demo.base.BaseTest;
import com.restapi.demo.pojo.Post;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Test class demonstrating PUT request operations using REST Assured.
 * Target API: https://jsonplaceholder.typicode.com/posts/{id}
 */
public class PutRequestTest extends BaseTest {

    @Test(description = "Verify that PUT /posts/{id} updates the resource and returns HTTP 200")
    public void testUpdatePost() {
        int postId = 1;
        Post updatedPost = new Post(1, postId, "Updated Title", "Updated body content.");

        given()
                .spec(requestSpec)
                .body(updatedPost)
        .when()
                .put("/posts/{id}", postId)
        .then()
                .statusCode(200)
                .body("id", equalTo(postId))
                .body("title", equalTo(updatedPost.getTitle()))
                .body("body", equalTo(updatedPost.getBody()))
                .body("userId", equalTo(updatedPost.getUserId()));
    }

    @Test(description = "Verify that PUT /posts/{id} response can be deserialized into a Post object")
    public void testUpdatePostDeserialization() {
        int postId = 5;
        Post updatedPost = new Post(1, postId, "Deserialized Update Title", "Deserialized update body.");

        Post responsePost = given()
                .spec(requestSpec)
                .body(updatedPost)
        .when()
                .put("/posts/{id}", postId)
        .then()
                .statusCode(200)
                .extract()
                .as(Post.class);

        Assert.assertEquals(responsePost.getId(), postId, "Response post ID should match");
        Assert.assertEquals(responsePost.getTitle(), updatedPost.getTitle(),
                "Response post title should match updated title");
        Assert.assertEquals(responsePost.getBody(), updatedPost.getBody(),
                "Response post body should match updated body");
    }

    @Test(description = "Verify that PATCH /posts/{id} partially updates a resource and returns HTTP 200")
    public void testPartialUpdatePost() {
        int postId = 1;
        String patchBody = "{\"title\": \"Patched Title\"}";

        given()
                .spec(requestSpec)
                .body(patchBody)
        .when()
                .patch("/posts/{id}", postId)
        .then()
                .statusCode(200)
                .body("id", equalTo(postId))
                .body("title", equalTo("Patched Title"));
    }
}
