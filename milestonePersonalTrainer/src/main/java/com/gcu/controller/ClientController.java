package com.gcu.controller;

import com.gcu.model.ClientModel;
import com.gcu.service.ClientService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/clients/")
public class ClientController {

	@Autowired
	private ClientService clientService;

	// Fetch all clients from the database
	@GetMapping
	public String getAllClients(Model model) {
		List<ClientModel> clients = clientService.getAllClients();
		model.addAttribute("clients", clients);
		return "clients"; // assuming the template is named "clients.html"
	}

	// Fetch a specific client by its ID
	@GetMapping("/{id}")
	public String getClientById(@PathVariable("id") Long id, Model model) {
		ClientModel client = clientService.getClientById(id);
		if (client != null) {
			model.addAttribute("client", client);
			return "clientDetails"; // assuming you have a template named "clientDetails.html"
		}
		model.addAttribute("errorMessage", "Client not found");
		return "clients";
	}

	@GetMapping("/create")
	public String showAddClientForm(Model model) {
		ClientModel newClient = new ClientModel();
		model.addAttribute("client", newClient);
		return "addClient";
	}

	@PostMapping
	public String addClient(@Valid @ModelAttribute("client") ClientModel client, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "addClient"; // Show the form again with error messages
		}
		clientService.addClient(client);
		model.addAttribute("successMessage", "Client added successfully!");
		return "redirect:/clients/";
	}

	@GetMapping("/edit/{id}")
	public String showEditClientForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
		ClientModel client = clientService.getClientById(id);
		if (client == null) {
			// If the client is not found, send an error message and redirect
			redirectAttributes.addFlashAttribute("errorMessage", "No client found with ID: " + id);
			return "redirect:/clients/";
		}
		// Add the client to the model for editing
		model.addAttribute("client", client);
		return "editClient"; // Make sure you have a template named "editClient.html"
	}

	@PostMapping("/edit/{id}")
	public String doEditClient(@PathVariable Long id, @ModelAttribute @Valid ClientModel client,
			BindingResult bindingResult) {
		// Validate the client edits
		if (bindingResult.hasErrors()) {
			return "editClient"; // Go back to the form if there are validation errors
		}
		client.setClientId(id); // Set the client ID to ensure you're updating the correct client
		clientService.editClient(client); // Apply the edits
		return "redirect:/clients/"; // Redirect to the list of clients
	}

	@GetMapping("/delete/{id}")
	public String deleteClient(@PathVariable Long id) {
		clientService.deleteClient(id);
		return "redirect:/clients/";
	}
}
