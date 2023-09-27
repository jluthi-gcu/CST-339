package com.gcu.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Represents a workout with attributes such as ID, name, description, duration,
 * difficulty level, and type.
 * 
 * @author Joel Luthi, Alex Frear, Ian Hook
 * @version 1.0
 */
public class WorkoutModel {

	/** Unique ID for the workout. */
	private Long workoutId;

	/** The name of the workout. */
	@NotNull(message = "Workout name is required.")
	@Size(min = 3, max = 50, message = "Name should be between 3 to 50 characters.")
	private String workoutName;

	/** A detailed description of the workout. */
	@NotNull(message = "Workout description is required.")
	@Size(min = 0, max = 500, message = "Description should be between 0 to 500 characters.")
	private String workoutDescription;

	/** The duration of the workout in minutes. */
	@Min(value = 5, message = "Duration should be at least 5 minutes.")
	private int workoutDuration;

	/** The difficulty level of the workout. */
	private Difficulty workoutDifficulty;

	/** The type or category of the workout. */
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

	/**
	 * Retrieves the unique ID of the workout.
	 *
	 * @return the workout ID.
	 */
	public Long getWorkoutId() {
		return workoutId;
	}

	/**
	 * Sets the unique ID for the workout.
	 *
	 * @param workoutId the ID to set.
	 */
	public void setWorkoutId(Long workoutId) {
		this.workoutId = workoutId;
	}

	/**
	 * Retrieves the name of the workout.
	 *
	 * @return the workout name.
	 */
	public String getWorkoutName() {
		return workoutName;
	}

	/**
	 * Sets the name for the workout.
	 *
	 * @param workoutName the name to set.
	 */
	public void setWorkoutName(String workoutName) {
		this.workoutName = workoutName;
	}

	/**
	 * Retrieves the detailed description of the workout.
	 *
	 * @return the workout description.
	 */
	public String getWorkoutDescription() {
		return workoutDescription;
	}

	/**
	 * Sets the description for the workout.
	 *
	 * @param workoutDescription the description to set.
	 */
	public void setWorkoutDescription(String workoutDescription) {
		this.workoutDescription = workoutDescription;
	}

	/**
	 * Retrieves the duration of the workout in minutes.
	 *
	 * @return the workout duration.
	 */
	public int getWorkoutDuration() {
		return workoutDuration;
	}

	/**
	 * Sets the duration for the workout in minutes.
	 *
	 * @param workoutDuration the duration to set.
	 */
	public void setWorkoutDuration(int workoutDuration) {
		this.workoutDuration = workoutDuration;
	}

	/**
	 * Retrieves the difficulty level of the workout.
	 *
	 * @return the workout difficulty.
	 */
	public Difficulty getWorkoutDifficulty() {
		return workoutDifficulty;
	}

	/**
	 * Sets the difficulty level for the workout.
	 *
	 * @param workoutDifficulty the difficulty level to set.
	 */
	public void setWorkoutDifficulty(Difficulty workoutDifficulty) {
		this.workoutDifficulty = workoutDifficulty;
	}

	/**
	 * Retrieves the type or category of the workout.
	 *
	 * @return the workout type.
	 */
	public WorkoutType getWorkoutType() {
		return workoutType;
	}

	/**
	 * Sets the type or category for the workout.
	 *
	 * @param workoutType the type to set.
	 */
	public void setWorkoutType(WorkoutType workoutType) {
		this.workoutType = workoutType;
	}

	/**
	 * Provides a string representation of the WorkoutModel object.
	 *
	 * @return the string representation.
	 */
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
		/**
		 * Represents a beginner level of difficulty.
		 */
		BEGINNER("Beginner"),

		/**
		 * Represents an intermediate level of difficulty.
		 */
		INTERMEDIATE("Intermediate"),

		/**
		 * Represents an advanced level of difficulty.
		 */
		ADVANCED("Advanced");

		private final String label;

		Difficulty(String label) {
			this.label = label;
		}

		/**
		 * Retrieves the descriptive label of the difficulty.
		 *
		 * @return the label of the difficulty.
		 */
		public String getLabel() {
			return this.label;
		}

		/**
		 * Converts a string label into the corresponding {@link Difficulty}. This
		 * conversion is case-insensitive.
		 *
		 * @param label The string label to convert.
		 * @return the matching {@link Difficulty}.
		 * @throws IllegalArgumentException if the label does not match any
		 *                                  {@link Difficulty}.
		 */
		public static Difficulty fromString(String label) {
			for (Difficulty difficulty : Difficulty.values()) {
				if (difficulty.label.equalsIgnoreCase(label)) {
					return difficulty;
				}
			}
			throw new IllegalArgumentException("No Difficulty with label " + label + " found");
		}
	}

	/**
	 * Enum definition for various types of a workout. Each level is associated with
	 * a descriptive label.
	 */
	public enum WorkoutType {
		/**
		 * Represents a cardio-focused workout.
		 */
		CARDIO("Cardio"),

		/**
		 * Represents a strength training-focused workout.
		 */
		STRENGTH("Strength"),

		/**
		 * Represents a flexibility-focused workout.
		 */
		FLEXIBILITY("Flexibility"),

		/**
		 * Represents a balance-focused workout.
		 */
		BALANCE("Balance"),

		/**
		 * Represents an aerobic-focused workout.
		 */
		AEROBIC("Aerobic"),

		/**
		 * Represents a hybrid workout, combining various workout types.
		 */
		HYBRID("Hybrid");

		private final String label;

		/**
		 * Constructs a new {@link WorkoutType} with the provided label.
		 *
		 * @param label The descriptive label for the workout type.
		 */
		WorkoutType(String label) {
			this.label = label;
		}

		/**
		 * Retrieves the descriptive label for the workout type.
		 *
		 * @return the label of the workout type.
		 */
		public String getLabel() {
			return this.label;
		}

		/**
		 * Converts a string label into the corresponding {@link WorkoutType}. This
		 * conversion is case-insensitive.
		 *
		 * @param label The string label to convert.
		 * @return the matching {@link WorkoutType}.
		 * @throws IllegalArgumentException if the label does not match any
		 *                                  {@link WorkoutType}.
		 */
		public static WorkoutType fromString(String label) {
			for (WorkoutType type : WorkoutType.values()) {
				if (type.label.equalsIgnoreCase(label)) {
					return type;
				}
			}
			throw new IllegalArgumentException("No WorkoutType with label " + label + " found");
		}
	}
}