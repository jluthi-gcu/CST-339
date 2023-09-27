package com.gcu.repository;

import com.gcu.model.ClientModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Repository class for performing CRUD operations on {@link ClientModel}
 * entities in the database.
 * 
 * @author Joel Luthi, Alex Frear, Ian Hook
 * @version 1.0
 */
@Repository
public class ClientRepository {

	/**
	 * Automatically injects the JdbcTemplate bean to interact with the database.
	 */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Retrieves all clients from the database.
	 *
	 * @return a list of all clients.
	 */
	public List<ClientModel> findAll() {
		String sql = "SELECT * FROM clients";
		return jdbcTemplate.query(sql, new ClientRowMapper());
	}

	/**
	 * Saves a new client to the database.
	 *
	 * @param client the client object to be saved.
	 * @return the saved client object.
	 */
	public ClientModel save(ClientModel client) {
		String sql = "INSERT INTO clients (clientName, clientEmail) VALUES (?, ?)";
		jdbcTemplate.update(sql, client.getClientName(), client.getClientEmail());
		return client;
	}

	/**
	 * Updates the details of an existing client in the database.
	 *
	 * @param client the client object with updated details.
	 * @return the updated client object.
	 */
	public ClientModel update(ClientModel client) {
		String sql = "UPDATE clients SET clientName=?, clientEmail=? WHERE clientId=?";
		jdbcTemplate.update(sql, client.getClientName(), client.getClientEmail(), client.getClientId());
		return client;
	}

	/**
	 * Deletes a client from the database.
	 *
	 * @param clientId the ID of the client to be deleted.
	 * @return the number of rows affected.
	 */
	public int delete(Long clientId) {
		String sql = "DELETE FROM clients WHERE clientId=?";
		return jdbcTemplate.update(sql, clientId);
	}

	/**
	 * Retrieves a specific client from the database by its ID.
	 *
	 * @param id the ID of the client.
	 * @return the client object if found, null otherwise.
	 */
	public ClientModel findById(Long id) {
		String sql = "SELECT * FROM clients WHERE clientId = ?";
		try {
			return jdbcTemplate.queryForObject(sql, new ClientRowMapper(), id);
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			return null;
		}
	}

	/**
	 * Maps a result set row to a client model object.
	 */
	private class ClientRowMapper implements RowMapper<ClientModel> {
		@Override
		public ClientModel mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new ClientModel(rs.getLong("clientId"), rs.getString("clientName"), rs.getString("clientEmail"));
		}
	}

	/**
	 * Retrieves the last five clients added to the database.
	 *
	 * @return a list of the last five clients.
	 */
	public List<ClientModel> findLastFiveClients() {
		String sql = "SELECT * FROM clients ORDER BY clientId DESC LIMIT 5";
		return jdbcTemplate.query(sql, new ClientRowMapper());
	}

}
