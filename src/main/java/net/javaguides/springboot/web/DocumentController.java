package net.javaguides.springboot.web;

import net.javaguides.springboot.model.Document;
import net.javaguides.springboot.model.Phase;
import net.javaguides.springboot.service.DocumentServiceImpl;
import net.javaguides.springboot.web.dto.DocumentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:3000")
public class DocumentController {
    @Autowired
    private DocumentServiceImpl documentService;

    public DocumentController(DocumentServiceImpl documentService) {
        this.documentService = documentService;
    }


    //Upload Files
    @PostMapping("/uploadFile")
    public DocumentDto uploadFile(@RequestParam("file") MultipartFile file,
                                  @RequestParam("phaseName") String phaseName,
                                  @RequestParam("uploadDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date uploadDate,
                                  @RequestParam("alertMessage") String alertMessage) {

        Document document = documentService.storeFile(file, phaseName, uploadDate, alertMessage);

        String fileDownloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(document.getDocumentId().toString())
                .toUriString();

        return new DocumentDto(document.getDocumentId().toString(), document.getDocumentName(),
                fileDownloadUrl, document.getDocumentType(), (long) document.getData().length,
                document.getPhases().getPhaseName(), document.getUploadDate(), document.getAlertMessage());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<DocumentDto> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files,
                                                 @RequestParam("phaseId") String phaseName,
                                                 @RequestParam("uploadDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date uploadDate,
                                                 @RequestParam("alertMessage") String alertMessage) {
        List<DocumentDto> uploadedFiles = new ArrayList<>();

        for (MultipartFile file : files) {
            DocumentDto documentDto = uploadFile(file, phaseName, uploadDate, alertMessage);
            uploadedFiles.add(documentDto);
        }

        return uploadedFiles;
    }


    //Download Files
    @GetMapping("/downloadFile/{fileId:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) throws Exception {
        // Load file as Resource
        Document document;
        document = documentService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(document.getDocumentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getDocumentName() + "\"")
                .body(new ByteArrayResource(document.getData()));
    }

    // Get All Files
    @GetMapping("/getAllFiles")
    public List<DocumentDto> getAllFiles() {
        List<Document> documents = documentService.getAllFiles();
        return documents.stream()
                .map(document -> new DocumentDto(document.getDocumentId(), document.getDocumentName(),
                        ServletUriComponentsBuilder.fromCurrentContextPath()
                                .path("/downloadFile/")
                                .path(document.getDocumentId())
                                .toUriString(),
                        document.getDocumentType(), (long) document.getData().length,
                         document.getPhases().getPhaseName(), document.getUploadDate(), document.getAlertMessage()))
                .collect(Collectors.toList());
    }

    @GetMapping("/api/phases/{phaseId}/documents")
    public List<Document> getDocumentsByPhaseId(@PathVariable Integer phaseId) {
        return documentService.getDocumentsByPhaseId(phaseId);
    }


    // Delete File
    @DeleteMapping("/deleteFile/{fileId:.+}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileId) {
        try {
            // Attempt to delete the file by its ID
            documentService.deleteFile(fileId);
            return ResponseEntity.ok("File deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to delete the file: " + e.getMessage());
        }
    }

    // Get All Alert Messages
    @GetMapping("/getAllAlertMessages")
    public List<String> getAllAlertMessages() {
        return documentService.getAllAlertMessages();
    }


}
