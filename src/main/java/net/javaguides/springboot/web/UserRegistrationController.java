package net.javaguides.springboot.web;

import net.javaguides.springboot.exception.InvalidCredentialsException;
import net.javaguides.springboot.model.Phase;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.response.LoginResponse;
import net.javaguides.springboot.service.UserServiceImpl;
import net.javaguides.springboot.web.dto.LoginDto;
import net.javaguides.springboot.web.dto.PasswordUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import net.javaguides.springboot.web.dto.UserRegistrationDto;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1/employee")
public class UserRegistrationController {
	@Autowired
	private UserServiceImpl userService;

	public UserRegistrationController(UserServiceImpl userService) {
		this.userService = userService;
	}


	@GetMapping
	public String showRegistrationForm() {
		return "registration";
	}
	
	@PostMapping("/save")
	public String registerUserAccount(@RequestBody UserRegistrationDto registrationDto) {
		String id = userService.save(registrationDto);
		return id;
	}

	@PostMapping(path = "/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto) {
		User user = userService.loginUser(loginDto);
		if (user != null) {
			// Login successful, return the user object
			return ResponseEntity.ok(user);
		} else {
			// Login failed, return an appropriate response (e.g., custom error message)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials. Login failed.");
		}
	}

	//User Profile
	@GetMapping("/home/{userId}")
	public ResponseEntity<User> getUserProfile(@PathVariable Integer userId) {
		// Retrieve user details based on the provided userId
		User user = userService.getUserById(userId);

		// Check if the user was found
		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			// Return a 404 Not Found response if the user was not found
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	//Get Users by projectId
	@GetMapping("/users/{projectId}")
	public List<User> getUsersByProjectId(@PathVariable Integer projectId) {
		return userService.getUsersByProjectId(projectId);
	}

	//Edit User
	@PutMapping("/users/edit/{userId}")
	public ResponseEntity<User> updateUser(@PathVariable Integer userId, @RequestBody User updatedUser) {
		// Fetch the existing user from the database
		User existingUser = userService.getUserById(userId);

		if (existingUser != null) {
			// Update individual fields if provided in the request body
			if (updatedUser.getFirstName() != null) {
				existingUser.setFirstName(updatedUser.getFirstName());
			}

			if (updatedUser.getLastName() != null) {
				existingUser.setLastName(updatedUser.getLastName());
			}

			if (updatedUser.getEmail() != null) {
				existingUser.setEmail(updatedUser.getEmail());
			}

			// Update other fields as needed

			// Save the updated user to the database
			User updated = userService.updateUser(existingUser);
			return new ResponseEntity<>(updated, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}


	//Update Password
	@PostMapping("/change-password")
	public ResponseEntity<String> changePassword(@RequestBody PasswordUpdateDto passwordUpdateDto) {
		try {
			userService.changePassword(
					passwordUpdateDto.getEmail(),
					passwordUpdateDto.getOldPassword(),
					passwordUpdateDto.getNewPassword());
			return ResponseEntity.ok("Password changed successfully.");
		} catch (InvalidCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during password change.");
		}
	}


}
