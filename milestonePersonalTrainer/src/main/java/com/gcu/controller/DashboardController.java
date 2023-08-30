package com.gcu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// Marks the class as a Spring MVC Controller.
@Controller
// Sets the base URL mapping for this controller, so methods will be relative to "/dashboard".
@RequestMapping("/dashboard")
public class DashboardController {

	// Defines an HTTP GET mapping for the root URL of this controller (i.e.,
	// "/dashboard/").
	@GetMapping("/")
	public String display(Model model) {
		// Adds an attribute to the model (to be used in the view) with the key "title"
		// and value "Dashboard".
		model.addAttribute("title", "Dashboard");

		// Returns the name of the view to be rendered. This will map to
		// "dashboard.html" in the templates directory.
		return "dashboard";
	}
}