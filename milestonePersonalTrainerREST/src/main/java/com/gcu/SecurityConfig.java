package com.gcu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.gcu.service.CustomUserDetailsService;

/**
 * Configuration class for setting up security-related beans and configurations.
 * Manages the authentication and authorization for user requests.
 * 
 * @author Joel Luthi, Alex Frear, Ian Hook
 * @version 1.0
 */
@Configuration
public class SecurityConfig {

	/**
	 * Service to provide custom user details for authentication. Used to fetch
	 * user-specific data when processing authentication requests.
	 */
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	/**
	 * Configures and returns the {@link SecurityFilterChain} bean, which dictates
	 * the security configurations and how HTTP requests are intercepted and
	 * handled.
	 * 
	 * @param http The {@link HttpSecurity} object used for configuring request
	 *             matching and defining the security constraints on those requests.
	 * @return the built {@link SecurityFilterChain} after applying the security
	 *         configurations.
	 * @throws Exception if any errors occur during configuration.
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				// Disable CSRF (Cross Site Request Forgery) protection. Not recommended for
				// production!
				.csrf(csrf -> csrf.disable()).authorizeHttpRequests(authorizeRequests -> authorizeRequests
						// Define URL patterns and their access rights
						.requestMatchers("/", "/api/**", "/images/**", "/login/**", "/register/**").permitAll()
						// Any other request needs to be authenticated
						.anyRequest().authenticated())

				// Set the user details service for fetching user details
				.userDetailsService(customUserDetailsService);

		// Return the built HttpSecurity object
		return http.build();
	}

	/**
	 * Provides a bean for password encoding using the BCrypt hashing algorithm.
	 * This encoder is utilized when processing authentication to match hashed
	 * passwords against provided credentials.
	 * 
	 * @return the {@link BCryptPasswordEncoder} used for hashing passwords.
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
