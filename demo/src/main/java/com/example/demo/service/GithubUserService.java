package com.example.demo.service;


import com.example.demo.enums.GithubUrl;
import com.example.demo.model.GithubUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;



@Service
public class GithubUserService {

    private final RestTemplate restTemplate;

    private final GithubTokenService githubTokenService;

    private final UserService userService;

    private static final Logger logger = LogManager.getLogger(GithubUserService.class);

    public GithubUserService(GithubTokenService githubTokenService, UserService userService, RestTemplate restTemplate) {
        this.githubTokenService = githubTokenService;
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<GithubUser> getGithubUser(String login) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + githubTokenService.getGitHubToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            var user = restTemplate.exchange(GithubUrl.USER_URL + login, HttpMethod.GET, entity, GithubUser.class).getBody();
            user.setCalculations(calculateCalculations(user.getFollowers(), user.getPublicRepos()));
            logger.info("Data fetch for " + login + " successful");
            try {
                saveUserData(login);
            } catch (Exception e) {
              logger.info("Unable to save User " + e);
            }

            return ResponseEntity.ok(user);
        } catch (HttpClientErrorException e) {
            throw e;
        }
    }

    private Double calculateCalculations(Integer followers, Integer publicRepos) {
        return ((double) 6 / ((double) followers)) * ((double) 2 + (double) publicRepos);
    }

    private synchronized void saveUserData(String login) {
        var savedData = userService.saveOrUpdateUserData(login);
        logger.info("User " + savedData.getLogin() + " updated, new count is " + savedData.getRequestCount());
    }

}
