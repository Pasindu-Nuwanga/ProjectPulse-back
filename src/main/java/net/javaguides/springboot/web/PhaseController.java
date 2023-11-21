package net.javaguides.springboot.web;

import net.javaguides.springboot.model.Phase;
import net.javaguides.springboot.model.Project;
import net.javaguides.springboot.repository.PhaseRepository;
import net.javaguides.springboot.service.DocumentServiceImpl;
import net.javaguides.springboot.service.PhaseServiceImpl;
import net.javaguides.springboot.web.dto.PhaseDto;
import net.javaguides.springboot.web.dto.ProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:3000")
public class PhaseController {

    @Autowired
    private PhaseServiceImpl phaseService;


    // Insert a new phase based on project name
    @PostMapping("/phases")
    public ResponseEntity<String> createPhase(@RequestBody PhaseDto phaseDto) {
        try {
            phaseService.createPhase(phaseDto.getPhaseName(), phaseDto.getProjectName());
            return ResponseEntity.ok("Phase created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to create phase: " + e.getMessage());
        }
    }

    @GetMapping("/phase/all")
    public ResponseEntity<List<PhaseDto>> getAllPhases() {
        List<PhaseDto> phases = phaseService.getAllPhases();
        return ResponseEntity.ok(phases);
    }

    @GetMapping("/api/projects/{projectId}/phases")
    public List<Phase> getPhasesByProjectId(@PathVariable Integer projectId) {
        return phaseService.getPhasesByProjectId(projectId);
    }

    // Edit the name of a phase
    @PutMapping("edit/phases/{phaseId}")
    public ResponseEntity<String> editPhaseName(@PathVariable Integer phaseId, @RequestBody PhaseDto phaseDto) {
        try {
            PhaseDto updatedPhase = phaseService.editPhaseName(phaseId, phaseDto.getPhaseName());
            return ResponseEntity.ok("Phase name is updated successfully!");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body("Phase not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to edit phase name: " + e.getMessage());
        }
    }


}
