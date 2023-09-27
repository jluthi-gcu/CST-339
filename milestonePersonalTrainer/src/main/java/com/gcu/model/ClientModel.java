package com.gcu.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Represents a client with attributes for ID, name, and email. This model is
 * also equipped with validation annotations for the name and email attributes.
 * 
 * @author Joel Luthi, Alex Frear, Ian Hook
 * @version 1.0
 */
public class ClientModel {

	/** Unique identifier for the client */
	private Long clientId;

	/**
	 * The name of the client. Validation ensures the name is neither null nor
	 * outside the range of 3 to 50 characters.
	 */
	@NotNull(message = "Client name is required.")
	@Size(min = 3, max = 50, message = "Name should be between 3 to 50 characters.")
	private String clientName;

	/**
	 * The email address of the client used for communications. Validation ensures
	 * the email is neither null nor outside the range of 5 to 100 characters.
	 */
	@NotNull(message = "Client email is required.")
	@Size(min = 5, max = 100, message = "Email should be between 5 to 100 characters.")
	private String clientEmail;

	/** Default constructor */
	public ClientModel() {
	}

	/**
	 * Overloaded constructor to initialize client attributes.
	 *
	 * @param clientId   Unique identifier for the client.
	 * @param clientName The name of the client.
	 * @param email      The email address of the client.
	 */
	public ClientModel(Long clientId, String clientName, String email) {
		this.clientId = clientId;
		this.clientName = clientName;
		this.clientEmail = email;
	}

	// --- Getters and Setters ---

	/**
	 * @return The unique identifier of the client.
	 */
	public Long getClientId() {
		return clientId;
	}

	/**
	 * Sets the unique identifier of the client.
	 *
	 * @param clientId The unique identifier to set.
	 */
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return The name of the client.
	 */
	public String getClientName() {
		return clientName;
	}

	/**
	 * Sets the name of the client.
	 *
	 * @param clientName The client name to set.
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	/**
	 * @return The email address of the client.
	 */
	public String getClientEmail() {
		return clientEmail;
	}

	/**
	 * Sets the email address of the client.
	 *
	 * @param clientEmail The email address to set.
	 */
	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}

	/**
	 * Returns a string representation of the ClientModel.
	 *
	 * @return A string in the format "ClientModel [clientId=value,
	 *         clientName=value, email=value]"
	 */
	@Override
	public String toString() {
		return "ClientModel [clientId=" + clientId + ", clientName=" + clientName + ", email=" + clientEmail + "]";
	}
}
