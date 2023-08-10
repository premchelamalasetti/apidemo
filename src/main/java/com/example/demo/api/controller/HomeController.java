package com.example.demo.api.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.model.User;
import com.example.demo.api.service.UserService;

@RestController
@ResponseBody
public class HomeController 
{
	private final UserService userService;
	
	

	public HomeController(UserService userService) {
		super();
		this.userService = userService;
	}

	@RequestMapping(value="/api/create",method = RequestMethod.POST)
	public ResponseEntity<String> addUser(User user)
	{
		userService.create(user);	
		return ResponseEntity.ok("User Added");
	}
	@RequestMapping(value="/api/users",method = RequestMethod.GET)
	public ArrayList<User> getAllUsers()
	{
		return userService.findAllUsers();
	}
	@RequestMapping(value="/api/findbyid/{id}",method=RequestMethod.GET)
	public Optional<User> getUserById(@PathVariable Long id)
	{
		return userService.findUsersByID(id);
	}
	@RequestMapping(value="/api/delete{id}",method=RequestMethod.DELETE)
	public String deleteUserById(@PathVariable Long id)
	{
		userService.deleteData(id);
		return "User deleted Successfully";
	}
	@RequestMapping(value="/api/update/{id}",method = RequestMethod.PUT)
	public User updateUser(@RequestBody User user)
	{
		return userService.updateUserDetails(user);
	}
}
