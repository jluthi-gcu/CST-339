package com.gcu.controller;

import com.gcu.model.LoginModel;
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

public class LoginControllerTest {

	@InjectMocks
	private LoginController loginController; // The controller being tested

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
    public void testDoLoginWithErrors() {
        when(bindingResult.hasErrors()).thenReturn(true); // Simulate validation errors
        
        LoginModel loginModel = new LoginModel(); // Create a login model
        String result = loginController.doLogin(loginModel, bindingResult, model); // Call the method being tested

        assertEquals("login", result); // Ensure that the expected view name is returned
    }

	@Test
    public void testDoLoginWithCorrectCredentials() {
        when(bindingResult.hasErrors()).thenReturn(false); // Simulate no validation errors
        when(userService.authenticate(any(LoginModel.class))).thenReturn(true); // Simulate successful authentication
        
        LoginModel loginModel = new LoginModel(); // Create a login model
        String result = loginController.doLogin(loginModel, bindingResult, model); // Call the method being tested

        assertEquals("redirect:/workouts/", result); // Ensure that the expected redirection is performed
    }

	@Test
    public void testDoLoginWithIncorrectCredentials() {
        when(bindingResult.hasErrors()).thenReturn(false); // Simulate no validation errors
        when(userService.authenticate(any(LoginModel.class))).thenReturn(false); // Simulate failed authentication
        
        LoginModel loginModel = new LoginModel(); // Create a login model
        String result = loginController.doLogin(loginModel, bindingResult, model); // Call the method being tested

        assertEquals("login", result); // Ensure that the expected view name is returned
    }
}
