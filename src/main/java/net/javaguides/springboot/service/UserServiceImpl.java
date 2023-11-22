package net.javaguides.springboot.service;

import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.deser.NullValueProvider;
import net.javaguides.springboot.exception.InvalidCredentialsException;
import net.javaguides.springboot.model.Phase;
import net.javaguides.springboot.model.Project;
import net.javaguides.springboot.repository.ProjectRepository;
import net.javaguides.springboot.repository.RoleRepository;
import net.javaguides.springboot.response.LoginResponse;
import net.javaguides.springboot.web.dto.LoginDto;
import net.javaguides.springboot.web.dto.PasswordUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.Role;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.web.dto.UserRegistrationDto;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl{

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ProjectRepository projectRepository;

	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ProjectRepository projectRepository, BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.projectRepository = projectRepository;
		this.passwordEncoder = passwordEncoder;
	}


	// Validate Role
	private Role validateRole(Integer roleId) {
		Optional<Role> roleOptional = roleRepository.findById(roleId);
		if (roleOptional.isPresent()) {
			return roleOptional.get();
		} else {
			throw new IllegalArgumentException("Invalid roleId: " + roleId);

		}
	}

	// Validate Project
	private Project validateProject(Integer projectId) {
		Optional<Project> projectOptional = projectRepository.findById(projectId);
		if (projectOptional.isPresent()) {
			return projectOptional.get();
		} else {
			throw new IllegalArgumentException("Invalid roleId: " + projectId);

		}
	}

	public String save(UserRegistrationDto registrationDto) {

		User user = new User();
		user.setFirstName(registrationDto.getFirstName());
		user.setLastName(registrationDto.getLastName());
		user.setEmail(registrationDto.getEmail());
		user.setPassword(this.passwordEncoder.encode(registrationDto.getPassword()));

		Role role = validateRole(registrationDto.getRoleId());
		Project project = validateProject(registrationDto.getProjectId());
		user.setRoles(role);
		user.setProjects(project);
		userRepository.save(user);
		return "created user Successfully";
	}


	public User loginUser(LoginDto loginDto) {
		try {
			// Validate that email and password are not empty
			if (StringUtils.isEmpty(loginDto.getEmail()) || StringUtils.isEmpty(loginDto.getPassword())) {
				throw new IllegalArgumentException("Email and password are required.");
			}

			// Find the user by email (username)
			User user = userRepository.findByEmail(loginDto.getEmail());

			if (user != null) {
				// Check if the entered password matches the stored encoded password
				String password = loginDto.getPassword();
				String encodedPassword = user.getPassword();

				boolean isPasswordCorrect = passwordEncoder.matches(password, encodedPassword);

				if (isPasswordCorrect) {
					// If the password is correct, the login is successful
					return user; // Return the user object upon successful login
				} else {
					// Password is incorrect
					throw new InvalidCredentialsException("Incorrect password.");
				}
			} else {
				// User with the provided email is not found
				throw new InvalidCredentialsException("User not found.");
			}

		} catch (InvalidCredentialsException ice) {
			// Catch and rethrow custom exception with specific message
			throw ice;
		} catch (IllegalArgumentException iae) {
			// Catch and rethrow exception for empty fields
			throw iae;
		} catch (Exception ex) {
			// Log the exception for debugging purposes
			ex.printStackTrace(); // Print the stack trace to console or log it using a logging framework
			throw new RuntimeException("An error occurred during login.");
		}
	}


	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}

		// Get a list of SimpleGrantedAuthority objects from the user's roles
		Collection<GrantedAuthority> authorities = user.getRoles().setRoleName();

		return new org.springframework.security.core.userdetails.User(
				user.getEmail(), user.getPassword(), authorities);
	}

	public List<User> getUsersByProjectId(Integer projectId) {
		return userRepository.findByProjectsProjectId(projectId);
	}

	public User getUserById(Integer userId) {
		Optional<User> userOptional = userRepository.findById(userId);
		return userOptional.orElse(null); // Return null if user not found, handle it appropriately in your application
	}

	public User updateUser(User updatedUser) {
		// Assuming user ID is not null
		User existingUser = userRepository.findById(updatedUser.getUserId()).orElse(null);

		if (existingUser != null) {
			// Update the user details
			existingUser.setFirstName(updatedUser.getFirstName());
			existingUser.setLastName(updatedUser.getLastName());
			existingUser.setEmail(updatedUser.getEmail());

			// Save the updated user to the database
			return userRepository.save(existingUser);
		}

		return null; // User not found
	}

	@Transactional
	public void changePassword(String userEmail, String oldPassword, String newPassword) {
		try {
			User user = userRepository.findByEmail(userEmail);

			if (user != null) {
				if (oldPasswordIsValid(user, oldPassword)) {
					// Check if the new password is null or empty
					if (newPassword != null && !newPassword.isEmpty()) {
						// Check if the new password is different from the old password
						if (!newPassword.equals(oldPassword)) {
							// If the current password is correct, the new password is provided, and it's different from the old password, update the password
							user.setPassword(passwordEncoder.encode(newPassword));
							userRepository.save(user);
						} else {
							// New password is the same as the old password
							throw new InvalidCredentialsException("New password must be different from the old password.");
						}
					} else {
						// New password is null or empty
						throw new InvalidCredentialsException("New password cannot be null or empty.");
					}
				} else {
					// Current password is incorrect
					throw new InvalidCredentialsException("Incorrect current password.");
				}
			} else {
				// User with the provided email is not found
				throw new InvalidCredentialsException("User not found.");
			}
		} catch (IllegalArgumentException e) {
			// Handle the IllegalArgumentException (e.g., log it or return an error message)
			throw e;
		}
	}

	public Boolean oldPasswordIsValid (User user, String oldPassword){
		return passwordEncoder.matches(oldPassword, user.getPassword());
	}


}
