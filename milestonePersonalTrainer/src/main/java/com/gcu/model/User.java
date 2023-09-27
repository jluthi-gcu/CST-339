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
 * Represents a user within the system, providing details such as name, email,
 * phone number, username, and password. Implements UserDetails for Spring
 * Security integration.
 * 
 * @author Joel Luthi, Alex Frear, Ian Hook
 * @version 1.0
 */
@SuppressWarnings("serial")
@Table("users")
public class User implements UserDetails {

	/** The primary key for the User. */
	@Id
	private Long id; // primary key

	/** The user's first name. */
	@NotEmpty(message = "First Name is required.")
	private String firstName;

	/** The user's last name. */
	@NotEmpty(message = "Last Name is required.")
	private String lastName;

	/** The user's email address. */
	@Email(message = "Please provide a valid email address.")
	@NotEmpty(message = "Email is required.")
	private String emailAddress;

	/** The user's phone number. */
	@NotEmpty(message = "Phone number is required.")
	private String phoneNumber;

	/** The username for the user, used for authentication. */
	@NotEmpty(message = "Username is required.")
	@Size(min = 1, max = 32, message = "Username must be between 1 and 32 characters")
	private String username;

	/** The user's password, used for authentication. */
	@NotEmpty(message = "Password is required.")
	@Size(min = 1, max = 32, message = "Password must be between 1 and 32 characters")
	private String password;

	/**
	 * @return the unique identifier for the user.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the unique identifier to set for the user.
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
	 * Retrieves the authorities granted to the user. For simplicity, this
	 * implementation does not define any roles or authorities.
	 * 
	 * @return a collection of granted authorities for the user.
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList(); // For simplicity, no roles/authorities are defined.
	}

	/**
	 * Checks if the account has not expired.
	 * 
	 * @return true as accounts in this implementation do not expire.
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * Checks if the account is not locked.
	 * 
	 * @return true as accounts in this implementation are not locked.
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * Checks if the credentials have not expired.
	 * 
	 * @return true as credentials in this implementation do not expire.
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * Checks if the user is enabled.
	 * 
	 * @return true as users in this implementation are enabled by default.
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}
}