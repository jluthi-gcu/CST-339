package com.gcu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.gcu.repository.UserRepository;

/**
 * Service class that implements the {@link UserDetailsService} interface for
 * Spring Security. This class is responsible for loading user-specific data
 * during authentication.
 * 
 * @author Joel Luthi, Alex Frear, Ian Hook
 * @version 1.0
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	/**
	 * Repository instance to fetch user details from the database.
	 */
	@Autowired
	private UserRepository userRepository;

	/**
	 * Loads a user entity based on the username. This method overrides the default
	 * behavior provided by Spring Security's {@link UserDetailsService}.
	 * 
	 * @param username the username identifying the user to fetch details for.
	 * @return UserDetails object populated with data from the user entity.
	 * @throws UsernameNotFoundException if the user cannot be found by the given
	 *                                   username.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Fetch user by username using the UserRepository and if not found, throw an
		// exception
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
	}
}
