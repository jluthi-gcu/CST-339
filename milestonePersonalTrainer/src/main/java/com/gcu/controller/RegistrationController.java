package com.gcu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import com.gcu.model.User;

// This annotation marks the class as a Spring MVC Controller.
@Controller
// Specifies the base URL mapping for this controller, setting methods relative to "/register/".
@RequestMapping("/register/")
public class RegistrationController {

	// This annotation indicates that the method handles HTTP GET requests
	// for the root URL of this controller (i.e., "/register/").
	@GetMapping("/")
	// Method to display the registration page.
	public String display(Model model) {
		// Adding attributes to the model for use in the view, such as the title and a
		// new User object.
		model.addAttribute("title", "User Registration");
		model.addAttribute("user", new User());

		// Returns the name of the view to be rendered (maps to "register.html" in the
		// templates directory).
		return "register";
	}

	// This annotation indicates that the method handles HTTP POST requests for the
	// URL "/register/doRegister".
	@PostMapping("/doRegister")
	// Method to handle form submissions.
	public String doRegister(
			// Validate the form input against the User model and bind the results.
			@Valid User user, BindingResult bindingResult, Model model) {

		// Check if there were any validation errors on form submission.
		if (bindingResult.hasErrors()) {
			// If there are errors, return to the registration page to display them.
			return "register";
		}

		// Placeholder for future implementation: Saving the registered user to the
		// database.
		// TODO: Save the user to the database (implement in a later milestone)

		// If registration is successful, redirect the user to the login page.
		return "redirect:/login/";
	}
}