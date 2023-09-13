package com.gcu.service;

import com.gcu.model.ScheduledWorkoutModel;
import com.gcu.repository.ScheduledWorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduledWorkoutService {

	@Autowired
	private ScheduledWorkoutRepository scheduledWorkoutRepository;

	/**
	 * Fetch all scheduled workouts from the repository.
	 * 
	 * @return List of all scheduled workouts.
	 */
	public List<ScheduledWorkoutModel> getAllScheduledWorkouts() {
		List<ScheduledWorkoutModel> scheduledWorkouts = new ArrayList<>();
		scheduledWorkoutRepository.findAll().forEach(scheduledWorkouts::add);
		return scheduledWorkouts;
	}

	/**
	 * Schedule a new workout.
	 * 
	 * @param scheduledWorkout ScheduledWorkoutModel to be saved.
	 * @return Saved ScheduledWorkoutModel.
	 */
	public ScheduledWorkoutModel scheduleWorkout(ScheduledWorkoutModel scheduledWorkout) {
		return scheduledWorkoutRepository.save(scheduledWorkout);
	}

	/**
	 * Fetch a scheduled workout by its ID.
	 * 
	 * @param id ID of the scheduled workout to be fetched.
	 * @return Found ScheduledWorkoutModel or null if not found.
	 */
	public ScheduledWorkoutModel getScheduledWorkoutById(Long id) {
		return scheduledWorkoutRepository.findById(id);
	}

	/**
	 * Update details of an existing scheduled workout in the repository.
	 * 
	 * @param scheduledWorkout ScheduledWorkoutModel to be updated.
	 * @return Updated ScheduledWorkoutModel.
	 */
	public ScheduledWorkoutModel editScheduledWorkout(ScheduledWorkoutModel scheduledWorkout) {
		return scheduledWorkoutRepository.update(scheduledWorkout);
	}

	/**
	 * Delete a scheduled workout by its ID.
	 * 
	 * @param scheduleId ID of the scheduled workout to be deleted.
	 * @return Number of rows affected.
	 */
	public int deleteScheduledWorkout(Long scheduleId) {
		return scheduledWorkoutRepository.delete(scheduleId);
	}

	// Fetch the next five scheduled workouts
	public List<ScheduledWorkoutModel> getNextFiveScheduledWorkouts() {
		return scheduledWorkoutRepository.findNextFiveScheduledWorkouts();
	}
}
