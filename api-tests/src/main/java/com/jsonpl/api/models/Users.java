package com.jsonpl.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Map;

@Getter
@Setter
@ToString
@Accessors(fluent = true)
public class Users {
    @JsonProperty("id")
    public int id;

    @JsonProperty("email")
    public String email;

    @JsonProperty("username")
    public String username;

    @JsonProperty("name")
    public String name;

    @JsonProperty("website")
    public String website;

    @JsonProperty("address")
    public Map address;

    @JsonProperty("phone")
    public String phone;

    @JsonProperty("company")
    public Map company;

}
