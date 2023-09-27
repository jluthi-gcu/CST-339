package com.gcu.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Represents a model for user login information. This model contains fields for
 * the username and password, both with validation constraints. The username and
 * password fields are mandatory, with specified character limits.
 * 
 * @author Joel Luthi, Alex Frear, Ian Hook
 * @version 1.0
 */
public class LoginModel {

	/**
	 * The username of the user. It is mandatory and must be between 1 and 32
	 * characters.
	 */
	@NotNull(message = "User name is a required field")
	@Size(min = 1, max = 32, message = "Username must be between 1 and 32 characters")
	private String username;

	/**
	 * The password of the user. It is mandatory and must be at least 1 character
	 * (with a very large upper limit).
	 */
	@NotNull(message = "Password is a required field")
	@Size(min = 1, max = 1000000, message = "Password must be at least 1 character")
	private String password;

	/**
	 * Retrieves the current username value.
	 *
	 * @return the current username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Updates the username value.
	 *
	 * @param username the new username to set.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Retrieves the current password value.
	 *
	 * @return the current password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Updates the password value.
	 *
	 * @param password the new password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}