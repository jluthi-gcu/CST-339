package com.gcu.controller;

import com.gcu.model.User;
import com.gcu.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class RegistrationControllerTest {

	@InjectMocks
	private RegistrationController registrationController; // The controller being tested

	@Mock
	private UserService userService; // Mocked service dependency

	@Mock
	private Model model; // Mocked Model object used to pass data to views

	@Mock
	private BindingResult bindingResult; // Mocked BindingResult object to hold validation errors

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this); // Initialize mocks before each test
	}

	@Test
	public void testDisplay() {
		String result = registrationController.display(model); // Call the method being tested
		assertEquals("register", result); // Ensure that the expected view name is returned

		verify(model, times(1)).addAttribute(eq("user"), any(User.class)); // Verify that the user attribute is added to
																			// the model
	}

	@Test
    public void testDoRegisterWithErrors() {
        when(bindingResult.hasErrors()).thenReturn(true); // Simulate validation errors

        User user = new User(); // Create a user object
        String result = registrationController.doRegister(user, bindingResult, model); // Call the method being tested

        assertEquals("register", result); // Ensure that the expected view name is returned
    }

	@Test
    public void testDoRegisterSuccessfully() {
        when(bindingResult.hasErrors()).thenReturn(false); // Simulate no validation errors
        when(userService.register(any(User.class))).thenReturn(new User()); // Simulate successful user registration and return a new user

        User user = new User(); // Create a user object
        String result = registrationController.doRegister(user, bindingResult, model); // Call the method being tested

        assertEquals("redirect:/login/", result); // Ensure that the expected redirection is performed
    }
}
