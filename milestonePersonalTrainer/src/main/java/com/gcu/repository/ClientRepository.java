package com.gcu.repository;

import com.gcu.model.ClientModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClientRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<ClientModel> findAll() {
		String sql = "SELECT * FROM clients";
		return jdbcTemplate.query(sql, new ClientRowMapper());
	}

	public ClientModel save(ClientModel client) {
		String sql = "INSERT INTO clients (clientName, clientEmail) VALUES (?, ?)";
		jdbcTemplate.update(sql, client.getClientName(), client.getClientEmail());
		return client;
	}

	public ClientModel update(ClientModel client) {
		String sql = "UPDATE clients SET clientName=?, clientEmail=? WHERE clientId=?";
		jdbcTemplate.update(sql, client.getClientName(), client.getClientEmail(), client.getClientId());
		return client;
	}

	public int delete(Long clientId) {
		String sql = "DELETE FROM clients WHERE clientId=?";
		return jdbcTemplate.update(sql, clientId);
	}

	public ClientModel findById(Long id) {
		String sql = "SELECT * FROM clients WHERE clientId = ?";
		try {
			return jdbcTemplate.queryForObject(sql, new ClientRowMapper(), id);
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			return null;
		}
	}

	private class ClientRowMapper implements RowMapper<ClientModel> {
		@Override
		public ClientModel mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new ClientModel(rs.getLong("clientId"), rs.getString("clientName"), rs.getString("clientEmail"));
		}
	}

	public List<ClientModel> findLastFiveClients() {
		String sql = "SELECT * FROM clients ORDER BY clientId DESC LIMIT 5";
		return jdbcTemplate.query(sql, new ClientRowMapper());
	}

}
