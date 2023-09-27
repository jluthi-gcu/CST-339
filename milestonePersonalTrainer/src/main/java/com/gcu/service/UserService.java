package com.gcu.service;

import com.gcu.model.LoginModel;
import com.gcu.model.User;
import com.gcu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Service class responsible for user-related operations such as authentication
 * and registration. This class communicates with the {@link UserRepository} for
 * data retrieval and the {@link PasswordEncoder} for password hashing.
 * 
 * @author Joel Luthi, Alex Frear, Ian Hook
 * @version 1.0
 */
@Service
public class UserService {

	/**
	 * Repository instance to interact with user data in the database.
	 */
	@Autowired
	private UserRepository userRepository;

	/**
	 * Component responsible for encoding and matching passwords.
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Authenticates a user based on the credentials provided in the
	 * {@link LoginModel}.
	 * 
	 * @param loginModel Model containing the username and password provided by the
	 *                   user.
	 * @return {@code true} if authentication was successful, {@code false}
	 *         otherwise.
	 */
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

	/**
	 * Registers a new user after hashing their password.
	 * 
	 * @param user The user entity containing the plaintext password to be hashed
	 *             and stored.
	 * @return The saved user entity, typically including any generated ID.
	 */
	public User register(User user) {
		// Hash the plain password from the User model before saving it to the database
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		// Save the user object with the hashed password to the database
		return userRepository.save(user);
	}
}
