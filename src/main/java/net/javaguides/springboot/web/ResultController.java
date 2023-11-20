package net.javaguides.springboot.web;

import net.javaguides.springboot.model.Inspection;
import net.javaguides.springboot.model.Result;
import net.javaguides.springboot.service.ResultServiceImpl;
import net.javaguides.springboot.web.dto.InspectionRequestDto;
import net.javaguides.springboot.web.dto.InspectionResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class ResultController {

    @Autowired
    private ResultServiceImpl resultService;

    public ResultController(ResultServiceImpl resultService) {
        this.resultService = resultService;
    }

    @PostMapping("inspection/result")
    public ResponseEntity<String> submitInspectionResult(@ModelAttribute InspectionResultDto resultDto) {
        try {
            Result savedInspectionResult = resultService.submitInspectionResult(resultDto);
            return ResponseEntity.ok("Inspection request submitted successfully with ID: " + savedInspectionResult.getResultId());
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions as needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error submitting inspection request.");
        }
    }

    @GetMapping("/inspection/result/find")
    public List<Result> getInspectionResults() {
        return resultService.getAllInspectionResults();
    }

    @GetMapping("/inspection/result/byPhase/{phaseId}")
    public List<Result> getResultsByPhase(@PathVariable Integer phaseId) {
        return resultService.getResultsByPhase(phaseId);
    }

    @GetMapping("/inspection/result/download/{defectFileName}")
    public ResponseEntity<byte[]> downloadDefectFile(@PathVariable String defectFileName) {
        byte[] defectFileData = resultService.getFileDataByDefectFileName(defectFileName);
        if (defectFileData != null) {
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + defectFileName)
                    .body(defectFileData);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
