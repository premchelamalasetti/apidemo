package com.example.demo.api.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestTemplate;

import com.example.demo.api.model.User;
import com.example.demo.api.repo.UserRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserIntegrationTest {

	@LocalServerPort
	private int port;

	private String baseUrl = "http://localhost";

	@Autowired
	private UserRepository userRepository;

	private static RestTemplate restTemplate;

	private User user;
	private User user2;

	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}

	@BeforeEach
	public void beforeSetUp() {
		baseUrl = baseUrl + ":" + port + "/api";
		User user = new User();
		user.setName("Prem");
		user.setJob("Trainne");
		user.setMail("prem.pe@gmail.com");
		user.setCreatedDate(new Date(System.currentTimeMillis()));
		user.setCreatedTime(new Time(System.currentTimeMillis()));
		
		User user2 = new User();
		user2.setName("Sri");
		user2.setJob("Trainne");
		user2.setMail("sri.pe@gmail.com");
		user2.setCreatedDate(new Date(System.currentTimeMillis()));
		user2.setCreatedTime(new Time(System.currentTimeMillis()));
		
		userRepository.save(user);
		userRepository.save(user2);
	}

	@AfterEach
	public void afterSetup() {
		userRepository.deleteAll();
	}

	@Test
	void shouldCreateuserTest() {
		User user = new User();
		user.setId(1L);
		user.setName("Prem");
		user.setJob("Trainne");
		user.setMail("prem.pe@gmail.com");
		user.setCreatedDate(new Date(System.currentTimeMillis()));
		user.setCreatedTime(new Time(System.currentTimeMillis()));
		userRepository.save(user);
		
		User user2 = new User();
		user2.setId(2L);
		user2.setName("Sri");
		user2.setJob("Trainne");
		user2.setMail("sri.pe@gmail.com");
		user2.setCreatedDate(new Date(System.currentTimeMillis()));
		user2.setCreatedTime(new Time(System.currentTimeMillis()));
		userRepository.save(user2);
		User newUser = restTemplate.postForObject(baseUrl + "/create", user, User.class);
		assertNotNull(user);
		assertThat(newUser.getId()).isNotNull();
	}

	@Test
	void shouldReturnUsersTest() {

		List<User> list = restTemplate.getForObject(baseUrl + "/users", List.class);

		assertThat(list.size()).isEqualTo(2);
	}

	@Test
	void shouldFetchOneUser() {

		User existingUser = restTemplate.getForObject(baseUrl + "/findbyid/" + user.getId(), User.class);
		System.out.println("-----"+existingUser+"-----");
		assertNotNull(existingUser);
		assertEquals("Prem", existingUser.getName());
	}

	
}
