package com.example.demo.api.service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.api.model.User;
import com.example.demo.api.repo.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User create(User user) {
		user.setCreatedDate(new Date(System.currentTimeMillis()));
		user.setCreatedTime(new Time(System.currentTimeMillis()));
		return userRepository.save(user);
	}

	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	public Optional<User> findUsersByID(Long id) {
		return userRepository.findById(id);
	}

	public void deleteData(Long id) {
		userRepository.deleteById(id);
	}

	public User updateUserDetails(User user) {
		return userRepository.save(user);
	}


}
