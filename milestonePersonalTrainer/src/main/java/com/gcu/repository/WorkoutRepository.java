package com.gcu.repository;

import com.gcu.model.WorkoutModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class WorkoutRepository {

	// Automatically wire in the JdbcTemplate bean for JDBC operations
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// Retrieve all workout records from the database
	public List<WorkoutModel> findAll() {
		String sql = "SELECT * FROM workouts";
		return jdbcTemplate.query(sql, new WorkoutRowMapper());
	}

	// Save a new workout to the database
	public WorkoutModel save(WorkoutModel workout) {
		String sql = "INSERT INTO workouts (workout_name, workout_description, workout_duration, workout_difficulty, workout_type) VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, workout.getWorkoutName(), workout.getWorkoutDescription(),
				workout.getWorkoutDuration(), workout.getWorkoutDifficulty().name(), workout.getWorkoutType().name());
		return workout;
	}

	// Update an existing workout in the database
	public WorkoutModel update(WorkoutModel workout) {
		String sql = "UPDATE workouts SET workout_name=?, workout_description=?, workout_duration=?, workout_difficulty=?, workout_type=? WHERE workout_id=?";
		jdbcTemplate.update(sql, workout.getWorkoutName(), workout.getWorkoutDescription(),
				workout.getWorkoutDuration(), workout.getWorkoutDifficulty().name(), workout.getWorkoutType().name(),
				workout.getWorkoutId());
		return workout;
	}

	// Delete a workout from the database using its ID
	public int delete(Long workoutId) {
		String sql = "DELETE FROM workouts WHERE workout_id=?";
		return jdbcTemplate.update(sql, workoutId);
	}

	// Find a specific workout in the database by its ID
	public WorkoutModel findById(Long id) {
		String sql = "SELECT * FROM workouts WHERE workout_id = ?";
		try {
			return jdbcTemplate.queryForObject(sql, new WorkoutRowMapper(), id);
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			// If the result is empty (no record found), return null
			return null;
		}
	}

	// Define how to map a database row to a WorkoutModel object
	private class WorkoutRowMapper implements RowMapper<WorkoutModel> {
		@Override
		public WorkoutModel mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new WorkoutModel(rs.getLong("workout_id"), rs.getString("workout_name"),
					rs.getString("workout_description"), rs.getInt("workout_duration"),
					WorkoutModel.Difficulty.fromString(rs.getString("workout_difficulty")),
					WorkoutModel.WorkoutType.fromString(rs.getString("workout_type")));
		}
	}

	public List<WorkoutModel> findLastFive() {
		String sql = "SELECT * FROM workouts ORDER BY workout_id DESC LIMIT 5";
		return jdbcTemplate.query(sql, new WorkoutRowMapper());
	}

}