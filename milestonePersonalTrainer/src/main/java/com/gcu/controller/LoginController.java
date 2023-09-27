package com.gcu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import com.gcu.model.LoginModel;

/**
 * Controller responsible for managing user login operations. It handles
 * incoming GET requests for the "/login" URL.
 * 
 * @author Joel Luthi, Alex Frear, Ian Hook
 * @version 1.0
 */
@Controller
@RequestMapping("/login")
public class LoginController {

	/**
	 * Displays the login form. This method also captures potential error messages
	 * in case of an unsuccessful login attempt.
	 *
	 * @param error Optional query parameter to indicate whether an error occurred
	 *              during the login process.
	 * @param model Spring's Model object, used to pass attributes to the view.
	 * @return The template name for the login page.
	 */
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
