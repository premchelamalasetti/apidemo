package com.example.demo.api.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.api.model.User;
import com.example.demo.api.repo.UserRepository;
import com.example.demo.api.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
public class HomeControllerTest
{
	@MockBean
	private UserService userService;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private UserRepository userRepository;
	
	
	private User user;
	private User user2;
	
	@BeforeEach
	void init()
	{
		user = new User();
		user.setId(1L);
		user.setName("Prem");
		user.setJob("Trainne");
		user.setMail("prem.pe@gmail.com");
		user.setCreatedDate(new Date(System.currentTimeMillis()));
		user.setCreatedTime(new Time(System.currentTimeMillis()));
		userRepository.save(user);
		
		user2 = new User();
		user2.setId(2L);
		user2.setName("Sri");
		user2.setJob("Trainne");
		user2.setMail("sri.pe@gmail.com");
		user2.setCreatedDate(new Date(System.currentTimeMillis()));
		user2.setCreatedTime(new Time(System.currentTimeMillis()));
		userRepository.save(user2);
	}
	@Test
	void addUserTest() throws Exception
	{
		this.mockMvc.perform(post("/api/create"))
		.andExpect(header().string("Content-Type", "application/json"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id",is(user.getId())))
		.andExpect(jsonPath("$.name", is(user.getName())))
		.andExpect(jsonPath("$.job",is(user.getJob())))
		.andExpect(jsonPath("$.mail", is(user.getMail())))
		.andExpect(jsonPath("$.createdDate", is(user.getCreatedDate().toString())))
		.andExpect(jsonPath("$.createdTime", is(user.getCreatedTime().toString())))
		.andReturn();
		}
}
