package com.gcu.service;

import com.gcu.model.LoginModel;
import com.gcu.model.User;
import com.gcu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

	// Automatically inject an instance of UserRepository
	@Autowired
	private UserRepository userRepository;

	// Automatically inject an instance of PasswordEncoder (used for hashing
	// passwords)
	@Autowired
	private PasswordEncoder passwordEncoder;

	// Method to authenticate a user using the provided LoginModel
	public boolean authenticate(LoginModel loginModel) {
		// Fetch user by username
		Optional<User> userOpt = userRepository.findByUsername(loginModel.getUsername());

		// Check if the user was not found in the database
		if (!userOpt.isPresent()) {
			System.out.println("Authentication failed - No user found with username: " + loginModel.getUsername());
			return false;
		}

		// Retrieve user details if present
		User user = userOpt.get();

		// Check if the password in the LoginModel matches the hashed password in the
		// database
		boolean passwordMatches = passwordEncoder.matches(loginModel.getPassword(), user.getPassword());

		if (!passwordMatches) {
			System.out.println(
					"Authentication failed for username: " + loginModel.getUsername() + " - Incorrect password");
		} else {
			System.out.println("Authentication successful for username: " + loginModel.getUsername());
		}

		return passwordMatches;
	}

	// Method to register a new user
	public User register(User user) {
		// Hash the plain password from the User model before saving it to the database
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		// Save the user object with the hashed password to the database
		return userRepository.save(user);
	}
}
