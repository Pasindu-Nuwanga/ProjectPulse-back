package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Phase;
import net.javaguides.springboot.model.Project;
import net.javaguides.springboot.repository.ProjectRepository;
import net.javaguides.springboot.web.dto.PhaseDto;
import net.javaguides.springboot.web.dto.ProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectServiceImpl {

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectDto createProject(ProjectDto projectDto) {
        // Check if the project name is null
        if (projectDto.getProjectName() == null) {
            // Handle the case where the project name is null (you can throw an exception or handle it as needed)
            throw new IllegalArgumentException("Project name cannot be null");
        }

        // Create a new Project object and set its projectName using the provided ProjectDto
        Project project = new Project();
        project.setProjectName(projectDto.getProjectName());

        // Save the newly created project to the database
        project = projectRepository.save(project);

        // Map the saved project back to a ProjectDto and return it
        return new ProjectDto(project.getProjectId(), project.getProjectName());
    }


    public List<ProjectDto> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .map(project -> new ProjectDto(project.getProjectId(), project.getProjectName()))
                .collect(Collectors.toList());
    }

    public ProjectDto editProjectName(Integer projectId, String newProjectName) {
        // Check if the project exists
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Phase not found with id: " + projectId));

        // Update the project name
        project.setProjectName(newProjectName);
        projectRepository.save(project);

        // Return the updated ProjectDto
        return new ProjectDto(project.getProjectId(), project.getProjectName());
    }

    public ProjectDto getProjectById(Integer projectId) {
        // Logic to retrieve project by ID from your data source (e.g., database)
        Project project = projectRepository.findById(projectId).orElse(null);

        if (project != null) {
            // Convert Project entity to ProjectDto if needed
            ProjectDto projectDto = convertToDto(project);
            return projectDto;
        } else {
            return null; // Return null if project with given ID is not found
        }
    }

    private ProjectDto convertToDto(Project project) {

         ProjectDto projectDto = new ProjectDto();
         projectDto.setProjectId(project.getProjectId());
         projectDto.setProjectName(project.getProjectName());

         return projectDto;
    }
}
