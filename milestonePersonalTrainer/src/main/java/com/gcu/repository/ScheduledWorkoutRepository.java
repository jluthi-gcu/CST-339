package com.gcu.repository;

import com.gcu.model.ScheduledWorkoutModel;
import com.gcu.model.WorkoutModel;
import com.gcu.model.ClientModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Repository class for performing CRUD operations on
 * {@link ScheduledWorkoutModel} entities in the database. It deals with the
 * associations between {@link ScheduledWorkoutModel}, {@link WorkoutModel}, and
 * {@link ClientModel}.
 * 
 * @author Joel Luthi, Alex Frear, Ian Hook
 * @version 1.0
 */
@Repository
public class ScheduledWorkoutRepository {

	/**
	 * Automatically injected JdbcTemplate bean for database interaction.
	 */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Retrieves all scheduled workouts.
	 *
	 * @return List of all scheduled workouts.
	 */
	public List<ScheduledWorkoutModel> findAll() {
		String sql = "SELECT * FROM workout_schedule";
		return jdbcTemplate.query(sql, new ScheduledWorkoutRowMapper());
	}

	/**
	 * Saves a new scheduled workout in the database.
	 *
	 * @param scheduledWorkout The scheduled workout to be saved.
	 * @return The saved scheduled workout.
	 */
	public ScheduledWorkoutModel save(ScheduledWorkoutModel scheduledWorkout) {
		String sql = "INSERT INTO workout_schedule (workoutId, clientId, scheduleDate, startTime, endTime) VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, scheduledWorkout.getWorkout().getWorkoutId(),
				scheduledWorkout.getClient().getClientId(), scheduledWorkout.getScheduleDate(),
				scheduledWorkout.getStartTime(), scheduledWorkout.getEndTime());
		return scheduledWorkout;
	}

	/**
	 * Updates a scheduled workout in the database.
	 *
	 * @param scheduledWorkout The scheduled workout to be updated.
	 * @return The updated scheduled workout.
	 */
	public ScheduledWorkoutModel update(ScheduledWorkoutModel scheduledWorkout) {
		String sql = "UPDATE workout_schedule SET workoutId=?, clientId=?, scheduleDate=?, startTime=?, endTime=? WHERE scheduleId=?";
		jdbcTemplate.update(sql, scheduledWorkout.getWorkout().getWorkoutId(),
				scheduledWorkout.getClient().getClientId(), scheduledWorkout.getScheduleDate(),
				scheduledWorkout.getStartTime(), scheduledWorkout.getEndTime(), scheduledWorkout.getScheduleId());
		return scheduledWorkout;
	}

	/**
	 * Deletes a scheduled workout by its ID.
	 *
	 * @param scheduleId The ID of the scheduled workout to be deleted.
	 * @return The number of rows affected by the delete operation.
	 */
	public int delete(Long scheduleId) {
		String sql = "DELETE FROM workout_schedule WHERE scheduleId=?";
		return jdbcTemplate.update(sql, scheduleId);
	}

	/**
	 * Finds a scheduled workout by its ID.
	 *
	 * @param id The ID of the scheduled workout to find.
	 * @return The found scheduled workout, or null if not found.
	 */
	public ScheduledWorkoutModel findById(Long id) {
		String sql = "SELECT * FROM workout_schedule WHERE scheduleId = ?";
		try {
			return jdbcTemplate.queryForObject(sql, new ScheduledWorkoutRowMapper(), id);
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			return null;
		}
	}

	/**
	 * Maps rows of a ResultSet to instances of {@link ScheduledWorkoutModel}.
	 */
	private class ScheduledWorkoutRowMapper implements RowMapper<ScheduledWorkoutModel> {
		@Override
		public ScheduledWorkoutModel mapRow(ResultSet rs, int rowNum) throws SQLException {
			WorkoutModel workout = new WorkoutModel();
			workout.setWorkoutId(rs.getLong("workoutId"));

			ClientModel client = new ClientModel();
			client.setClientId(rs.getLong("clientId"));

			return new ScheduledWorkoutModel(rs.getLong("scheduleId"), workout, client,
					rs.getDate("scheduleDate").toLocalDate(), rs.getTime("startTime").toLocalTime(),
					rs.getTime("endTime").toLocalTime());
		}
	}

	/**
	 * Finds the next five scheduled workouts in ascending order by schedule date.
	 *
	 * @return List of the next five scheduled workouts.
	 */
	public List<ScheduledWorkoutModel> findNextFiveScheduledWorkouts() {
		String sql = "SELECT * FROM workout_schedule ORDER BY scheduleDate ASC LIMIT 5";
		return jdbcTemplate.query(sql, new ScheduledWorkoutRowMapper());
	}

}
