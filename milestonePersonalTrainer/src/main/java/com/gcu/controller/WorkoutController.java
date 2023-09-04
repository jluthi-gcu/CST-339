package com.gcu.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

	private final WorkoutService workoutService;

	// Map to hold sorting methods
	private static final Map<String, Comparator<WorkoutModel>> SORTING_METHODS = Map.of("name",
			Comparator.comparing(WorkoutModel::getWorkoutName), "duration",
			Comparator.comparingInt(WorkoutModel::getWorkoutDuration), "difficulty",
			Comparator.comparing(WorkoutModel::getWorkoutDifficulty), "type",
			Comparator.comparing(WorkoutModel::getWorkoutType));

	@Autowired
	public WorkoutController(WorkoutService workoutService) {
		this.workoutService = workoutService;
	}

	@GetMapping("/")
	public String displayWorkouts(Model model,
			@RequestParam(value = "sortOption", required = false) String sortOption) {
		List<WorkoutModel> workouts = workoutService.getAllWorkouts();
		List<WorkoutModel> sortedWorkouts = new ArrayList<>(workouts);

		// Use the map to fetch and apply the appropriate comparator
		if (sortOption != null && SORTING_METHODS.containsKey(sortOption)) {
			sortedWorkouts.sort(SORTING_METHODS.get(sortOption));
		}

		model.addAttribute("workouts", sortedWorkouts);
		model.addAttribute("title", "Your Workouts");
		return "workouts";
	}

	@GetMapping("/create")
	public String showCreateForm(Model model) {
		model.addAttribute("workout", new WorkoutModel());
		return "createWorkout";
	}

	@PostMapping("/create")
	public String doCreateWorkout(@ModelAttribute @Valid WorkoutModel workout, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "createWorkout";
		}

		workoutService.addWorkout(workout);
		return "redirect:/workouts/";
	}

	@GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        WorkoutModel workout = workoutService.getWorkoutById(id);
        if (workout == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "No workout found with ID: " + id);
            return "redirect:/workouts/";
        }
        model.addAttribute("workout", workout);
        return "editWorkout";
    }

	@PostMapping("/edit/{id}")
	public String doEditWorkout(@PathVariable Long id, @ModelAttribute @Valid WorkoutModel workout,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "editWorkout"; // Return to the edit form if there are errors
		}

		workoutService.editWorkout(workout);
		return "redirect:/workouts/";
	}

	@GetMapping("/delete/{id}")
	public String deleteWorkout(@PathVariable Long id) {
		workoutService.deleteWorkout(id);
		return "redirect:/workouts/";
	}
}