package com.gcu.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Model class for representing a workout. This class encapsulates all the
 * properties and behaviors associated with a workout.
 */
public class WorkoutModel {

	// Unique identifier for each workout, useful especially if persisted in a
	// database
	private Long workoutId;

	// Name of the workout; validation constraints ensure it's neither null nor too
	// short/long.
	@NotNull(message = "Workout name is required.")
	@Size(min = 3, max = 50, message = "Name should be between 3 to 50 characters.")
	private String workoutName;

	// Description of the workout; validation constraints ensure it's neither null
	// nor too short/long.
	@NotNull(message = "Workout description is required.")
	@Size(min = 0, max = 500, message = "Description should be between 0 to 500 characters.")
	private String workoutDescription;

	// Duration of the workout in minutes; it should be at least 5 minutes.
	@Min(value = 5, message = "Duration should be at least 5 minutes.")
	private int workoutDuration;

	// The difficulty level of the workout as defined by the Difficulty enum.
	private Difficulty workoutDifficulty;

	// The type of the workout as defined by the WorkoutType enum.
	private WorkoutType workoutType;

	/**
	 * Default no-argument constructor.
	 */
	public WorkoutModel() {
	}

	/**
	 * Full constructor to initialize a WorkoutModel with all its attributes.
	 *
	 * @param workoutId          The unique ID associated with the workout
	 * @param workoutName        The descriptive name of the workout
	 * @param workoutDescription A detailed description of the workout
	 * @param workoutDuration    The length of the workout in minutes
	 * @param workoutDifficulty  The difficulty level of the workout
	 * @param workoutType        The type of workout
	 */
	public WorkoutModel(Long workoutId, String workoutName, String workoutDescription, int workoutDuration,
			Difficulty workoutDifficulty, WorkoutType workoutType) {
		this.workoutId = workoutId;
		this.workoutName = workoutName;
		this.workoutDescription = workoutDescription;
		this.workoutDuration = workoutDuration;
		this.workoutDifficulty = workoutDifficulty;
		this.workoutType = workoutType;
	}

	// --- Getters and Setters ---

	// Standard getter and setter methods for the workoutId attribute
	public Long getWorkoutId() {
		return workoutId;
	}

	public void setWorkoutId(Long workoutId) {
		this.workoutId = workoutId;
	}

	// Standard getter and setter methods for the workoutName attribute
	public String getWorkoutName() {
		return workoutName;
	}

	public void setWorkoutName(String workoutName) {
		this.workoutName = workoutName;
	}

	// Standard getter and setter methods for the workoutDescription attribute
	public String getWorkoutDescription() {
		return workoutDescription;
	}

	public void setWorkoutDescription(String workoutDescription) {
		this.workoutDescription = workoutDescription;
	}

	// Standard getter and setter methods for the workoutDuration attribute
	public int getWorkoutDuration() {
		return workoutDuration;
	}

	public void setWorkoutDuration(int workoutDuration) {
		this.workoutDuration = workoutDuration;
	}

	// Standard getter and setter methods for the workoutDifficulty attribute
	public Difficulty getWorkoutDifficulty() {
		return workoutDifficulty;
	}

	public void setWorkoutDifficulty(Difficulty workoutDifficulty) {
		this.workoutDifficulty = workoutDifficulty;
	}

	// Standard getter and setter methods for the workoutType attribute
	public WorkoutType getWorkoutType() {
		return workoutType;
	}

	public void setWorkoutType(WorkoutType workoutType) {
		this.workoutType = workoutType;
	}

	// Overrides the default toString method to provide a custom string
	// representation of the object
	@Override
	public String toString() {
		return "WorkoutModel [workoutId=" + workoutId + ", workoutName=" + workoutName + ", workoutDescription="
				+ workoutDescription + ", workoutDuration=" + workoutDuration + ", workoutDifficulty="
				+ workoutDifficulty.getLabel() + ", workoutType=" + workoutType.getLabel() + "]";
	}

	/**
	 * Enum definition for various difficulty levels of a workout. Each level is
	 * associated with a descriptive label.
	 */
	public enum Difficulty {
		BEGINNER("Beginner"), INTERMEDIATE("Intermediate"), ADVANCED("Advanced");

		private final String label;

		Difficulty(String label) {
			this.label = label;
		}

		// Getter method to fetch the descriptive label of a difficulty level
		public String getLabel() {
			return this.label;
		}
	}

	/**
	 * Enum definition for various types of a workout.
	 */
	public enum WorkoutType {
		CARDIO("Cardio"), STRENGTH("Strength"), FLEXIBILITY("Flexibility"), BALANCE("Balance"), AEROBIC("Aerobic"),
		HYBRID("Hybrid");

		private final String label;

		WorkoutType(String label) {
			this.label = label;
		}

		// Getter method to fetch the descriptive label of a workout type
		public String getLabel() {
			return this.label;
		}
	}
}