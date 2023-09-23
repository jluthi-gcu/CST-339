package com.gcu.model;

import jakarta.validation.constraints.NotEmpty;

import java.util.Collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@SuppressWarnings("serial")
@Table("users")
public class User implements UserDetails {

	@Id
	private Long id; // primary key

	// Field for user's first name with validation to ensure it's not empty.
	@NotEmpty(message = "First Name is required.")
	private String firstName;

	// Field for user's last name with validation to ensure it's not empty.
	@NotEmpty(message = "Last Name is required.")
	private String lastName;

	// Field for user's email address with validations for format and non-emptiness.
	@Email(message = "Please provide a valid email address.")
	@NotEmpty(message = "Email is required.")
	private String emailAddress;

	// Field for user's phone number with validation to ensure it's not empty.
	@NotEmpty(message = "Phone number is required.")
	private String phoneNumber;

	// Field for username with validations for size and non-emptiness.
	@NotEmpty(message = "Username is required.")
	@Size(min = 1, max = 32, message = "Username must be between 1 and 32 characters")
	private String username;

	// Field for password with validations for size and non-emptiness.
	@NotEmpty(message = "Password is required.")
	@Size(min = 1, max = 32, message = "Password must be between 1 and 32 characters")
	private String password;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter method for the first name.
	 *
	 * @return the current first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter method for the first name.
	 *
	 * @param firstName the first name to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter method for the last name.
	 *
	 * @return the current last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter method for the last name.
	 *
	 * @param lastName the last name to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter method for the email address.
	 *
	 * @return the current email address
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Setter method for the email address.
	 *
	 * @param emailAddress the email address to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * Getter method for the phone number.
	 *
	 * @return the current phone number
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Setter method for the phone number.
	 *
	 * @param phoneNumber the phone number to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList(); // For simplicity, no roles/authorities are defined.
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}