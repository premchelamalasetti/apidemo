package com.example.demo.api.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.demo.api.model.User;
import com.example.demo.api.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
//@ExtendWith(MockitoExtension.class)
public class HomeControllerTest {
	@MockBean
	private UserService userService;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	private User user;
	private User user2;

	@BeforeEach
	void init() {
		user = new User();
		user.setId(1L);
		user.setName("Prem");
		user.setJob("Trainne");
		user.setMail("prem.pe@gmail.com");
		user.setCreatedDate(new Date(System.currentTimeMillis()));
		user.setCreatedTime(new Time(System.currentTimeMillis()));

		user2 = new User();
		user2.setId(2L);
		user2.setName("Sri");
		user2.setJob("Trainne");
		user2.setMail("sri.pe@gmail.com");
		user2.setCreatedDate(new Date(System.currentTimeMillis()));
		user2.setCreatedTime(new Time(System.currentTimeMillis()));
		userService.create(user2);
	}

	@Test
	@DisplayName("Testing the adding user ")
	void addUserTest() throws Exception
	{
		when(userService.create(any(User.class))).thenReturn(user);
		String 	requestBody= objectMapper.writeValueAsString(user);
		System.out.println("______"+requestBody+"________");
		ResultActions responseContent=mockMvc.perform(post("/api/create")
			    .contentType(MediaType.APPLICATION_JSON)
			    .content(objectMapper.writeValueAsString(user)));
		responseContent.andDo(print()).andExpect(status().isCreated())
		.andExpect(jsonPath("$.name", is(user.getName())))
		.andExpect(jsonPath("$.job",is(user.getJob())))
		.andExpect(jsonPath("$.mail", is(user.getMail())))
		.andExpect(jsonPath("$.createdDate", is(user.getCreatedDate().toString())))
		.andExpect(jsonPath("$.createdTime", is(user.getCreatedTime().toString())));
		System.out.println("------"+responseContent.toString()+"-------");
		}

	@Test
	@DisplayName("Testing get api")
	void getAllUsersTest() throws Exception {
		List<User> list = new ArrayList<>();
		list.add(user);
		list.add(user2);
		System.out.println(list);
		when(userService.findAllUsers()).thenReturn(list);
		this.mockMvc.perform(get("/api/users")).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", is(list.size())));
	}

	@Test
	@DisplayName("Testing get API that return user")
	void getUserByIdTest() throws Exception
	{
		when(userService.findUsersByID(anyLong())).thenReturn(Optional.of(user));	
		this.mockMvc.perform(get("/api/findbyid/{id}",1L)).andExpect(status().isOk())
		.andExpect(jsonPath("$.name",is(user.getName())))
		.andExpect(jsonPath("$.job",is(user.getJob())))
		.andExpect(jsonPath("$.mail",is(user.getMail())))
		.andExpect(jsonPath("$.createdDate",is(user.getCreatedDate().toString())))
		.andExpect(jsonPath("$.createdTime",is(user.getCreatedTime().toString())));
	}

	@Test
	@DisplayName("Testing delete api")
	void deleteUserByIdTest() throws Exception {
		doNothing().when(userService).deleteData(anyLong());
		this.mockMvc.perform(delete("/api/deleteById/{id}", 1L)).andExpect(status().isOk());
	}

	@Test
	@DisplayName("Testing put api")
	void updateUserByIdTest() throws Exception
	{
		when(userService.updateUserDetails(any(User.class))).thenReturn(user);
		this.mockMvc.perform(put("/api/updateById/{id}",1L) .contentType(MediaType.APPLICATION_JSON)
			    .content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isOk())
			    .andExpect(jsonPath("$.name",is(user.getName())))
			    .andExpect(jsonPath("$.job",is(user.getJob())))
			    .andExpect(jsonPath("$.mail",is(user.getMail())))
			 	.andExpect(jsonPath("$.createdDate",is(user.getCreatedDate().toString())))
			    .andExpect(jsonPath("$.createdTime",is(user.getCreatedTime().toString())));
	}
}
