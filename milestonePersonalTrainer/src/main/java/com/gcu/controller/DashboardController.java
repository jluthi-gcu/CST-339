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

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private WorkoutService workoutService;

	@Autowired
	private ClientService clientService;

	@Autowired
	private ScheduledWorkoutService scheduledWorkoutService;

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
