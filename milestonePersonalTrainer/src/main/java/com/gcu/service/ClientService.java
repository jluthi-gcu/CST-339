package com.gcu.service;

import com.gcu.model.ClientModel;
import com.gcu.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for managing {@link ClientModel} entities. This
 * class interacts with the {@link ClientRepository} to perform CRUD operations
 * on clients.
 * 
 * @author Joel Luthi, Alex Frear, Ian Hook
 * @version 1.0
 */
@Service
public class ClientService {

	/**
	 * Repository instance for client operations.
	 */
	@Autowired
	private ClientRepository clientRepository;

	/**
	 * Fetch all client entities from the database.
	 *
	 * @return List of all clients.
	 */
	public List<ClientModel> getAllClients() {
		List<ClientModel> clients = new ArrayList<>();
		// Fetch clients from repository and add to the clients list
		clientRepository.findAll().forEach(clients::add);
		return clients;
	}

	/**
	 * Add a new client to the database.
	 *
	 * @param client the client object to be added.
	 * @return the saved client object.
	 */
	public ClientModel addClient(ClientModel client) {
		return clientRepository.save(client);
	}

	/**
	 * Retrieve a client by its unique ID.
	 *
	 * @param id the ID of the desired client.
	 * @return the found client object, or null if not found.
	 */
	public ClientModel getClientById(Long id) {
		return clientRepository.findById(id);
	}

	/**
	 * Update details of an existing client in the database.
	 *
	 * @param client the client object with updated details.
	 * @return the updated client object.
	 */
	public ClientModel editClient(ClientModel client) {
		return clientRepository.update(client);
	}

	/**
	 * Delete a client by its unique ID from the database.
	 *
	 * @param clientId the ID of the client to be deleted.
	 * @return the number of rows affected by the delete operation.
	 */
	public int deleteClient(Long clientId) {
		return clientRepository.delete(clientId);
	}

	/**
	 * Retrieve the last five clients added to the database.
	 *
	 * @return a list of the last five clients.
	 */
	public List<ClientModel> getLastFiveClients() {
		return clientRepository.findLastFiveClients();
	}
}
