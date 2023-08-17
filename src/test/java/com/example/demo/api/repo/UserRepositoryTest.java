package com.example.demo.api.repo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.api.model.User;

@DataJpaTest
public class UserRepositoryTest {
	@Autowired
	private UserRepository userRepository;
	private User user;
	private User user2;

	@BeforeEach // this will execute for every test case
	// inside this method we can set up the data.
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
	// providing custom names to test cases
	@DisplayName("it should the data to databse")
	void save() {
		User newUser = userRepository.save(user);
		assertThat(newUser);
		assertThat(newUser.getId()).isNotEqualTo(null);
	}

	@Test
	@DisplayName("return all users")
	void findAll() {
		List<User> list = userRepository.findAll();
		assertNotNull(list);
		// assertThat(list).isNotNull();
		assertEquals(2, list.size());
	}

	@Test
	@DisplayName("Returnign users with sri name")
	void getUserByName() {
		List<User> list = userRepository.findByName("Sri");
		assertNotNull(list);
		assertThat(list.size()).isEqualTo(1);
	}

	@Test
	@DisplayName("It should retunr the id")
	void getUserById() {
		User existingUser = userRepository.findById(user.getId()).get();
		assertNotNull(existingUser);
		assertEquals("Prem", existingUser.getName());
	}

	@Test
	@DisplayName("It should update the user with job")
	void updateUser() {
		User existingUser = userRepository.findById(user.getId()).get();
		existingUser.setJob("Manager");
		User newUser = userRepository.save(existingUser);
		assertEquals("Manager", newUser.getJob());
		assertEquals("Prem", newUser.getName());
	}

	@Test
	@DisplayName("Deleting the user Id")
	void deleteUser() {
		Long id = user.getId();
		userRepository.delete(user);
		Optional<User> existingUser = userRepository.findById(id);
		List<User> list = userRepository.findAll();
		assertEquals(1, list.size());
		assertThat(existingUser).isEmpty();
	}
}
