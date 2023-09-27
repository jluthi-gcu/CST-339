package com.gcu.model;

import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a scheduled workout for a client. This model provides detailed
 * information regarding the scheduled date and time of a workout, as well as
 * references to the particular workout and client associated with the schedule.
 * 
 * @author Joel Luthi, Alex Frear, Ian Hook
 * @version 1.0
 */
public class ScheduledWorkoutModel {

	/**
	 * Unique identifier for each scheduled workout.
	 */
	private Long scheduleId;

	/**
	 * Reference to the specific workout being scheduled.
	 */
	@NotNull(message = "Workout is required.")
	private WorkoutModel workout;

	/**
	 * Reference to the client for whom the workout is scheduled.
	 */
	@NotNull(message = "Client is required.")
	private ClientModel client;

	/**
	 * Date when the workout is scheduled.
	 */
	@NotNull(message = "Schedule date is required.")
	private LocalDate scheduleDate;

	/**
	 * The start time of the scheduled workout.
	 */
	@NotNull(message = "Start time is required.")
	private LocalTime startTime;

	/**
	 * The end time of the scheduled workout. This is calculated based on the start
	 * time and the duration of the workout.
	 */
	@NotNull(message = "End time is required.")
	private LocalTime endTime;

	/**
	 * Default no-argument constructor.
	 */
	public ScheduledWorkoutModel() {
	}

	/**
	 * Constructor that initializes all the fields of the scheduled workout model.
	 *
	 * @param scheduleId   Unique identifier for the schedule.
	 * @param workout      Reference to the scheduled workout.
	 * @param client       Reference to the client.
	 * @param scheduleDate Date of the scheduled workout.
	 * @param startTime    Start time of the workout.
	 * @param endTime      End time of the workout.
	 */
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

	/**
	 * Gets the unique identifier for the scheduled workout.
	 *
	 * @return The schedule ID.
	 */
	public Long getScheduleId() {
		return scheduleId;
	}

	/**
	 * Sets the unique identifier for the scheduled workout.
	 *
	 * @param scheduleId The schedule ID to set.
	 */
	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

	/**
	 * Gets the scheduled workout details.
	 *
	 * @return The workout details.
	 */
	public WorkoutModel getWorkout() {
		return workout;
	}

	/**
	 * Sets the scheduled workout details.
	 *
	 * @param workout The workout details to set.
	 */
	public void setWorkout(WorkoutModel workout) {
		this.workout = workout;
		updateEndTime();
	}

	/**
	 * Gets the client for whom the workout is scheduled.
	 *
	 * @return The client details.
	 */
	public ClientModel getClient() {
		return client;
	}

	/**
	 * Sets the client for whom the workout is scheduled.
	 *
	 * @param client The client details to set.
	 */
	public void setClient(ClientModel client) {
		this.client = client;
	}

	/**
	 * Gets the date on which the workout is scheduled.
	 *
	 * @return The scheduled date.
	 */
	public LocalDate getScheduleDate() {
		return scheduleDate;
	}

	/**
	 * Sets the date for the scheduled workout.
	 *
	 * @param scheduleDate The date to set.
	 */
	public void setScheduleDate(LocalDate scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	/**
	 * Gets the starting time of the scheduled workout.
	 *
	 * @return The starting time.
	 */
	public LocalTime getStartTime() {
		return startTime;
	}

	/**
	 * Sets the starting time for the scheduled workout and updates the end time
	 * accordingly.
	 *
	 * @param startTime The starting time to set.
	 */
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
		updateEndTime();
	}

	/**
	 * This method updates the end time based on the start time and workout
	 * duration.
	 */
	private void updateEndTime() {
		if (this.startTime != null && this.workout != null) {
			this.endTime = this.startTime.plusMinutes(this.workout.getWorkoutDuration());
		}
	}

	/**
	 * Gets the ending time of the scheduled workout.
	 *
	 * @return The ending time.
	 */
	public LocalTime getEndTime() {
		return endTime;
	}

	/**
	 * Sets the ending time for the scheduled workout.
	 *
	 * @param endTime The ending time to set.
	 */
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	/**
	 * Returns a string representation of the scheduled workout model.
	 *
	 * @return A string containing details of the scheduled workout.
	 */
	@Override
	public String toString() {
		return "ScheduledWorkoutModel [scheduleId=" + scheduleId + ", workout=" + workout.getWorkoutName() + ", client="
				+ client.getClientName() + ", scheduleDate=" + scheduleDate + ", startTime=" + startTime + ", endTime="
				+ endTime + "]";
	}
}
