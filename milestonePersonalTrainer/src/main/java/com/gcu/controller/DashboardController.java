package com.gcu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gcu.service.WorkoutService;
import com.gcu.model.ClientModel;
import com.gcu.model.ScheduledWorkoutModel;
import com.gcu.model.WorkoutModel;
import com.gcu.service.ClientService;
import com.gcu.service.ScheduledWorkoutService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Controller responsible for displaying the user's dashboard which provides a
 * quick glance of recent workouts, clients, and upcoming scheduled workouts.
 * 
 * @author Joel Luthi, Alex Frear, Ian Hook
 * @version 1.0
 */
@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	/**
	 * Service responsible for handling business logic related to workouts. It's
	 * automatically injected by Spring Framework when an instance of this
	 * controller is created.
	 * 
	 */
	@Autowired
	private WorkoutService workoutService;

	/**
	 * Service responsible for handling business logic related to clients. It's
	 * automatically injected by Spring Framework when an instance of this
	 * controller is created.
	 * 
	 */
	@Autowired
	private ClientService clientService;

	/**
	 * Service responsible for handling business logic related to scheduled
	 * workouts. It's automatically injected by Spring Framework when an instance of
	 * this controller is created.
	 * 
	 */
	@Autowired
	private ScheduledWorkoutService scheduledWorkoutService;

	/**
	 * Fetches and displays relevant data for the user's dashboard, including recent
	 * workouts, clients, and upcoming scheduled workouts.
	 *
	 * @param model The model used to pass attributes to the view.
	 * @return The template name for displaying the dashboard.
	 */
	@GetMapping("/")
	public String showDashboard(Model model) {
		// Fetch the last 5 workouts and clients
		List<WorkoutModel> recentWorkouts = workoutService.getLastFiveWorkouts();
		List<ClientModel> recentClients = clientService.getLastFiveClients();

		// Fetch the next 5 upcoming scheduled workouts
		List<ScheduledWorkoutModel> upcomingScheduledWorkouts = scheduledWorkoutService.getNextFiveScheduledWorkouts();

		// Fetch full workout and client details for each upcoming scheduled workout
		for (ScheduledWorkoutModel scheduledWorkout : upcomingScheduledWorkouts) {
			WorkoutModel workout = workoutService.getWorkoutById(scheduledWorkout.getWorkout().getWorkoutId());
			ClientModel client = clientService.getClientById(scheduledWorkout.getClient().getClientId());

			scheduledWorkout.setWorkout(workout);
			scheduledWorkout.setClient(client);
		}

		// Add all the fetched data to the model
		model.addAttribute("recentWorkouts", recentWorkouts);
		model.addAttribute("recentClients", recentClients);
		model.addAttribute("upcomingScheduledWorkouts", upcomingScheduledWorkouts);

		model.addAttribute("title", "Your Dashboard");

		return "dashboard";
	}

}
