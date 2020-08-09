package com.jsonpl.api.services;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailDomainService extends BaseApiService {

    public static final Pattern VALID_EMAIL_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    @Step("verify domain for email {email}")
    public String verifyDomainEmail(String email) {
        String val = "http://" + StringUtils.substringAfterLast(email, "@");
        String message = "success";
        try {
            message = String.valueOf(RestAssured.given().when()
                    .get(val).statusCode());

        } catch (Exception ex) {
            message = ex.getMessage();

        }
        return message;
    }

    @Step("verify whether email {email} has correct format")
    public boolean isEmailHasCorrectFormat(String email) {
        Matcher matcher = VALID_EMAIL_REGEX.matcher(email);
        return matcher.find();
    }
}
