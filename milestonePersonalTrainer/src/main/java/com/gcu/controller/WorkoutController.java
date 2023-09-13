package com.gcu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.gcu.model.WorkoutModel;
import com.gcu.service.WorkoutService;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workouts")
public class WorkoutController {

	// A service for fetching and manipulating workouts
	private final WorkoutService workoutService;

	// A map defining ways to sort the workouts
	private static final Map<String, Comparator<WorkoutModel>> SORTING_METHODS = Map.of("name",
			Comparator.comparing(WorkoutModel::getWorkoutName), "duration",
			Comparator.comparingInt(WorkoutModel::getWorkoutDuration), "difficulty",
			Comparator.comparing(WorkoutModel::getWorkoutDifficulty), "type",
			Comparator.comparing(WorkoutModel::getWorkoutType));

	// Constructor-based dependency injection for the workout service
	public WorkoutController(WorkoutService workoutService) {
		this.workoutService = workoutService;
	}

	// Handle the GET requests to the root workout route, with optional sorting
	@GetMapping("/")
	public String displayWorkouts(Model model,
			@RequestParam(value = "sortOption", required = false) String sortOption) {
		// Fetch all workouts
		List<WorkoutModel> workouts = workoutService.getAllWorkouts();
		// Clone the list for sorting
		List<WorkoutModel> sortedWorkouts = new ArrayList<>(workouts);
		// If a valid sorting method is requested, sort the workouts
		if (sortOption != null && SORTING_METHODS.containsKey(sortOption)) {
			sortedWorkouts.sort(SORTING_METHODS.get(sortOption));
		}
		// Add the workouts to the response model
		model.addAttribute("workouts", sortedWorkouts);
		model.addAttribute("title", "Your Workouts");
		return "workouts";
	}

	// Display the form to create a new workout
	@GetMapping("/create")
	public String showCreateForm(Model model) {
		model.addAttribute("workout", new WorkoutModel());
		return "createWorkout";
	}

	// Process the form to create a new workout
	@PostMapping("/create")
	public String doCreateWorkout(@ModelAttribute @Valid WorkoutModel workout, BindingResult bindingResult) {
		// Validate the workout
		if (bindingResult.hasErrors()) {
			return "createWorkout"; // Go back to the form if there are validation errors
		}
		workoutService.addWorkout(workout); // Add the new workout
		return "redirect:/workouts/"; // Redirect to the list of workouts
	}

	// Display the form to edit an existing workout by ID
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
		WorkoutModel workout = workoutService.getWorkoutById(id);
		if (workout == null) {
			// If the workout is not found, send an error message and redirect
			redirectAttributes.addFlashAttribute("errorMessage", "No workout found with ID: " + id);
			return "redirect:/workouts/";
		}
		// Add the workout to the model for editing
		model.addAttribute("workout", workout);
		return "editWorkout";
	}

	// Process the form to edit an existing workout by ID
	@PostMapping("/edit/{id}")
	public String doEditWorkout(@PathVariable Long id, @ModelAttribute @Valid WorkoutModel workout,
			BindingResult bindingResult) {
		// Validate the workout edits
		if (bindingResult.hasErrors()) {
			return "editWorkout"; // Go back to the form if there are validation errors
		}
		workoutService.editWorkout(workout); // Apply the edits
		return "redirect:/workouts/"; // Redirect to the list of workouts
	}

	// Delete a workout by its ID
	@GetMapping("/delete/{id}")
	public String deleteWorkout(@PathVariable Long id) {
		workoutService.deleteWorkout(id);
		return "redirect:/workouts/";
	}
}
