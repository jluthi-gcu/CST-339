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

/**
 * Unit test class for the RegistrationController.
 */
public class RegistrationControllerTest {

	/**
	 * The RegistrationController instance to be tested.
	 */
	@InjectMocks
	private RegistrationController registrationController; // The controller being tested

	/**
	 * Mocked UserService dependency.
	 */
	@Mock
	private UserService userService; // Mocked service dependency

	/**
	 * Mocked Model object used to simulate the interaction with the view layer.
	 */
	@Mock
	private Model model; // Mocked Model object used to pass data to views

	/**
	 * Mocked BindingResult object used to capture validation errors.
	 */
	@Mock
	private BindingResult bindingResult; // Mocked BindingResult object to hold validation errors

	/**
	 * Set up routine that initializes the mocked objects before each test
	 * execution.
	 */
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this); // Initialize mocks before each test
	}

	/**
	 * Test case for the display method in the RegistrationController. Ensures that
	 * the correct view name is returned, and attributes are set on the model.
	 */
	@Test
	public void testDisplay() {
		String result = registrationController.display(model); // Call the method being tested
		assertEquals("register", result); // Ensure that the expected view name is returned

		verify(model, times(1)).addAttribute(eq("user"), any(User.class)); // Verify that the user attribute is added to
																			// the model
	}

	/**
     * Test case for the doRegister method in the RegistrationController when validation errors are present.
     * Ensures that the user is not registered and the "register" view is returned.
     */
	@Test
    public void testDoRegisterWithErrors() {
        when(bindingResult.hasErrors()).thenReturn(true); // Simulate validation errors

        User user = new User(); // Create a user object
        String result = registrationController.doRegister(user, bindingResult, model); // Call the method being tested

        assertEquals("register", result); // Ensure that the expected view name is returned
    }

	/**
     * Test case for the doRegister method in the RegistrationController when the registration is successful.
     * Ensures that the user is registered and redirected to the login page.
     */
	@Test
    public void testDoRegisterSuccessfully() {
        when(bindingResult.hasErrors()).thenReturn(false); // Simulate no validation errors
        when(userService.register(any(User.class))).thenReturn(new User()); // Simulate successful user registration and return a new user

        User user = new User(); // Create a user object
        String result = registrationController.doRegister(user, bindingResult, model); // Call the method being tested

        assertEquals("redirect:/login/", result); // Ensure that the expected redirection is performed
    }
}
