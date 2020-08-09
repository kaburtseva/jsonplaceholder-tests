package com.jsonpl.api.services;

import com.jsonpl.api.models.Posts;
import io.qameta.allure.Step;

import java.util.List;

public class PostService extends BaseApiService {

    @Step("get all by user id {userId}")
    public List<Posts> getAllPostsByUser(String userId) {
        return setUp().when().get("posts?userId=" + userId).then().log().ifError()
                .statusCode(200).extract().body().jsonPath().getList("", Posts.class);
    }
}
