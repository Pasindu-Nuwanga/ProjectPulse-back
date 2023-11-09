package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Inspection;
import net.javaguides.springboot.model.Phase;
import net.javaguides.springboot.repository.DocumentRepository;
import net.javaguides.springboot.repository.InspectionRepository;
import net.javaguides.springboot.repository.PhaseRepository;
import net.javaguides.springboot.web.dto.InspectionRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class InspectionServiceImpl {

    @Autowired
    private InspectionRepository inspectionRepository;

    @Autowired
    private PhaseRepository phaseRepository;

    public Inspection submitInspectionRequest(InspectionRequestDto requestDto) throws IOException {
        // Validate and process the inspection request data
        // For example, validate required fields, handle file attachment, etc.

        // Retrieve the Phase entity based on phaseName from the request
        Phase phase = phaseRepository.findByPhaseName(requestDto.getPhaseName());

        // Create an Inspection entity and populate its attributes
        Inspection inspection = new Inspection();
        inspection.setInspectionName(requestDto.getInspectionName());
        inspection.setPhaseSection(requestDto.getPhaseSection());
        inspection.setConstructionType(requestDto.getConstructionType());
        inspection.setPhases(phase);

        // Handle file attachment
        if (requestDto.getFileAttachment() != null && !requestDto.getFileAttachment().isEmpty()) {
            inspection.setFileAttachment(requestDto.getFileAttachment().getBytes());
        }

        // Save the inspection to the database
        return inspectionRepository.save(inspection);
    }

    public List<Inspection> getAllInspectionRequests() {
        return inspectionRepository.findAll();
    }



}
