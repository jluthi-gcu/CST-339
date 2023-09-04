package com.gcu.controller;

import com.gcu.model.WorkoutModel;
import com.gcu.service.WorkoutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class WorkoutControllerTest {

	@InjectMocks
	private WorkoutController workoutController; // The controller being tested

	@Mock
	private WorkoutService workoutService; // Mocked service dependency

	@Mock
	private Model model; // Mocked Model object used to pass data to views

	@Mock
	private BindingResult bindingResult; // Mocked BindingResult object to hold validation errors

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this); // Initialize mocks before each test
	}

	@Test
	public void testDisplayWorkouts() {
		List<WorkoutModel> workoutList = new ArrayList<>();
		when(workoutService.getAllWorkouts()).thenReturn(workoutList); // Simulate fetching workout list

		String result = workoutController.displayWorkouts(model, null); // Call the method being tested
		assertEquals("workouts", result); // Ensure that the expected view name is returned

		verify(model, times(1)).addAttribute("workouts", workoutList); // Verify that workout list is added to the model
		verify(model, times(1)).addAttribute("title", "Your Workouts"); // Verify that the title attribute is added to
																		// the model
	}

	@Test
	public void testShowCreateForm() {
		String result = workoutController.showCreateForm(model); // Call the method being tested
		assertEquals("createWorkout", result); // Ensure that the expected view name is returned

		verify(model, times(1)).addAttribute(eq("workout"), any(WorkoutModel.class)); // Verify that a workout attribute
																						// is added to the model
	}

	@Test
    public void testDoCreateWorkoutWithErrors() {
        when(bindingResult.hasErrors()).thenReturn(true); // Simulate validation errors

        WorkoutModel workout = new WorkoutModel(); // Create a workout object
        String result = workoutController.doCreateWorkout(workout, bindingResult); // Call the method being tested

        assertEquals("createWorkout", result); // Ensure that the expected view name is returned
    }

	@Test
    public void testDoCreateWorkoutSuccessfully() {
        when(bindingResult.hasErrors()).thenReturn(false); // Simulate no validation errors

        WorkoutModel workout = new WorkoutModel(); // Create a workout object
        String result = workoutController.doCreateWorkout(workout, bindingResult); // Call the method being tested

        assertEquals("redirect:/workouts/", result); // Ensure that the expected redirection is performed
    }

	@Test
	public void testShowEditFormWithExistingWorkout() {
		Long workoutId = 1L; // Define a workout ID
		WorkoutModel workout = new WorkoutModel(); // Create a workout object
		when(workoutService.getWorkoutById(workoutId)).thenReturn(workout); // Simulate fetching an existing workout

		String result = workoutController.showEditForm(workoutId, model, null); // Call the method being tested
		assertEquals("editWorkout", result); // Ensure that the expected view name is returned

		verify(model, times(1)).addAttribute("workout", workout); // Verify that the workout attribute is added to the
																	// model
	}

	@Test
	public void testShowEditFormWithNonExistingWorkout() {
		Long workoutId = 1L; // Define a workout ID
		when(workoutService.getWorkoutById(workoutId)).thenReturn(null); // Simulate no existing workout

		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class); // Mock RedirectAttributes

		String result = workoutController.showEditForm(workoutId, model, redirectAttributes); // Call the method being
																								// tested
		assertEquals("redirect:/workouts/", result); // Ensure that the expected redirection is performed

		verify(redirectAttributes, times(1)).addFlashAttribute("errorMessage",
				"No workout found with ID: " + workoutId); // Verify that an error message is added to the flash
															// attributes
	}

	@Test
	public void testDoEditWorkoutWithErrors() {
		Long workoutId = 1L; // Define a workout ID
		when(bindingResult.hasErrors()).thenReturn(true); // Simulate validation errors

		WorkoutModel workout = new WorkoutModel(); // Create a workout object
		String result = workoutController.doEditWorkout(workoutId, workout, bindingResult); // Call the method being
																							// tested

		assertEquals("editWorkout", result); // Ensure that the expected view name is returned
	}

	@Test
	public void testDoEditWorkoutSuccessfully() {
		Long workoutId = 1L; // Define a workout ID
		when(bindingResult.hasErrors()).thenReturn(false); // Simulate no validation errors

		WorkoutModel workout = new WorkoutModel(); // Create a workout object
		String result = workoutController.doEditWorkout(workoutId, workout, bindingResult); // Call the method being
																							// tested

		assertEquals("redirect:/workouts/", result); // Ensure that the expected redirection is performed
	}

	@Test
	public void testDeleteWorkout() {
		Long workoutId = 1L; // Define a workout ID
		String result = workoutController.deleteWorkout(workoutId); // Call the method being tested
		assertEquals("redirect:/workouts/", result); // Ensure that the expected redirection is performed

		verify(workoutService, times(1)).deleteWorkout(workoutId); // Verify that the workout deletion method is called
	}
}
