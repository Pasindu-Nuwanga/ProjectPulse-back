package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Inspection;
import net.javaguides.springboot.model.Result;
import net.javaguides.springboot.repository.InspectionRepository;
import net.javaguides.springboot.repository.ResultRepository;
import net.javaguides.springboot.web.dto.InspectionResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class ResultServiceImpl {
    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private InspectionRepository inspectionRepository;

    public Result submitInspectionResult(InspectionResultDto resultDto) throws IOException {
        List<Inspection> inspections = inspectionRepository.findByInspectionName(resultDto.getInspectionName());

        if (!inspections.isEmpty()) {
            Inspection inspection = inspections.get(0);

            Result result = new Result();
            result.setInspectionStatus(resultDto.getInspectionStatus());
            result.setInspectionResultDate(resultDto.getInspectionResultDate());
            result.setComments(resultDto.getComments());
            result.setInspections(inspection);

            MultipartFile defectFileAttachment = resultDto.getDefectFileAttachment();
            if (defectFileAttachment != null && !defectFileAttachment.isEmpty()) {
                String originalFileName = StringUtils.cleanPath(defectFileAttachment.getOriginalFilename());
                result.setDefectFileName(originalFileName);
                result.setDefectFileAttachment(defectFileAttachment.getBytes());
            }

            return resultRepository.save(result);
        } else {
            // Handle the case when no inspections are found with the given name
            // You might want to throw an exception or handle it based on your business logic
            return null;
        }
    }

    //Get all results
    public List<Result> getAllInspectionResults() {
        return resultRepository.findAll();
    }

    //For file download
    public byte[] getFileDataByDefectFileName(String defectFileName) {
        Result result = resultRepository.findByDefectFileName(defectFileName);
        if (result != null) {
            return result.getDefectFileAttachment();
        }
        return null;
    }

    //Get results by inspection id
    public List<Result> getResultsByPhase(Integer phaseId) {
        return resultRepository.findByInspectionsInspectionId(phaseId);
    }


}
