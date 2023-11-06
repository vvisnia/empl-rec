package com.example.demo.model;


import com.example.demo.util.View;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GithubUser {
    @JsonView(View.Base.class)
    private String id;
    @JsonView(View.Base.class)
    private String login;
    @JsonView(View.Base.class)
    private String name;
    @JsonView(View.Base.class)
    private String type;
    @JsonAlias({"avatar_url", "avatarUrl"})
    @JsonView(View.Base.class)
    private String avatarUrl;
    @JsonAlias({"created_at", "createdAt"})
    @JsonView(View.Base.class)
    private String createdAt;
    private Integer followers;
    @JsonAlias({"public_repos", "publicRepos"})
    private Integer publicRepos;
    @JsonView(View.Base.class)
    private Double calculations;

}
