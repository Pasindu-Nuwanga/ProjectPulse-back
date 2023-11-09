package net.javaguides.springboot.web;

import net.javaguides.springboot.service.ProjectServiceImpl;
import net.javaguides.springboot.web.dto.ProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class ProjectController {

    @Autowired
    private ProjectServiceImpl projectService;

    // Create a new project
    @PostMapping("/projects")
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto projectDto) {
        ProjectDto createdProject = projectService.createProject(projectDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
    }

    // Get all projects for dropdown list
    @GetMapping("/project/all")
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        List<ProjectDto> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Integer projectId) {
        try {
            // Convert projectId to appropriate data type if needed (e.g., Long)
            ProjectDto project = projectService.getProjectById(projectId);
            if (project != null) {
                return ResponseEntity.ok(project);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Handle exceptions and return an appropriate error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
