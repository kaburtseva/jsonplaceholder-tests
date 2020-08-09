package com.jsonpl.api.services;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.http.params.CoreConnectionPNames.CONNECTION_TIMEOUT;
import static org.apache.http.params.CoreConnectionPNames.SO_TIMEOUT;

public class EmailDomainService extends BaseApiService {

    public static final Pattern VALID_EMAIL_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    @Step("verify domain for email {email}")
    public String verifyDomainEmail(String email) {
        //Timeout added since some unreachable domains after long pulling return Runtime error
        RestAssuredConfig config = RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam(CONNECTION_TIMEOUT, 1000)
                        .setParam(SO_TIMEOUT, 1000));
        String val = "http://" + StringUtils.substringAfterLast(email, "@");
        String message = "success";
        try {
            message = String.valueOf(RestAssured.given().config(config).when()
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
