package com.gcu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

// Mark this class as the main Spring Boot application class.
@SpringBootApplication
// Specify the base packages to scan for components (beans) during the application start-up.
@ComponentScan({ "com.gcu" })
public class MilestonePersonalTrainerApplication {

	public static void main(String[] args) {
		// Start the Spring Boot application using the specified class and command-line
		// arguments.
		SpringApplication.run(MilestonePersonalTrainerApplication.class, args);
	}

}