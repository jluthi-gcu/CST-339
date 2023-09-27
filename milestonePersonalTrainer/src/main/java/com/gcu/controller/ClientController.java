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

/**
 * Controller responsible for handling CRUD operations related to clients.
 * 
 * @author Joel Luthi, Alex Frear, Ian Hook
 * @version 1.0
 */
@Controller
@RequestMapping("/clients/")
public class ClientController {

	/**
	 * Service responsible for handling business logic related to clients. It's
	 * automatically injected by Spring Framework when an instance of this
	 * controller is created.
	 */
	@Autowired
	private ClientService clientService;

	/**
	 * Fetch all clients from the database and display them.
	 *
	 * @param model The model used to pass attributes to the view.
	 * @return The template name for displaying all clients.
	 */
	@GetMapping
	public String getAllClients(Model model) {
		List<ClientModel> clients = clientService.getAllClients();
		model.addAttribute("clients", clients);
		return "clients"; // assuming the template is named "clients.html"
	}

	/**
	 * Fetch a specific client by its ID and display its details.
	 *
	 * @param id    The ID of the client to retrieve.
	 * @param model The model used to pass attributes to the view.
	 * @return The template name for displaying client details.
	 */
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

	/**
	 * Show a form for adding a new client.
	 *
	 * @param model The model used to pass attributes to the view.
	 * @return The template name for the add client form.
	 */
	@GetMapping("/create")
	public String showAddClientForm(Model model) {
		ClientModel newClient = new ClientModel();
		model.addAttribute("client", newClient);
		return "addClient";
	}

	/**
	 * Process the addition of a new client.
	 *
	 * @param client The new client to be added.
	 * @param result Results of binding and validation.
	 * @param model  The model used to pass attributes to the view.
	 * @return Redirects to the list of clients or shows the form again with errors.
	 */
	@PostMapping
	public String addClient(@Valid @ModelAttribute("client") ClientModel client, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "addClient"; // Show the form again with error messages
		}
		clientService.addClient(client);
		model.addAttribute("successMessage", "Client added successfully!");
		return "redirect:/clients/";
	}

	/**
	 * Show a form for editing a client by its ID.
	 *
	 * @param id                 The ID of the client to edit.
	 * @param model              The model used to pass attributes to the view.
	 * @param redirectAttributes Redirect attributes for flash messages.
	 * @return The template name for the edit client form or a redirect.
	 */
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

	/**
	 * Process the edits for a specific client.
	 *
	 * @param id            The ID of the client to be edited.
	 * @param client        The edited client details.
	 * @param bindingResult Results of binding and validation.
	 * @return Redirects to the list of clients or shows the edit form again with
	 *         errors.
	 */
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

	/**
	 * Deletes a client by its ID.
	 *
	 * @param id The ID of the client to delete.
	 * @return Redirects to the list of clients.
	 */
	@GetMapping("/delete/{id}")
	public String deleteClient(@PathVariable Long id) {
		clientService.deleteClient(id);
		return "redirect:/clients/";
	}
}
