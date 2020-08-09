package com.jsonpl.api.services;

import com.jsonpl.api.models.Users;
import io.qameta.allure.Step;
import java.util.List;

public class UsersService extends BaseApiService {

    @Step("get user id by name {user}")
    public Users findUserIdByName(String user) {
        return getAllUsers()
                .stream()
                .filter(it -> it.username.contains(user))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("No such user in list"));
    }

    private List<Users> getAllUsers() {
        return setUp()
                .when()
                .get("/users")
                .then().log().ifError().statusCode(200).extract().body().jsonPath().getList("", Users.class);
    }
}
