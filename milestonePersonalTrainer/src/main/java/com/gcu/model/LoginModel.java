package com.gcu.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Model class representing the login credentials: username and password.
 */
public class LoginModel {

	// Field for username with validation annotations
	// It must not be null and its size should be between 1 and 32 characters.
	@NotNull(message = "User name is a required field")
	@Size(min = 1, max = 32, message = "Username must be between 1 and 32 characters")
	private String username;

	// Field for password with validation annotations
	// It must not be null and its size should be between 1 and 32 characters.
	@NotNull(message = "Password is a required field")
	@Size(min = 1, max = 1000000, message = "Password must be at least 1 character")
	private String password;

	/**
	 * Getter method for the username.
	 *
	 * @return the current username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Setter method for the username.
	 *
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Getter method for the password.
	 *
	 * @return the current password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter method for the password.
	 *
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}