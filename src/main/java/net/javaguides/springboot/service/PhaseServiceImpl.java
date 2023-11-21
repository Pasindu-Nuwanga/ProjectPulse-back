package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Phase;
import net.javaguides.springboot.model.Project;
import net.javaguides.springboot.repository.PhaseRepository;
import net.javaguides.springboot.repository.ProjectRepository;
import net.javaguides.springboot.web.dto.PhaseDto;
import net.javaguides.springboot.web.dto.ProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static org.hibernate.bytecode.BytecodeLogger.LOGGER;

@Service
@Transactional
public class PhaseServiceImpl {

    @Autowired
    private PhaseRepository phaseRepository;
    @Autowired
    private ProjectRepository projectRepository;

    public PhaseDto createPhase(String phaseName, String projectName) {
        // Find or create the project by projectName
        Project project = projectRepository.findByProjectName(projectName);
        if (project == null) {
            project = new Project();
            project.setProjectName(projectName);
            projectRepository.save(project);
        }

        // Create a new phase and associate it with the project
        Phase phase = new Phase();
        phase.setPhaseName(phaseName);
        phase.setProject(project);
        phaseRepository.save(phase);

        // Map the saved phase back to PhaseDto and return it
        return new PhaseDto(phase.getPhaseId(), phase.getPhaseName(), project.getProjectName());
    }

    public List<PhaseDto> getAllPhases() {
        List<Phase> phases = phaseRepository.findAll();
        return phases.stream()
                .map(phase -> new PhaseDto(phase.getPhaseId(), phase.getPhaseName(), phase.getProject().getProjectName()))
                .collect(Collectors.toList());
    }

    public List<Phase> getPhasesByProjectId(Integer projectId) {
        return phaseRepository.findByProjectProjectId(projectId);
    }


    public PhaseDto editPhaseName(Integer phaseId, String newPhaseName) {
        // Check if the phase exists
        Phase phase = phaseRepository.findById(phaseId)
                .orElseThrow(() -> new EntityNotFoundException("Phase not found with id: " + phaseId));

        // Update the phase name
        phase.setPhaseName(newPhaseName);
        phaseRepository.save(phase);

        // Return the updated PhaseDto
        return new PhaseDto(phase.getPhaseId(), phase.getPhaseName(), phase.getProject().getProjectName());
    }

}
