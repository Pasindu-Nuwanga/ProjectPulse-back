package net.javaguides.springboot.service;

import net.javaguides.springboot.exception.DuplicateFileNameException;
import net.javaguides.springboot.exception.DuplicateInspectionNameException;
import net.javaguides.springboot.model.Document;
import net.javaguides.springboot.model.Inspection;
import net.javaguides.springboot.model.Phase;
import net.javaguides.springboot.repository.DocumentRepository;
import net.javaguides.springboot.repository.InspectionRepository;
import net.javaguides.springboot.repository.PhaseRepository;
import net.javaguides.springboot.web.dto.InspectionRequestDto;
import net.javaguides.springboot.web.dto.InspectionResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class InspectionServiceImpl {

    @Autowired
    private InspectionRepository inspectionRepository;

    @Autowired
    private PhaseRepository phaseRepository;

    public Inspection submitInspectionRequest(InspectionRequestDto requestDto) throws IOException {
        Phase phase = phaseRepository.findByPhaseName(requestDto.getPhaseName());

        // Check if an inspection with the same name already exists
        if (inspectionRepository.existsByInspectionNameAndPhases(requestDto.getInspectionName(), phase)) {
            // Throw an exception or return an error response
            throw new DuplicateInspectionNameException("An inspection with the same name already exists in this phase.");
        }

        // Check if an inspection with the same fileName already exists
        if (inspectionRepository.existsByFileNameAndPhases(requestDto.getFileName(), phase)) {
            // Throw an exception or return an error response
            throw new DuplicateFileNameException("A file with the same name already exists in this phase.");
        }

        Inspection inspection = new Inspection();
        inspection.setInspectionName(requestDto.getInspectionName());
        inspection.setPhaseSection(requestDto.getPhaseSection());
        inspection.setConstructionType(requestDto.getConstructionType());
        inspection.setPhases(phase);
        inspection.setInspectionRequestDate(requestDto.getInspectionRequestDate());

        // Check if inspectionDate is provided before setting it
        if (requestDto.getInspectionDate() != null) {
            inspection.setInspectionDate(requestDto.getInspectionDate());
        }

        // Set fileName and handle fileAttachment logic
        MultipartFile fileAttachment = requestDto.getFileAttachment();
        if (fileAttachment != null && !fileAttachment.isEmpty()) {
            String originalFileName = StringUtils.cleanPath(fileAttachment.getOriginalFilename());

            // Check if an inspection with the same fileName already exists for the given phase
            if (inspectionRepository.existsByFileNameAndPhases(originalFileName, phase)) {
                // Throw an exception or return an error response
                throw new DuplicateFileNameException("A file with the same name already exists in this phase.");
            }

            inspection.setFileName(originalFileName);
            inspection.setFileAttachment(fileAttachment.getBytes());
        }

        return inspectionRepository.save(inspection);
    }



    public List<Inspection> getAllInspectionRequests() {
        return inspectionRepository.findAll();
    }

    public byte[] getFileDataByFileName(String fileName) {
        Inspection inspection = inspectionRepository.findByFileName(fileName);
        if (inspection != null) {
            return inspection.getFileAttachment();
        }
        return null;
    }

    public List<Inspection> getInspectionsByPhase(Integer phaseId) {
        return inspectionRepository.findByPhasesPhaseId(phaseId);
    }

    public Inspection updateInspectionDate(Integer inspectionId, Date newInspectionDate) {
        Optional<Inspection> optionalInspection = inspectionRepository.findById(inspectionId);
        if (optionalInspection.isPresent()) {
            Inspection inspection = optionalInspection.get();
            inspection.setInspectionDate(newInspectionDate);
            return inspectionRepository.save(inspection);
        } else {
            throw new IllegalArgumentException("Inspection not found with ID: " + inspectionId);
        }
    }

}
