package com.gcu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.gcu.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	// Automatically wire in the UserRepository bean to fetch user details
	@Autowired
	private UserRepository userRepository;

	// Override the method from UserDetailsService interface
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Fetch user by username using the UserRepository and if not found, throw an
		// exception
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
	}
}
