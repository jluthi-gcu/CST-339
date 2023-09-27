package com.gcu.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gcu.model.WorkoutModel;
import com.gcu.service.WorkoutService;

/**
 * A REST controller responsible for handling requests related to workouts.
 * Provides endpoints to retrieve all workouts and get a specific workout by its
 * ID.
 * 
 * @author Joel Luthi, Alex Frear, Ian Hook
 * @version 1.0
 */
@RestController
@RequestMapping("/api/workouts")
public class WorkoutsRestService {

	/**
	 * The service used to interact with {@link WorkoutModel} data.
	 */
	@Autowired
	WorkoutService service;

	/**
	 * Endpoint to retrieve all workouts.
	 * 
	 * @return a list of all {@link WorkoutModel} workouts, or an appropriate HTTP
	 *         status if an error occurs.
	 */
	@GetMapping(path = "/getAllWorkouts")
	public ResponseEntity<?> getAllWorkouts() {
		try {

			List<WorkoutModel> workouts = service.getAllWorkouts();
			if (workouts == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			else
				return new ResponseEntity<>(workouts, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Endpoint to retrieve a specific workout by its ID.
	 * 
	 * @param id the ID of the workout to retrieve
	 * @return the specified {@link WorkoutModel} workout, or an appropriate HTTP
	 *         status if an error occurs.
	 */
	@GetMapping("/getWorkoutById/{id}")
	public ResponseEntity<?> getWorkoutById(@PathVariable Long id) {
		try {

			WorkoutModel workout = service.getWorkoutById(id);
			if (workout == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			else
				return new ResponseEntity<>(workout, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}