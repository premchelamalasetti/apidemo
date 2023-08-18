package com.example.demo.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.api.model.User;
import com.example.demo.api.repo.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	private User user;
	private User user2;

	@BeforeEach
	void init() {
		user = new User();
		user.setName("Prem");
		user.setJob("Trainne");
		user.setMail("prem.pe@gmail.com");
		user.setCreatedDate(new Date(System.currentTimeMillis()));
		user.setCreatedTime(new Time(System.currentTimeMillis()));
		userRepository.save(user);

		user2 = new User();
		user2.setName("Sri");
		user2.setJob("Trainne");
		user2.setMail("sri.pe@gmail.com");
		user2.setCreatedDate(new Date(System.currentTimeMillis()));
		user2.setCreatedTime(new Time(System.currentTimeMillis()));
		userRepository.save(user2);
	}

	@Test
	@DisplayName("Testing Service Layer Save()method")
    void save() {
		when(userRepository.save(any(User.class))).thenReturn(user);
		
		User newUser=userRepository.save(user);
		
		assertNotNull(newUser);
		assertThat(newUser.getName()).isEqualTo("Prem");
    }

	@Test
	void findAll() {
		List<User> list = userRepository.findAll();
		list.add(user);
		list.add(user2);
		when(userRepository.findAll()).thenReturn(list);
		List<User> users = userService.findAllUsers();
		assertNotNull(list);
		assertEquals(2, list.size());
	}

	@Test
	@DisplayName("Returnign users with sri name") 
	void getUserById()
	{
		user.setId(1L);
	  when(userRepository.findById(anyLong())).thenReturn(Optional.of(user)); 
	  Optional<User> existingUser=userService.findUsersByID(1L); 
	  assertNotNull(existingUser);
	  assertThat(existingUser.get().getId()).isEqualTo(1L);
    }

	@Test
	void updateUsers() {
	    when(userRepository.save(any(User.class))).thenReturn(user);

	    user.setName("Vikas");
	    User newUser = userService.updateUserDetails(user);

	    assertNotNull(newUser);
	    assertEquals("Vikas", newUser.getName());
	}

		@Test
		void deleteUser()
		{
			user.setId(1L); 
			userService.deleteData(1L);
			verify(userRepository, times(1)).deleteById(1L);
			assertNotNull(user);
		}
}