package com.gcu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.gcu.service.CustomUserDetailsService;

/**
 * This class provides the configuration for the application's security. It
 * defines beans and settings related to authentication, authorization, and
 * password encoding.
 * 
 * @author Joel Luthi, Alex Frear, Ian Hook
 * @version 1.0
 */
@Configuration
public class SecurityConfig {

	/**
	 * The {@link CustomUserDetailsService} instance responsible for fetching user
	 * details.
	 */
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	/**
	 * Defines the security filter chain for the application.
	 * 
	 * @param http The HttpSecurity object to be configured.
	 * @return The SecurityFilterChain object built from the HttpSecurity
	 *         configuration.
	 * @throws Exception If an error occurs during configuration.
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
				// Configure form login behavior
				.formLogin(formLogin -> formLogin
						// Define the login page URL
						.loginPage("/login/")
						// Define the default success URL after successful login
						.defaultSuccessUrl("/dashboard/", true)
						// Define the failure URL if login fails
						.failureUrl("/login/?error=true")
						// Define the URL where the login POST request should be sent
						.loginProcessingUrl("/login/"))
				// Configure logout behavior
				.logout(logout -> logout
						// Define the URL to redirect to after successful logout
						.logoutSuccessUrl("/login/"))
				// Set the user details service for fetching user details
				.userDetailsService(customUserDetailsService);

		// Return the built HttpSecurity object
		return http.build();
	}

	/**
	 * Provides a password encoder bean. The encoder is responsible for hashing
	 * passwords using the BCrypt hashing algorithm.
	 * 
	 * @return A {@link BCryptPasswordEncoder} object.
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
