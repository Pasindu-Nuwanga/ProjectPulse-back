package net.javaguides.springboot.service;

import java.util.*;
import java.util.stream.Collectors;

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

	private Role validateRole(Integer roleId) {
		Optional<Role> roleOptional = roleRepository.findById(roleId);
		if (roleOptional.isPresent()) {
			return roleOptional.get();
		} else {
			throw new IllegalArgumentException("Invalid roleId: " + roleId);
			// or handle it in another appropriate way, like returning a default role
		}
	}

	private Project validateProject(Integer projectId) {
		Optional<Project> projectOptional = projectRepository.findById(projectId);
		if (projectOptional.isPresent()) {
			return projectOptional.get();
		} else {
			throw new IllegalArgumentException("Invalid roleId: " + projectId);
			// or handle it in another appropriate way, like returning a default role
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
			User user = userRepository.findByEmail(loginDto.getEmail());
			if (user != null) {
				String password = loginDto.getPassword();
				String encodedPassword = user.getPassword();
				boolean isPasswordCorrect = passwordEncoder.matches(password, encodedPassword);
				if (isPasswordCorrect) {
					Optional<User> user1 = userRepository.findOneByEmailAndPassword(loginDto.getEmail(), encodedPassword);
					if (user1.isPresent()) {
						return user; // Return the user object upon successful login
					}
				}
			}
			throw new RuntimeException("Invalid email or password.");
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
