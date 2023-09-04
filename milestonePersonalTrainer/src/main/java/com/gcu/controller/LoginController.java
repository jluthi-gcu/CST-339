package com.gcu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.gcu.model.LoginModel;
import com.gcu.service.UserService;

import jakarta.validation.Valid;

// Marks the class as a Spring MVC Controller.
@Controller
// Sets the base URL mapping for this controller, making methods relative to "/login".
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private UserService userService;

	// Defines an HTTP GET mapping for the root URL of this controller (i.e.,
	// "/login/").
	@GetMapping("/")
	public String display(Model model) {
		// Adds attributes to the model for use in the view (like title and a new
		// LoginModel object).
		model.addAttribute("title", "Login Form");
		model.addAttribute("loginModel", new LoginModel());

		// Returns the name of the view to be rendered (maps to "login.html" in the
		// templates directory).
		return "login";
	}

	// Defines an HTTP POST mapping for the URL "/login/doLogin".
	@PostMapping("/doLogin")
	public String doLogin(@Valid @ModelAttribute("loginModel") LoginModel loginModel, BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("title", "Login Form");
			return "login";
		}

		// Use the userService to authenticate the user.
		if (userService.authenticate(loginModel)) {
			return "redirect:/workouts/";
		} else {
			model.addAttribute("title", "Login Form");
			model.addAttribute("errorMessage", "Invalid username or password.");
			return "login";
		}
	}
}