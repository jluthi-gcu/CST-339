package com.gcu.controller;

import com.gcu.model.LoginModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class LoginControllerTest {

	@InjectMocks
	private LoginController loginController; // The controller being tested

	@Mock
	private Model model; // Mocked Model object used to pass data to views

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this); // Initialize mocks before each test
	}

	@Test
	public void testDisplayLoginPageWithoutError() {
		// Act
		String viewName = loginController.display(null, model);

		// Assert
		assertEquals("login", viewName);
		verify(model).addAttribute("title", "Login Form");
		verify(model).addAttribute(eq("loginModel"), any(LoginModel.class));
		verify(model, never()).addAttribute(eq("errorMessage"), anyString());
	}

	@Test
	public void testDisplayLoginPageWithError() {
		// Act
		String viewName = loginController.display("error", model);

		// Assert
		assertEquals("login", viewName);
		verify(model).addAttribute("title", "Login Form");
		verify(model).addAttribute(eq("loginModel"), any(LoginModel.class));
		verify(model).addAttribute("errorMessage", "Invalid username or password.");
	}
}
