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

/**
 * Unit test class for the LoginController.
 */
public class LoginControllerTest {

	/**
	 * The LoginController instance to be tested.
	 */
	@InjectMocks
	private LoginController loginController; // The controller being tested

	/**
	 * Mocked Model object used to simulate the interaction with the view layer.
	 */
	@Mock
	private Model model; // Mocked Model object used to pass data to views

	/**
	 * Set up routine that initializes the mocked objects before each test
	 * execution.
	 */
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this); // Initialize mocks before each test
	}

	/**
	 * Test case for the display method in the LoginController when no error is
	 * present. Ensures that the correct view name is returned, and attributes are
	 * set on the model.
	 */
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

	/**
	 * Test case for the display method in the LoginController when an error is
	 * present. Ensures that the correct view name is returned, and attributes,
	 * including the error message, are set on the model.
	 */
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
