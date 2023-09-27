package com.gcu.controller;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gcu.model.WorkoutModel;
import com.gcu.service.WorkoutService;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Controller responsible for handling CRUD operations on workouts. It
 * communicates with a REST API to fetch and manipulate workout data.
 * 
 * @author Joel Luthi, Alex Frear, Ian Hook
 * @version 1.0
 */
@Controller
@RequestMapping("/workouts")
public class WorkoutController {

	// Constants for API configuration
	/**
	 * The hostname where the API server is running.
	 */
	private static final String API_HOSTNAME = "localhost";

	/**
	 * The port number where the API server is listening.
	 */
	private static final String API_PORT = "8081";

	/**
	 * The username used for authentication to the API server.
	 */
	private static final String API_USERNAME = "afrear";

	/**
	 * The password used for authentication to the API server.
	 */
	private static final String API_PASSWORD = "12345";

	/**
	 * A service object that contains methods for performing CRUD operations on
	 * workout data.
	 */
	private final WorkoutService workoutService;

	/**
	 * A map that defines the different ways in which the workouts can be sorted.
	 * The keys represent the sort option, and the values are the comparators to use
	 * for that sort option.
	 */
	private static final Map<String, Comparator<WorkoutModel>> SORTING_METHODS = Map.of("name",
			Comparator.comparing(WorkoutModel::getWorkoutName), "duration",
			Comparator.comparingInt(WorkoutModel::getWorkoutDuration), "difficulty",
			Comparator.comparing(WorkoutModel::getWorkoutDifficulty), "type",
			Comparator.comparing(WorkoutModel::getWorkoutType));

	/**
	 * Constructor that initializes the WorkoutService.
	 *
	 * @param workoutService The service used for workout operations.
	 */
	public WorkoutController(WorkoutService workoutService) {
		this.workoutService = workoutService;
	}

	/**
	 * Handles the GET requests to the root workout route. Optionally sorts the
	 * workouts based on the provided sort option.
	 *
	 * @param model      Model to bind data to the view.
	 * @param sortOption The option to sort the workouts by.
	 * @return The view name to render.
	 */
	@GetMapping("/")
	public String displayWorkouts(Model model,
			@RequestParam(value = "sortOption", required = false) String sortOption) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(API_USERNAME, API_PASSWORD);

		// Create an HttpEntity with the headers
		HttpEntity<Void> entity = new HttpEntity<>(headers);

		// Get all the Workouts from the REST API
		String url = "http://" + API_HOSTNAME + ":" + API_PORT + "/api/workouts/getAllWorkouts";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<WorkoutModel>> rateResponse = restTemplate.exchange(url, HttpMethod.GET, entity,
				new ParameterizedTypeReference<List<WorkoutModel>>() {
				});
		List<WorkoutModel> workouts = rateResponse.getBody();

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

	/**
	 * Displays the form for creating a new workout.
	 *
	 * @param model Model to bind data to the view.
	 * @return The view name to render.
	 */
	@GetMapping("/create")
	public String showCreateForm(Model model) {
		model.addAttribute("workout", new WorkoutModel());
		return "createWorkout";
	}

	/**
	 * Processes the form submission for creating a new workout.
	 *
	 * @param workout       The workout to be created.
	 * @param bindingResult Validation results for the submitted workout.
	 * @return Redirects to the list of workouts or remains on the form based on
	 *         validation.
	 */
	@PostMapping("/create")
	public String doCreateWorkout(@ModelAttribute @Valid WorkoutModel workout, BindingResult bindingResult) {
		// Validate the workout
		if (bindingResult.hasErrors()) {
			return "createWorkout"; // Go back to the form if there are validation errors
		}
		workoutService.addWorkout(workout); // Add the new workout
		return "redirect:/workouts/"; // Redirect to the list of workouts
	}

	/**
	 * Displays the form for editing an existing workout.
	 *
	 * @param id                 The ID of the workout to edit.
	 * @param model              Model to bind data to the view.
	 * @param redirectAttributes Attributes for redirect scenarios.
	 * @return The view name to render or redirects based on workout availability.
	 */
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(API_USERNAME, API_PASSWORD);

		// Create an HttpEntity with the headers
		HttpEntity<Void> entity = new HttpEntity<>(headers);

		// Get all the Workouts from the REST API
		String url = "http://" + API_HOSTNAME + ":" + API_PORT + "/api/workouts/getWorkoutById/" + id.toString();
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<WorkoutModel> rateResponse = restTemplate.exchange(url, HttpMethod.GET, entity,
				new ParameterizedTypeReference<WorkoutModel>() {
				});
		WorkoutModel workout = rateResponse.getBody();

		if (workout == null) {
			// If the workout is not found, send an error message and redirect
			redirectAttributes.addFlashAttribute("errorMessage", "No workout found with ID: " + id);
			return "redirect:/workouts/";
		}
		// Add the workout to the model for editing
		model.addAttribute("workout", workout);
		return "editWorkout";
	}

	/**
	 * Processes the form submission for editing an existing workout.
	 *
	 * @param id            The ID of the workout to edit.
	 * @param workout       The workout data to be updated.
	 * @param bindingResult Validation results for the edited workout.
	 * @return Redirects to the list of workouts or remains on the form based on
	 *         validation.
	 */
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

	/**
	 * Deletes a workout based on its ID.
	 *
	 * @param id The ID of the workout to delete.
	 * @return Redirects to the list of workouts after deletion.
	 */
	@GetMapping("/delete/{id}")
	public String deleteWorkout(@PathVariable Long id) {
		workoutService.deleteWorkout(id);
		return "redirect:/workouts/";
	}
}
