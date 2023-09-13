package com.gcu.service;

import com.gcu.model.ClientModel;
import com.gcu.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

	// Automatically inject an instance of ClientRepository
	@Autowired
	private ClientRepository clientRepository;

	// Fetch all clients from the database
	public List<ClientModel> getAllClients() {
		List<ClientModel> clients = new ArrayList<>();
		// Fetch clients from repository and add to the clients list
		clientRepository.findAll().forEach(clients::add);
		return clients;
	}

	// Add a new client to the database
	public ClientModel addClient(ClientModel client) {
		return clientRepository.save(client);
	}

	// Fetch a client by its ID
	public ClientModel getClientById(Long id) {
		return clientRepository.findById(id);
	}

	// Update details of an existing client in the database
	public ClientModel editClient(ClientModel client) {
		return clientRepository.update(client);
	}

	// Delete a client from the database using its ID
	public int deleteClient(Long clientId) {
		return clientRepository.delete(clientId);
	}

	// Fetch the last five clients from the database
	public List<ClientModel> getLastFiveClients() {
		return clientRepository.findLastFiveClients();
	}
}
