package com.gcu.service;

import com.gcu.model.WorkoutModel;
import com.gcu.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkoutService {

	@Autowired
	private WorkoutRepository workoutRepository;

	public List<WorkoutModel> getAllWorkouts() {
		List<WorkoutModel> workouts = new ArrayList<>();
		workoutRepository.findAll().forEach(workouts::add);
		return workouts;
	}

	public WorkoutModel addWorkout(WorkoutModel workout) {
		return workoutRepository.save(workout);
	}

	public WorkoutModel getWorkoutById(Long id) {
		return workoutRepository.findById(id);
	}

	public WorkoutModel editWorkout(WorkoutModel workout) {
		return workoutRepository.update(workout);
	}

	public int deleteWorkout(Long workoutId) {
		return workoutRepository.delete(workoutId);
	}
}