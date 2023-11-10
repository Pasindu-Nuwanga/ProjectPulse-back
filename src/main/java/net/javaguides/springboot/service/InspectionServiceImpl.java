package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Document;
import net.javaguides.springboot.model.Inspection;
import net.javaguides.springboot.model.Phase;
import net.javaguides.springboot.repository.DocumentRepository;
import net.javaguides.springboot.repository.InspectionRepository;
import net.javaguides.springboot.repository.PhaseRepository;
import net.javaguides.springboot.web.dto.InspectionRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
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

        Inspection inspection = new Inspection();
        inspection.setInspectionName(requestDto.getInspectionName());
        inspection.setPhaseSection(requestDto.getPhaseSection());
        inspection.setConstructionType(requestDto.getConstructionType());
        inspection.setPhases(phase);

        MultipartFile fileAttachment = requestDto.getFileAttachment();
        if (fileAttachment != null && !fileAttachment.isEmpty()) {
            String originalFileName = StringUtils.cleanPath(fileAttachment.getOriginalFilename());
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

    public List<Inspection> getInspectionRequestsByPhase(String phaseName) {
        return inspectionRepository.findByPhases_PhaseName(phaseName);
    }

}
