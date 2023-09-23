package com.gcu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import com.gcu.model.LoginModel;

@Controller
@RequestMapping("/login")
public class LoginController {

	// Define a method to handle GET requests to "/login/" URL
	@GetMapping("/")
	public String display(
			// Capture the "error" query parameter from the URL if it's present.
			@RequestParam(value = "error", required = false) String error,
			// Spring's Model object is used to pass data from controllers to views
			Model model) {

		// Add a "title" attribute to the model, which will be used in the view
		model.addAttribute("title", "Login Form");

		// Add a new instance of LoginModel to the model.
		model.addAttribute("loginModel", new LoginModel());

		// If the "error" query parameter is present in the URL, add an error message to
		// the model
		if (error != null) {
			model.addAttribute("errorMessage", "Invalid username or password.");
		}

		return "login";
	}
}
