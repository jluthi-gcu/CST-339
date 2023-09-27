package com.gcu.repository;

import com.gcu.model.WorkoutModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Repository class for performing CRUD operations on {@link WorkoutModel}
 * entities in the database.
 * 
 * @author Joel Luthi, Alex Frear, Ian Hook
 * @version 1.0
 */
@Repository
public class WorkoutRepository {

	/**
	 * Automatically wired bean for JDBC operations.
	 */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Retrieves all workout records from the database.
	 *
	 * @return A list of all workouts from the database.
	 */
	public List<WorkoutModel> findAll() {
		String sql = "SELECT * FROM workouts";
		return jdbcTemplate.query(sql, new WorkoutRowMapper());
	}

	/**
	 * Saves a new workout record to the database.
	 *
	 * @param workout The workout entity to be saved.
	 * @return The saved workout entity.
	 */
	public WorkoutModel save(WorkoutModel workout) {
		String sql = "INSERT INTO workouts (workout_name, workout_description, workout_duration, workout_difficulty, workout_type) VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, workout.getWorkoutName(), workout.getWorkoutDescription(),
				workout.getWorkoutDuration(), workout.getWorkoutDifficulty().name(), workout.getWorkoutType().name());
		return workout;
	}

	/**
	 * Updates an existing workout record in the database.
	 *
	 * @param workout The workout entity to be updated.
	 * @return The updated workout entity.
	 */
	public WorkoutModel update(WorkoutModel workout) {
		String sql = "UPDATE workouts SET workout_name=?, workout_description=?, workout_duration=?, workout_difficulty=?, workout_type=? WHERE workout_id=?";
		jdbcTemplate.update(sql, workout.getWorkoutName(), workout.getWorkoutDescription(),
				workout.getWorkoutDuration(), workout.getWorkoutDifficulty().name(), workout.getWorkoutType().name(),
				workout.getWorkoutId());
		return workout;
	}

	/**
	 * Deletes a workout record from the database using its ID.
	 *
	 * @param workoutId The ID of the workout to be deleted.
	 * @return The number of rows affected by the delete operation.
	 */
	public int delete(Long workoutId) {
		String sql = "DELETE FROM workouts WHERE workout_id=?";
		return jdbcTemplate.update(sql, workoutId);
	}

	/**
	 * Retrieves a specific workout record by its ID.
	 *
	 * @param id The ID of the workout to be retrieved.
	 * @return The workout entity if found; otherwise, null.
	 */
	public WorkoutModel findById(Long id) {
		String sql = "SELECT * FROM workouts WHERE workout_id = ?";
		try {
			return jdbcTemplate.queryForObject(sql, new WorkoutRowMapper(), id);
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			// If the result is empty (no record found), return null
			return null;
		}
	}

	/**
	 * Inner class to define how to map a database row to a {@link WorkoutModel}
	 * object.
	 */
	private class WorkoutRowMapper implements RowMapper<WorkoutModel> {
		/**
		 * Maps a database row to a WorkoutModel object.
		 *
		 * @param rs     The ResultSet containing the row data.
		 * @param rowNum The current row number.
		 * @return A WorkoutModel object corresponding to the row data.
		 * @throws SQLException If any SQL errors occur.
		 */
		@Override
		public WorkoutModel mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new WorkoutModel(rs.getLong("workout_id"), rs.getString("workout_name"),
					rs.getString("workout_description"), rs.getInt("workout_duration"),
					WorkoutModel.Difficulty.fromString(rs.getString("workout_difficulty")),
					WorkoutModel.WorkoutType.fromString(rs.getString("workout_type")));
		}
	}

	/**
	 * Retrieves the last five workout records from the database.
	 *
	 * @return A list of the last five workouts from the database.
	 */
	public List<WorkoutModel> findLastFive() {
		String sql = "SELECT * FROM workouts ORDER BY workout_id DESC LIMIT 5";
		return jdbcTemplate.query(sql, new WorkoutRowMapper());
	}

}
