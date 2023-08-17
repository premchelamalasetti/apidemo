package com.example.demo.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.model.User;
import com.example.demo.api.service.UserService;

@RestController
@RequestMapping("/api")
public class HomeController {
	private final UserService userService;

	public HomeController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping(value = "/create")
	public User addUser(User user) {
		return userService.create(user);
		//return "User added Successfully";
	}

	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userService.findAllUsers();
	}

	@GetMapping("/findbyid/{id}")
	public Optional<User> getUserById(@PathVariable Long id) {
		return userService.findUsersByID(id);
	}

	@DeleteMapping("/deleteById/{id}")
	public String deleteUserById(@PathVariable Long id) {
		userService.deleteData(id);
		return "User deleted Successfully";
	}

	@PutMapping("/updateById/{id}")
	public User updateUser(@RequestBody User user) {
		return userService.updateUserDetails(user);
	}
}
