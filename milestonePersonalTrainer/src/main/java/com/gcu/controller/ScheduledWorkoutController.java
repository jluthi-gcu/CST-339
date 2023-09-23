package com.gcu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.gcu.model.ScheduledWorkoutModel;
import com.gcu.model.WorkoutModel;
import com.gcu.model.ClientModel;
import com.gcu.service.ClientService;
import com.gcu.service.ScheduledWorkoutService;
import com.gcu.service.WorkoutService;

import jakarta.validation.Valid;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/scheduledworkouts")
public class ScheduledWorkoutController {

	private final ScheduledWorkoutService scheduledWorkoutService;
	private final WorkoutService workoutService;
	private final ClientService clientService;

	public ScheduledWorkoutController(ScheduledWorkoutService scheduledWorkoutService, WorkoutService workoutService,
			ClientService clientService) {
		this.scheduledWorkoutService = scheduledWorkoutService;
		this.workoutService = workoutService;
		this.clientService = clientService;
	}

	private static final Map<String, Comparator<ScheduledWorkoutModel>> SORTING_METHODS = Map.of("date",
			Comparator.comparing(ScheduledWorkoutModel::getScheduleDate), "startTime",
			Comparator.comparing(ScheduledWorkoutModel::getStartTime), "endTime",
			Comparator.comparing(ScheduledWorkoutModel::getEndTime), "workoutName",
			Comparator.comparing(s -> s.getWorkout().getWorkoutName()), "clientName",
			Comparator.comparing(s -> s.getClient().getClientName()));

	@GetMapping("/")
	public String displayScheduledWorkouts(Model model,
			@RequestParam(value = "sortOption", required = false) String sortOption) {

		List<ScheduledWorkoutModel> scheduledWorkouts = scheduledWorkoutService.getAllScheduledWorkouts();

		// Fetch full workout and client details for each scheduled workout
		for (ScheduledWorkoutModel scheduledWorkout : scheduledWorkouts) {
			WorkoutModel workout = workoutService.getWorkoutById(scheduledWorkout.getWorkout().getWorkoutId());
			ClientModel client = clientService.getClientById(scheduledWorkout.getClient().getClientId());
			scheduledWorkout.setWorkout(workout);
			scheduledWorkout.setClient(client);
		}

		// If a valid sorting method is requested, sort the scheduledWorkouts
		if (sortOption != null && SORTING_METHODS.containsKey(sortOption)) {
			scheduledWorkouts.sort(SORTING_METHODS.get(sortOption));
		}

		model.addAttribute("scheduledWorkouts", scheduledWorkouts);
		model.addAttribute("title", "Your Scheduled Workouts");
		return "scheduledWorkouts";
	}

	@GetMapping("/create")
	public String showCreateForm(Model model) {
		model.addAttribute("scheduledWorkout", new ScheduledWorkoutModel());

		List<WorkoutModel> availableWorkouts = workoutService.getAllWorkouts();
		List<ClientModel> availableClients = clientService.getAllClients();

		model.addAttribute("availableWorkouts", availableWorkouts);
		model.addAttribute("availableClients", availableClients);
		return "createScheduledWorkout";
	}

	@PostMapping("/create")
	public String doCreateScheduledWorkout(@ModelAttribute @Valid ScheduledWorkoutModel scheduledWorkout,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "createScheduledWorkout";
		}

		// Fetch full client and workout objects based on received IDs.
		ClientModel client = clientService.getClientById(scheduledWorkout.getClient().getClientId());
		WorkoutModel workout = workoutService.getWorkoutById(scheduledWorkout.getWorkout().getWorkoutId());

		// Set the fetched client and workout objects to the scheduledWorkout model.
		scheduledWorkout.setClient(client);
		scheduledWorkout.setWorkout(workout);

		scheduledWorkoutService.scheduleWorkout(scheduledWorkout);
		return "redirect:/scheduledworkouts/";
	}

	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
		ScheduledWorkoutModel scheduledWorkout = scheduledWorkoutService.getScheduledWorkoutById(id);
		if (scheduledWorkout == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "No scheduled workout found with ID: " + id);
			return "redirect:/scheduledworkouts/";
		}
		model.addAttribute("scheduledWorkout", scheduledWorkout);

		// Fetch available workouts and clients for the dropdowns
		List<WorkoutModel> availableWorkouts = workoutService.getAllWorkouts();
		List<ClientModel> availableClients = clientService.getAllClients();

		// Add them to the model
		model.addAttribute("availableWorkouts", availableWorkouts);
		model.addAttribute("availableClients", availableClients);

		System.out.println("Scheduled Workout ID: " + scheduledWorkout.getScheduleId());
		return "editScheduledWorkout";
	}

	@PostMapping("/edit/{id}")
	public String doEditScheduledWorkout(@PathVariable Long id,
			@ModelAttribute @Valid ScheduledWorkoutModel scheduledWorkout, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "editScheduledWorkout";
		}
		scheduledWorkoutService.editScheduledWorkout(scheduledWorkout);
		return "redirect:/scheduledworkouts/";
	}

	@GetMapping("/delete/{id}")
	public String deleteScheduledWorkout(@PathVariable Long id) {
		scheduledWorkoutService.deleteScheduledWorkout(id);
		return "redirect:/scheduledworkouts/";
	}
}
