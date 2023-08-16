package com.gcu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gcu.model.WorkoutModel;

import java.util.Arrays;
import java.util.List;

// This annotation denotes this class as a Spring Web MVC Controller.
@Controller
// Specifies that this controller will handle requests with the base URL "/workouts".
@RequestMapping("/workouts")
public class WorkoutController {

	// This annotation indicates that this method handles HTTP GET requests to the
	// root URL of this controller ("/workouts/").
	@GetMapping("/")
	public String displayWorkouts(Model model) {

		// Creating a mock list of workouts. This simulates data that might come from a
		// database or service.
		// Each workout has an ID, name, description, duration, and difficulty level.
		List<WorkoutModel> workouts = Arrays.asList(
				new WorkoutModel(1L, "Cardio Blast", "High-intensity cardio workout", 30, "Hard"),
				new WorkoutModel(2L, "Yoga Zen", "Relaxing yoga poses", 60, "Easy"),
				new WorkoutModel(3L, "Strength Training", "Lifting weights and resistance", 45, "Medium"));

		// Adding the list of workouts to the model, making it available to the view.
		model.addAttribute("workouts", workouts);

		// Adding a title attribute to the model, which can be displayed in the view.
		model.addAttribute("title", "Your Workouts");

		// Returns the name of the view to be rendered, this will map to "workouts.html"
		// in the templates directory.
		return "workouts";
	}
}