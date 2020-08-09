package com.jsonpl.api.services;


import com.jsonpl.api.models.Posts;
import io.qameta.allure.Step;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PostService extends BaseApiService {

    public Set<Integer> getPostsByUserId(int userId) {
        Set<Integer> ids = new HashSet<>();
        getAllPostsByUser(userId).stream().forEach(it -> ids.add(it.id));
        return ids;
    }

    @Step("get all by user id {user}")
    public List<Posts> getAllPostsByUser(int userId) {
        return setUp().when().get("posts?userId=" + userId).then().log().ifError()
                .statusCode(200).extract().body().jsonPath().getList("", Posts.class);
    }
}
