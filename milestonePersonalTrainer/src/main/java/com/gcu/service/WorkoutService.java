package com.gcu.service;

import com.gcu.model.WorkoutModel;
import com.gcu.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkoutService {

	// Automatically inject an instance of WorkoutRepository
	@Autowired
	private WorkoutRepository workoutRepository;

	// Fetch all workouts from the database
	public List<WorkoutModel> getAllWorkouts() {
		List<WorkoutModel> workouts = new ArrayList<>();
		// Fetch workouts from repository and add to the workouts list
		workoutRepository.findAll().forEach(workouts::add);
		return workouts;
	}

	// Add a new workout to the database
	public WorkoutModel addWorkout(WorkoutModel workout) {
		return workoutRepository.save(workout);
	}

	// Fetch a workout by its ID
	public WorkoutModel getWorkoutById(Long id) {
		return workoutRepository.findById(id);
	}

	// Update details of an existing workout in the database
	public WorkoutModel editWorkout(WorkoutModel workout) {
		return workoutRepository.update(workout);
	}

	// Delete a workout from the database using its ID
	public int deleteWorkout(Long workoutId) {
		return workoutRepository.delete(workoutId);
	}

	// Fetch the last five workouts from the database
	public List<WorkoutModel> getLastFiveWorkouts() {
		return workoutRepository.findLastFive();
	}
}
