package com.gcu.service;

import com.gcu.model.LoginModel;
import com.gcu.model.User;
import com.gcu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public boolean authenticate(LoginModel loginModel) {
		Optional<User> userOpt = userRepository.findByUsername(loginModel.getUsername());
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			return loginModel.getPassword().equals(user.getPassword());
		}
		return false;
	}

	public User register(User user) {
		return userRepository.save(user);
	}
}