package com.gcu.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Model class for representing a client. This class encapsulates all the
 * properties and behaviors associated with a client.
 */
public class ClientModel {

	private Long clientId;

	// Name of the client; validation constraints ensure it's neither null nor too
	// short/long.
	@NotNull(message = "Client name is required.")
	@Size(min = 3, max = 50, message = "Name should be between 3 to 50 characters.")
	private String clientName;

	// Email of the client for communications.
	@NotNull(message = "Client email is required.")
	@Size(min = 5, max = 100, message = "Email should be between 5 to 100 characters.")
	private String clientEmail;

	/**
	 * Default no-argument constructor.
	 */
	public ClientModel() {
	}

	/**
	 * Full constructor to initialize a ClientModel with all its attributes.
	 *
	 * @param clientId   The unique ID associated with the client
	 * @param clientName The descriptive name of the client
	 * @param email      The email of the client
	 */
	public ClientModel(Long clientId, String clientName, String email) {
		this.clientId = clientId;
		this.clientName = clientName;
		this.clientEmail = email;
	}

	// --- Getters and Setters ---

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientEmail() {
		return clientEmail;
	}

	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}

	@Override
	public String toString() {
		return "ClientModel [clientId=" + clientId + ", clientName=" + clientName + ", email=" + clientEmail + "]";
	}
}
