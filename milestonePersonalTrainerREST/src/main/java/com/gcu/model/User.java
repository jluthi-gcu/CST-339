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

/**
 * Represents a User entity with fields for user authentication and
 * identification. This model class also implements the {@link UserDetails}
 * interface which provides methods used by the Spring Security framework.
 * 
 * @author Joel Luthi, Alex Frear, Ian Hook
 * @version 1.0
 */
@SuppressWarnings("serial")
@Table("users")
public class User implements UserDetails {

	/**
	 * Unique identifier for each user. Acts as the primary key.
	 */
	@Id
	private Long id; // primary key

	/**
	 * The user's first name. This field is validated to ensure it is not empty.
	 */
	@NotEmpty(message = "First Name is required.")
	private String firstName;

	/**
	 * The user's last name. This field is validated to ensure it is not empty.
	 */
	@NotEmpty(message = "Last Name is required.")
	private String lastName;

	/**
	 * The user's email address. This field is validated for format and
	 * non-emptiness.
	 */
	@Email(message = "Please provide a valid email address.")
	@NotEmpty(message = "Email is required.")
	private String emailAddress;

	/**
	 * The user's phone number. This field is validated to ensure it is not empty.
	 */
	@NotEmpty(message = "Phone number is required.")
	private String phoneNumber;

	/**
	 * The username of the user. This field is validated for its size and
	 * non-emptiness.
	 */
	@NotEmpty(message = "Username is required.")
	@Size(min = 1, max = 32, message = "Username must be between 1 and 32 characters")
	private String username;

	/**
	 * The user's password. This field is validated for its size and non-emptiness.
	 */
	@NotEmpty(message = "Password is required.")
	@Size(min = 1, max = 32, message = "Password must be between 1 and 32 characters")
	private String password;

	/**
	 * Retrieves the unique identifier for this user.
	 *
	 * @return the ID of this user.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the unique identifier for this user.
	 *
	 * @param id the ID to set for this user.
	 */
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

	/**
	 * This method returns a collection of authorities (roles) granted to the user.
	 * Currently, it returns an empty list, which means the user has no roles.
	 * 
	 * @return an empty list of granted authorities.
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList(); // For simplicity, no roles/authorities are defined.
	}

	/**
	 * Indicates whether the user's account has expired. An expired account cannot
	 * be authenticated.
	 * 
	 * @return true as the accounts in this application are not designed to expire.
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * Indicates whether the user is locked or unlocked. A locked user cannot be
	 * authenticated.
	 * 
	 * @return true as the accounts in this application are not designed to be
	 *         locked.
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * Indicates whether the user's credentials (password) has expired. Expired
	 * credentials prevent authentication.
	 * 
	 * @return true as the credentials in this application are not designed to
	 *         expire.
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * Indicates whether the user is enabled or disabled. A disabled user cannot be
	 * authenticated.
	 * 
	 * @return true as the users in this application are always considered enabled.
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}
}