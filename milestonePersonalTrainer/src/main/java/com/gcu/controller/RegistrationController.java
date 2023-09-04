package com.gcu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gcu.model.User;
import com.gcu.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/register/")
public class RegistrationController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String display(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping("/doRegister")
	public String doRegister(@Valid User user, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "register";
		}

		userService.register(user); // Here we use the injected service

		return "redirect:/login/";
	}
}