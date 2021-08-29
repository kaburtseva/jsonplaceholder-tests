package com.jsonpl.api.services;

import com.jsonpl.api.models.Posts;
import io.qameta.allure.Step;

import java.util.List;

public class PostService extends BaseApiService {

    @Step("get all by user id {userId}")
    public List<Posts> getAllPostsByUser(int userId) {
        return setUp().when().get("posts?userId=" + userId).then().log().ifError()
                .statusCode(200).extract().body().jsonPath().getList("", Posts.class);
    }

    @Step("get all posts")
    public List<Posts> getAllPosts() {
        return setUp().when().get("posts").then().log().ifError()
                .statusCode(200).extract().body().jsonPath().getList("", Posts.class);
    }

    @Step("create new post for user {userId}")
    public Posts createNewPost(int userId) {
        return setUp()
                .queryParam("title", "test")
                .queryParam("body", "bar")
                .queryParam("userId", userId)
                .header("Content-type", "application/json")
                .when()
                .post("posts").then().log().ifError().extract().body().as(Posts.class);
    }

    @Step("update post with id {postId} for user {userId} to new title {newTitle}")
    public int updatePostById(int postId, int userId, String newTitle) {
        return setUp().when()
                .queryParam("title", newTitle)
                .queryParam("body", "bar")
                .queryParam("userId", userId)
                .queryParam("id", postId)
                .header("Content-type", "application/json")
                .put("posts/" + postId).statusCode();
    }

    @Step("delete post with id {postId}")
    public int deletePostById(int postId) {
        return setUp().when()
                .delete("posts/" + postId).statusCode();
    }
}
