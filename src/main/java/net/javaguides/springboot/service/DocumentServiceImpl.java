package net.javaguides.springboot.service;

import net.javaguides.springboot.exception.FileNotFoundException;
import net.javaguides.springboot.exception.FileStorageException;
import net.javaguides.springboot.model.Document;
import net.javaguides.springboot.model.Phase;
import net.javaguides.springboot.repository.DocumentRepository;
import net.javaguides.springboot.repository.PhaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class DocumentServiceImpl{

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private PhaseRepository phaseRepository;


    public Document storeFile(MultipartFile file, String phaseName, Date uploadDate, String alertMessage) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Document dbFile = new Document();
            dbFile.setDocumentName(fileName);
            dbFile.setDocumentType(file.getContentType());

            // Set upload date and alert message
            dbFile.setUploadDate(uploadDate);
            dbFile.setAlertMessage(alertMessage);

            // Find phase by its name
            Phase phase = phaseRepository.findByPhaseName(phaseName);

            dbFile.setPhases(phase);
            dbFile.setData(file.getBytes());

            return documentRepository.save(dbFile);
        } catch (IOException e) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
        }
    }

    public Document getFile(String fileId) {
        return documentRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
    }

    public List<Document> getAllFiles() {
        return documentRepository.findAll();
    }

    public void deleteFile(String fileId) {
        Optional<Document> optionalDocument = documentRepository.findById(fileId);

        if (optionalDocument.isPresent()) {
            Document document = optionalDocument.get();
            documentRepository.delete(document);
        } else {
            throw new FileNotFoundException("File not found with ID: " + fileId);
        }
    }

    public List<Document> getDocumentsByPhaseId(Integer phaseId) {
        return documentRepository.findByPhasesPhaseId(phaseId);
    }

    public List<String> getAllAlertMessages() {
        List<Document> documents = documentRepository.findAll();
        return documents.stream()
                .map(Document::getAlertMessage)
                .collect(Collectors.toList());
    }


}
