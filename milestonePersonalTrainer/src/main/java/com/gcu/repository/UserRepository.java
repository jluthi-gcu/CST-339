package com.gcu.repository;

import com.gcu.model.User;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

	// Declare a method signature to find a User entity by its username
	// Optional is used to indicate the result might be present or not
	Optional<User> findByUsername(String username);
}
