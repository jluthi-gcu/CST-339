package com.gcu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gcu.model.WorkoutModel;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

//This annotation defines this class as a Spring MVC Controller
@Controller
//This annotation indicates that all the routes in this controller will start with "/workouts"
@RequestMapping("/workouts")
public class WorkoutController {

	// In-memory list to store the workout models temporarily.
	private List<WorkoutModel> workoutList = new ArrayList<>();

	// Map to hold sorting methods
	private static final Map<String, Comparator<WorkoutModel>> SORTING_METHODS = Map.of("name",
			Comparator.comparing(WorkoutModel::getWorkoutName), "duration",
			Comparator.comparingInt(WorkoutModel::getWorkoutDuration), "difficulty",
			Comparator.comparing(WorkoutModel::getWorkoutDifficulty), "type",
			Comparator.comparing(WorkoutModel::getWorkoutType));

	@GetMapping("/")
	public String displayWorkouts(Model model,
			@RequestParam(value = "sortOption", required = false) String sortOption) {
		List<WorkoutModel> sortedWorkouts = new ArrayList<>(workoutList);

		// Use the map to fetch and apply the appropriate comparator
		if (sortOption != null && SORTING_METHODS.containsKey(sortOption)) {
			sortedWorkouts.sort(SORTING_METHODS.get(sortOption));
		}

		model.addAttribute("workouts", sortedWorkouts);
		model.addAttribute("title", "Your Workouts");
		return "workouts";
	}

	// This method handles GET requests to "/workouts/create" URL and shows the form
	// to create a new workout
	@GetMapping("/create")
	public String showCreateForm(Model model) {
		// Add an empty workout model to the model. This will be used to capture the
		// user input in the form.
		model.addAttribute("workout", new WorkoutModel());
		// Return the name of the view which contains the form, in this case
		// "createWorkout.html"
		return "createWorkout";
	}

	// This method handles POST requests to "/workouts/create", which is called when
	// the user submits the form to create a new workout
	@PostMapping("/create")
	public String doCreateWorkout(@ModelAttribute @Valid WorkoutModel workout, BindingResult bindingResult) {
		// Check if the submitted workout model has any validation errors
		if (bindingResult.hasErrors()) {
			
			// If there are errors, re-render the form with the submitted data and
			// validation messages
			return "createWorkout";
		}

		// If there are no validation errors, add the new workout to the in-memory list
		workoutList.add(workout);

		// Redirect the user to the list of workouts after successfully creating a new
		// workout
		return "redirect:/workouts/";
	}
}