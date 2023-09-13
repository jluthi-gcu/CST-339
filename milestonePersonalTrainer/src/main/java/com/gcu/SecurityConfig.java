package com.gcu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.gcu.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

	// Autowire (automatically inject) the CustomUserDetailsService bean
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	// Define a bean for the security filter chain which dictates the security
	// configuration
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				// Disable CSRF (Cross Site Request Forgery) protection. Not recommended for
				// production!
				.csrf(csrf -> csrf.disable()).authorizeHttpRequests(authorizeRequests -> authorizeRequests
						// Define URL patterns and their access rights
						.requestMatchers("/", "/images/**", "/login/**", "/register/**").permitAll()
						// Any other request needs to be authenticated
						.anyRequest().authenticated())
				// Configure form login behavior
				.formLogin(formLogin -> formLogin
						// Define the login page URL
						.loginPage("/login/")
						// Define the default success URL after successful login
						.defaultSuccessUrl("/workouts/", true)
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

	// Define a bean for the password encoder. This encoder hashes passwords.
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
