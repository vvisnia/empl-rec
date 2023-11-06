package com.example.demo;

import com.example.demo.enums.GithubUrl;
import com.example.demo.model.GithubUser;
import com.example.demo.service.GithubTokenService;
import com.example.demo.service.GithubUserService;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class DemoApplicationTests {


	@Mock
	private RestTemplate restTemplate;

	@Mock
	private GithubTokenService githubTokenService;

	@Mock
	private UserService userService;

	private GithubUserService githubUserService;

	@BeforeEach
	public void setUp() {
		restTemplate = mock(RestTemplate.class);
		githubTokenService = mock(GithubTokenService.class);
		userService = mock(UserService.class);
		githubUserService = new GithubUserService(githubTokenService, userService, restTemplate);
	}

	@Test
	public void testGetGithubUser_UnauthorizedError() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer githubToken");
		HttpEntity<String> entity = new HttpEntity<>(headers);

		when(githubTokenService.getGitHubToken()).thenReturn("githubToken");
		when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), eq(entity), eq(GithubUser.class)))
				.thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));


		assertThrows(HttpClientErrorException.class, () -> githubUserService.getGithubUser("testuser"));
	}
	@Test
	public void testGetGithubUser_Success() {

		GithubUser mockUser = new GithubUser();
		mockUser.setLogin("testuser");
		mockUser.setAvatarUrl("test");
		mockUser.setFollowers(10);
		mockUser.setPublicRepos(15);
		mockUser.setId("test");
		mockUser.setName("test");
		mockUser.setCreatedAt("test");

		when(githubTokenService.getGitHubToken()).thenReturn("githubToken");
		when(restTemplate.exchange(
				anyString(),
				any(),
				any(),
				eq(GithubUser.class))
		).thenReturn(new ResponseEntity<>(mockUser, HttpStatus.OK));

		ResponseEntity<GithubUser> response = githubUserService.getGithubUser("testuser");

		assertEquals(response.getBody().getLogin(), ("testuser"));
		assertEquals(response.getBody().getCalculations(), 10.2d);
	}

	@Test
	public void testGetGithubUser_Error() {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer githubToken");

		HttpEntity<String> entity = new HttpEntity<>(headers);

		when(githubTokenService.getGitHubToken()).thenReturn("githubToken");
		when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), eq(entity), eq(GithubUser.class)))
				.thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

		assertThrows(HttpClientErrorException.class, () -> githubUserService.getGithubUser("testuser"));

	}




}


