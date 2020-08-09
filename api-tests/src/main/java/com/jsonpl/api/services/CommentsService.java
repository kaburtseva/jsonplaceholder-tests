package com.jsonpl.api.services;

import com.jsonplaceholder.api.models.Comments;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommentsService extends com.jsonplaceholder.api.services.BaseApiService {

    Set<String> getAllEmailAddresses(int post) {
        Set<String> emailForTheCurrenComment = new HashSet<>();
        getAllCommentsForThePost(post).stream().forEach(it -> emailForTheCurrenComment.add(it.email));
        return emailForTheCurrenComment;
    }

    public List<Comments> getAllCommentsForThePost(int postId) {
        return setUp().when().get(String.format("/posts/%s/comments", postId)).
                then().log().ifError().statusCode(200).extract().body().jsonPath().getList("", Comments.class);
    }
}
