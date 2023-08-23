package com.gcu.service;

import org.springframework.stereotype.Service;

import com.gcu.model.LoginModel;
import com.gcu.model.User; // Assuming you have a 'User' model for registration

@Service
public class UserService {

	public boolean authenticate(LoginModel loginModel) {
		// Hardcoded check for the username and password.
		return "admin".equals(loginModel.getUsername()) && "password123".equals(loginModel.getPassword());
	}

	public void register(User user) {
		// log the user registration since we don't have a database setup.
		System.out.println("User registered with username: " + user.getUsername());

	}
}
