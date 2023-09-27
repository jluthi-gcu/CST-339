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

/**
 * Controller responsible for managing scheduled workouts. Handles incoming
 * requests related to scheduled workout operations, including displaying,
 * creating, editing, and deleting scheduled workouts.
 * 
 * @author Joel Luthi, Alex Frear, Ian Hook
 * @version 1.0
 */
@Controller
@RequestMapping("/scheduledworkouts")
public class ScheduledWorkoutController {

	/** Service responsible for operations related to scheduled workouts. */
	private final ScheduledWorkoutService scheduledWorkoutService;

	/** Service responsible for operations related to workouts. */
	private final WorkoutService workoutService;

	/** Service responsible for operations related to clients. */
	private final ClientService clientService;

	/**
	 * Constructs a ScheduledWorkoutController with the provided services.
	 *
	 * @param scheduledWorkoutService Service related to scheduled workout
	 *                                operations.
	 * @param workoutService          Service related to workout operations.
	 * @param clientService           Service related to client operations.
	 */
	public ScheduledWorkoutController(ScheduledWorkoutService scheduledWorkoutService, WorkoutService workoutService,
			ClientService clientService) {
		this.scheduledWorkoutService = scheduledWorkoutService;
		this.workoutService = workoutService;
		this.clientService = clientService;
	}

	/**
	 * A static map providing sorting methods for scheduled workouts based on
	 * various attributes. This is used for the display of scheduled workouts in a
	 * sorted order.
	 */
	private static final Map<String, Comparator<ScheduledWorkoutModel>> SORTING_METHODS = Map.of("date",
			Comparator.comparing(ScheduledWorkoutModel::getScheduleDate), "startTime",
			Comparator.comparing(ScheduledWorkoutModel::getStartTime), "endTime",
			Comparator.comparing(ScheduledWorkoutModel::getEndTime), "workoutName",
			Comparator.comparing(s -> s.getWorkout().getWorkoutName()), "clientName",
			Comparator.comparing(s -> s.getClient().getClientName()));

	/**
	 * Displays the list of scheduled workouts.
	 *
	 * @param model      Spring's Model object, used to pass attributes to the view.
	 * @param sortOption Sorting option for the list.
	 * @return The template name for the scheduled workouts list.
	 */
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

	/**
	 * Displays the form to create a new scheduled workout.
	 *
	 * @param model Spring's Model object, used to pass attributes to the view.
	 * @return The template name for the creation form.
	 */
	@GetMapping("/create")
	public String showCreateForm(Model model) {
		model.addAttribute("scheduledWorkout", new ScheduledWorkoutModel());

		List<WorkoutModel> availableWorkouts = workoutService.getAllWorkouts();
		List<ClientModel> availableClients = clientService.getAllClients();

		model.addAttribute("availableWorkouts", availableWorkouts);
		model.addAttribute("availableClients", availableClients);
		return "createScheduledWorkout";
	}

	/**
	 * Processes the creation of a new scheduled workout.
	 *
	 * @param scheduledWorkout The scheduled workout model capturing input from the
	 *                         form.
	 * @param bindingResult    Captures validation errors, if any.
	 * @return Redirects to the scheduled workouts list upon success, or stays in
	 *         the creation form if errors exist.
	 */
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

	/**
	 * Displays the form to edit an existing scheduled workout.
	 *
	 * @param id                 ID of the scheduled workout to edit.
	 * @param model              Spring's Model object, used to pass attributes to
	 *                           the view.
	 * @param redirectAttributes Used to pass attributes between redirects.
	 * @return The template name for the edit form or redirects to the list if the
	 *         ID is invalid.
	 */
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

	/**
	 * Processes the editing of an existing scheduled workout.
	 *
	 * @param id               ID of the scheduled workout to edit.
	 * @param scheduledWorkout The scheduled workout model capturing updated data
	 *                         from the form.
	 * @param bindingResult    Captures validation errors, if any.
	 * @return Redirects to the scheduled workouts list upon success, or stays in
	 *         the edit form if errors exist.
	 */
	@PostMapping("/edit/{id}")
	public String doEditScheduledWorkout(@PathVariable Long id,
			@ModelAttribute @Valid ScheduledWorkoutModel scheduledWorkout, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "editScheduledWorkout";
		}
		scheduledWorkoutService.editScheduledWorkout(scheduledWorkout);
		return "redirect:/scheduledworkouts/";
	}

	/**
	 * Deletes a scheduled workout based on the provided ID.
	 *
	 * @param id ID of the scheduled workout to delete.
	 * @return Redirects to the scheduled workouts list after deletion.
	 */
	@GetMapping("/delete/{id}")
	public String deleteScheduledWorkout(@PathVariable Long id) {
		scheduledWorkoutService.deleteScheduledWorkout(id);
		return "redirect:/scheduledworkouts/";
	}
}
