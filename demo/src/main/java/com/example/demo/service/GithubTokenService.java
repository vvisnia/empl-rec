package com.example.demo.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GithubTokenService {

    @Value("${github.token}")
    private String githubToken;

    public String getGitHubToken() {
        return githubToken;
    }
}