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

@Controller
@RequestMapping("/register/")
public class RegistrationController {

	// Use Spring's dependency injection to automatically instantiate and assign a
	// UserService object to this field
	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String display(Model model) {
		// Add a new instance of User to the model.
		model.addAttribute("user", new User());

		return "register";
	}

	// Define a method to handle POST requests to "/register/doRegister" URL
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
