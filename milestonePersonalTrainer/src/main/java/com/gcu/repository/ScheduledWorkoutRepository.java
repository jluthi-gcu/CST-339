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

@Repository
public class ScheduledWorkoutRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<ScheduledWorkoutModel> findAll() {
		String sql = "SELECT * FROM workout_schedule";
		return jdbcTemplate.query(sql, new ScheduledWorkoutRowMapper());
	}

	public ScheduledWorkoutModel save(ScheduledWorkoutModel scheduledWorkout) {
		String sql = "INSERT INTO workout_schedule (workoutId, clientId, scheduleDate, startTime, endTime) VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, scheduledWorkout.getWorkout().getWorkoutId(),
				scheduledWorkout.getClient().getClientId(), scheduledWorkout.getScheduleDate(),
				scheduledWorkout.getStartTime(), scheduledWorkout.getEndTime());
		return scheduledWorkout;
	}

	public ScheduledWorkoutModel update(ScheduledWorkoutModel scheduledWorkout) {
		String sql = "UPDATE workout_schedule SET workoutId=?, clientId=?, scheduleDate=?, startTime=?, endTime=? WHERE scheduleId=?";
		jdbcTemplate.update(sql, scheduledWorkout.getWorkout().getWorkoutId(),
				scheduledWorkout.getClient().getClientId(), scheduledWorkout.getScheduleDate(),
				scheduledWorkout.getStartTime(), scheduledWorkout.getEndTime(), scheduledWorkout.getScheduleId());
		return scheduledWorkout;
	}

	public int delete(Long scheduleId) {
		String sql = "DELETE FROM workout_schedule WHERE scheduleId=?";
		return jdbcTemplate.update(sql, scheduleId);
	}

	public ScheduledWorkoutModel findById(Long id) {
		String sql = "SELECT * FROM workout_schedule WHERE scheduleId = ?";
		try {
			return jdbcTemplate.queryForObject(sql, new ScheduledWorkoutRowMapper(), id);
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			return null;
		}
	}

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

	public List<ScheduledWorkoutModel> findNextFiveScheduledWorkouts() {
		String sql = "SELECT * FROM workout_schedule ORDER BY scheduleDate ASC LIMIT 5";
		return jdbcTemplate.query(sql, new ScheduledWorkoutRowMapper());
	}

}
