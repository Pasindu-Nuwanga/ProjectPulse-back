package net.javaguides.springboot.web;

import net.javaguides.springboot.model.Document;
import net.javaguides.springboot.model.Inspection;
import net.javaguides.springboot.service.InspectionServiceImpl;
import net.javaguides.springboot.web.dto.InspectionRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class InspectionController {

    @Autowired
    private InspectionServiceImpl inspectionService;

    public InspectionController(InspectionServiceImpl inspectionService) {
        this.inspectionService = inspectionService;
    }

    @PostMapping("inspection/request")
    public ResponseEntity<String> submitInspectionRequest(@ModelAttribute InspectionRequestDto requestDto) {
        try {
            Inspection savedInspection = inspectionService.submitInspectionRequest(requestDto);
            return ResponseEntity.ok("Inspection request submitted successfully with ID: " + savedInspection.getInspectionId());
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions as needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error submitting inspection request.");
        }
    }

    @GetMapping("/inspection/request/find")
    public List<Inspection> getInspectionRequests() {
        return inspectionService.getAllInspectionRequests();
    }

    @GetMapping("/inspection/request/byPhase/{phaseId}")
    public List<Inspection> getInspectionsByPhase(@PathVariable Integer phaseId) {
        return inspectionService.getInspectionsByPhase(phaseId);
    }

    @GetMapping("/inspection/request/download/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {
        byte[] fileData = inspectionService.getFileDataByFileName(fileName);
        if (fileData != null) {
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + fileName)
                    .body(fileData);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


}
