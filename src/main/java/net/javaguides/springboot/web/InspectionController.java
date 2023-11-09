package net.javaguides.springboot.web;

import net.javaguides.springboot.model.Inspection;
import net.javaguides.springboot.service.InspectionServiceImpl;
import net.javaguides.springboot.web.dto.InspectionRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions as needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error submitting inspection request.");
        }
    }

    @GetMapping("/inspection/request/find")
    public List<Inspection> getInspectionRequests() {
        return inspectionService.getAllInspectionRequests();
    }

}
