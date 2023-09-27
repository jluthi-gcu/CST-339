package com.gcu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.gcu.model.User;
import com.gcu.service.UserService;
import jakarta.validation.Valid;

/**
 * Controller responsible for managing user registration. Handles incoming
 * requests related to the registration process, from displaying the
 * registration form to processing user inputs.
 * 
 * @author Joel Luthi, Alex Frear, Ian Hook
 * @version 1.0
 */
@Controller
@RequestMapping("/register/")
public class RegistrationController {

	/**
	 * Service responsible for handling business logic related to user registration.
	 * Spring's dependency injection mechanism will automatically instantiate and
	 * assign a {@link UserService} object to this field when an instance of this
	 * controller is created.
	 */
	@Autowired
	private UserService userService;

	/**
	 * Displays the registration form to the user.
	 *
	 * @param model Spring's Model object, used to pass attributes to the view.
	 * @return The template name for the registration page.
	 */
	@GetMapping("/")
	public String display(Model model) {
		// Add a new instance of User to the model.
		model.addAttribute("user", new User());

		return "register";
	}

	/**
	 * Processes user input from the registration form. Validates the user input,
	 * registers the user if valid, or returns errors if not.
	 *
	 * @param user          The user object capturing the input from the
	 *                      registration form.
	 * @param bindingResult Captures validation errors if any.
	 * @param model         Spring's Model object, used to pass attributes to the
	 *                      view.
	 * @return Redirects to the login page upon successful registration or the
	 *         registration page with errors if validation fails.
	 */
	@PostMapping("/doRegister")
	public String doRegister(
			// Validate the submitted User object. If there are validation errors, they will
			// be captured by the BindingResult.
			@Valid User user, BindingResult bindingResult, Model model) {

		// Check if there are any validation errors in the submitted User object
		if (bindingResult.hasErrors()) {
			// If there are errors, return to the "register" view to display them
			return "register";
		}

		// Use the injected UserService to register the user
		userService.register(user);

		// Redirect the user to the login page after successful registration
		return "redirect:/login/";
	}
}
