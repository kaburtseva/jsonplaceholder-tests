package com.jsonpl.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(fluent = true)
public class Posts {
    @JsonProperty("userId")
    int userId;

    @JsonProperty("id")
    public int id;

    @JsonProperty("title")
    String title;

    @JsonProperty("body")
    String body;
}
