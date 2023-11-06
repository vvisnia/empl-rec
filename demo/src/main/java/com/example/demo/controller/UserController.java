package com.example.demo.controller;


import com.example.demo.model.GithubUser;
import com.example.demo.service.GithubUserService;
import com.example.demo.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final GithubUserService githubUserService;

    public UserController(GithubUserService githubUserService) {
        this.githubUserService = githubUserService;
    }

    @GetMapping("/users/{login}")
    @JsonView(View.Base.class)
    public ResponseEntity<GithubUser> getUserInfo(@PathVariable String login) {
        return githubUserService.getGithubUser(login);
    }
}
