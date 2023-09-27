package com.gcu.service;

import com.gcu.model.WorkoutModel;
import com.gcu.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for workout-related operations. This class
 * communicates with the {@link WorkoutRepository} to manage
 * {@link WorkoutModel} entities.
 * 
 * @author Joel Luthi, Alex Frear, Ian Hook
 * @version 1.0
 */
@Service
public class WorkoutService {

	/**
	 * Repository instance to interact with workout data in the database.
	 */
	@Autowired
	private WorkoutRepository workoutRepository;

	/**
	 * Retrieves a list of all workouts from the database.
	 * 
	 * @return List of {@link WorkoutModel} entities.
	 */
	public List<WorkoutModel> getAllWorkouts() {
		List<WorkoutModel> workouts = new ArrayList<>();
		// Fetch workouts from repository and add to the workouts list
		workoutRepository.findAll().forEach(workouts::add);
		return workouts;
	}

	/**
	 * Saves a new workout entity to the database.
	 * 
	 * @param workout The {@link WorkoutModel} to be saved.
	 * @return The saved {@link WorkoutModel} entity, typically including any
	 *         generated ID.
	 */
	public WorkoutModel addWorkout(WorkoutModel workout) {
		return workoutRepository.save(workout);
	}

	/**
	 * Retrieves a specific workout by its unique ID.
	 * 
	 * @param id The unique ID of the workout to be retrieved.
	 * @return The found {@link WorkoutModel} entity or null if not found.
	 */
	public WorkoutModel getWorkoutById(Long id) {
		return workoutRepository.findById(id);
	}

	/**
	 * Updates an existing workout in the database.
	 * 
	 * @param workout The {@link WorkoutModel} entity with updated details.
	 * @return The updated {@link WorkoutModel} entity.
	 */
	public WorkoutModel editWorkout(WorkoutModel workout) {
		return workoutRepository.update(workout);
	}

	/**
	 * Deletes a workout from the database based on its unique ID.
	 * 
	 * @param workoutId The unique ID of the workout to be deleted.
	 * @return Number of rows affected by the delete operation.
	 */
	public int deleteWorkout(Long workoutId) {
		return workoutRepository.delete(workoutId);
	}

	/**
	 * Retrieves the last five workouts added to the database.
	 * 
	 * @return List of the last five {@link WorkoutModel} entities.
	 */
	public List<WorkoutModel> getLastFiveWorkouts() {
		return workoutRepository.findLastFive();
	}
}
