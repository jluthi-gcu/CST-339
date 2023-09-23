package com.gcu.model;

import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.validation.constraints.NotNull;

/**
 * Model class for representing a scheduled workout for a client. This class
 * encapsulates all the properties and behaviors associated with a scheduled
 * workout.
 */
public class ScheduledWorkoutModel {

	// Unique identifier for each scheduled workout
	private Long scheduleId;

	// Reference to the workout being scheduled
	@NotNull(message = "Workout is required.")
	private WorkoutModel workout;

	// Reference to the client for whom the workout is scheduled
	@NotNull(message = "Client is required.")
	private ClientModel client;

	// Date when the workout is scheduled
	@NotNull(message = "Schedule date is required.")
	private LocalDate scheduleDate;

	// Start time for the scheduled workout
	@NotNull(message = "Start time is required.")
	private LocalTime startTime;

	// End time for the scheduled workout
	@NotNull(message = "End time is required.")
	private LocalTime endTime;

	// Default no-argument constructor
	public ScheduledWorkoutModel() {
	}

	// Constructor with all attributes
	public ScheduledWorkoutModel(Long scheduleId, WorkoutModel workout, ClientModel client, LocalDate scheduleDate,
			LocalTime startTime, LocalTime endTime) {
		this.scheduleId = scheduleId;
		this.workout = workout;
		this.client = client;
		this.scheduleDate = scheduleDate;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	// Getters and Setters

	public Long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

	public WorkoutModel getWorkout() {
		return workout;
	}

	public void setWorkout(WorkoutModel workout) {
		this.workout = workout;
		updateEndTime();
	}

	public ClientModel getClient() {
		return client;
	}

	public void setClient(ClientModel client) {
		this.client = client;
	}

	public LocalDate getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(LocalDate scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
		updateEndTime();
	}

	private void updateEndTime() {
		if (this.startTime != null && this.workout != null) {
			this.endTime = this.startTime.plusMinutes(this.workout.getWorkoutDuration());
		}
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "ScheduledWorkoutModel [scheduleId=" + scheduleId + ", workout=" + workout.getWorkoutName() + ", client="
				+ client.getClientName() + ", scheduleDate=" + scheduleDate + ", startTime=" + startTime + ", endTime="
				+ endTime + "]";
	}
}
