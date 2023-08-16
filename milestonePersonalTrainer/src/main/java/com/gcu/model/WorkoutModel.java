package com.gcu.model;

/**
 * Model class for representing a workout. This class encapsulates all the
 * properties and behaviors associated with a workout.
 */
public class WorkoutModel {

	// Unique identifier for each workout, useful especially if persisted in a
	// database
	private Long workoutId;

	// Descriptive name of the workout (e.g. "Cardio Blast")
	private String name;

	// Detailed information about what the workout entails
	private String description;

	// The length of the workout in minutes (e.g. 30 indicates a half-hour workout)
	private int duration;

	// A qualitative measure of the workout's challenge level (e.g. "Easy",
	// "Medium", "Hard")
	private String difficulty;

	/**
	 * Full constructor to initialize a WorkoutModel with all its attributes.
	 *
	 * @param workoutId   The unique ID associated with the workout
	 * @param name        The descriptive name of the workout
	 * @param description A detailed description of the workout
	 * @param duration    The length of the workout in minutes
	 * @param difficulty  The difficulty level of the workout
	 */
	public WorkoutModel(Long workoutId, String name, String description, int duration, String difficulty) {
		this.workoutId = workoutId;
		this.name = name;
		this.description = description;
		this.duration = duration;
		this.difficulty = difficulty;
	}

	// --- Getters and Setters ---

	/**
	 * Retrieves the unique identifier for the workout.
	 * 
	 * @return The ID of the workout.
	 */
	public Long getWorkoutId() {
		return workoutId;
	}

	/**
	 * Sets the unique identifier for the workout.
	 * 
	 * @param workoutId The ID to set for the workout.
	 */
	public void setWorkoutId(Long workoutId) {
		this.workoutId = workoutId;
	}

	/**
	 * Retrieves the name of the workout.
	 * 
	 * @return The name of the workout.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the workout.
	 * 
	 * @param name The name to set for the workout.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Retrieves the detailed description of the workout.
	 * 
	 * @return The description of the workout.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the detailed description for the workout.
	 * 
	 * @param description The description to set for the workout.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Retrieves the duration of the workout in minutes.
	 * 
	 * @return The duration of the workout.
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Sets the duration of the workout in minutes.
	 * 
	 * @param duration The duration to set for the workout.
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * Retrieves the difficulty level of the workout.
	 * 
	 * @return The difficulty of the workout.
	 */
	public String getDifficulty() {
		return difficulty;
	}

	/**
	 * Sets the difficulty level for the workout.
	 * 
	 * @param difficulty The difficulty level to set for the workout.
	 */
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * Provides a string representation of the workout, useful for debugging and
	 * logging.
	 *
	 * @return A string showing the attributes of the workout
	 */
	@Override
	public String toString() {
		return "WorkoutModel [workoutId=" + workoutId + ", name=" + name + ", description=" + description
				+ ", duration=" + duration + ", difficulty=" + difficulty + "]";
	}
}