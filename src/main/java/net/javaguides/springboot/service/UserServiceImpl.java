package net.javaguides.springboot.service;

import java.util.*;
import java.util.stream.Collectors;

import net.javaguides.springboot.exception.InvalidCredentialsException;
import net.javaguides.springboot.model.Project;
import net.javaguides.springboot.repository.ProjectRepository;
import net.javaguides.springboot.repository.RoleRepository;
import net.javaguides.springboot.response.LoginResponse;
import net.javaguides.springboot.web.dto.LoginDto;
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

//	public User loginUser(LoginDto loginDto) {
//		try {
//			User user = userRepository.findByEmail(loginDto.getEmail());
//			if (user != null) {
//				String password = loginDto.getPassword();
//				String encodedPassword = user.getPassword();
//				boolean isPasswordCorrect = passwordEncoder.matches(password, encodedPassword);
//				if (isPasswordCorrect) {
//					Optional<User> user1 = userRepository.findOneByEmailAndPassword(loginDto.getEmail(), encodedPassword);
//					if (user1.isPresent()) {
//						return user; // Return the user object upon successful login
//					}
//				}
//			}
//			throw new RuntimeException("Invalid email or password.");
//		} catch (Exception ex) {
//			// Log the exception for debugging purposes
//			ex.printStackTrace(); // Print the stack trace to console or log it using a logging framework
//			throw new RuntimeException("An error occurred during login.");
//		}
//	}

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

	public User getUserById(Integer userId) {
		Optional<User> userOptional = userRepository.findById(userId);
		return userOptional.orElse(null); // Return null if user not found, handle it appropriately in your application
	}


}
