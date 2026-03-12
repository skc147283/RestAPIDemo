package com.restapi.demo.tests;

import com.restapi.demo.base.BaseTest;
import com.restapi.demo.pojo.Post;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Test class demonstrating GET request operations using REST Assured.
 * Target API: https://jsonplaceholder.typicode.com/posts
 */
public class GetRequestTest extends BaseTest {

    @Test(description = "Verify that GET /posts returns HTTP 200 and a non-empty list")
    public void testGetAllPosts() {
        given()
                .spec(requestSpec)
        .when()
                .get("/posts")
        .then()
                .statusCode(200)
                .body("$", not(empty()))
                .body("size()", equalTo(100));
    }

    @Test(description = "Verify that GET /posts/{id} returns the correct post")
    public void testGetPostById() {
        int postId = 1;

        given()
                .spec(requestSpec)
        .when()
                .get("/posts/{id}", postId)
        .then()
                .statusCode(200)
                .body("id", equalTo(postId))
                .body("userId", notNullValue())
                .body("title", not(emptyString()))
                .body("body", not(emptyString()));
    }

    @Test(description = "Verify that GET /posts/{id} response can be deserialized into a Post object")
    public void testGetPostByIdDeserialization() {
        int postId = 1;

        Post post = given()
                .spec(requestSpec)
        .when()
                .get("/posts/{id}", postId)
        .then()
                .statusCode(200)
                .extract()
                .as(Post.class);

        Assert.assertEquals(post.getId(), postId, "Post ID should match requested ID");
        Assert.assertNotNull(post.getTitle(), "Post title should not be null");
        Assert.assertFalse(post.getTitle().isEmpty(), "Post title should not be empty");
        Assert.assertNotNull(post.getBody(), "Post body should not be null");
        Assert.assertFalse(post.getBody().isEmpty(), "Post body should not be empty");
        Assert.assertTrue(post.getUserId() > 0, "User ID should be positive");
    }

    @Test(description = "Verify that GET /posts?userId={id} filters posts by user")
    public void testGetPostsByUserId() {
        int userId = 1;

        Response response = given()
                .spec(requestSpec)
                .queryParam("userId", userId)
        .when()
                .get("/posts")
        .then()
                .statusCode(200)
                .body("$", not(empty()))
                .extract()
                .response();

        List<Integer> userIds = response.jsonPath().getList("userId");
        userIds.forEach(id ->
                Assert.assertEquals((int) id, userId,
                        "All returned posts should belong to userId=" + userId)
        );
    }

    @Test(description = "Verify that GET /posts/{id} returns HTTP 404 for a non-existent post")
    public void testGetNonExistentPost() {
        int nonExistentId = 9999;

        given()
                .spec(requestSpec)
        .when()
                .get("/posts/{id}", nonExistentId)
        .then()
                .statusCode(404);
    }
}
