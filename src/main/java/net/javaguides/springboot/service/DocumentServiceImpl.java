package net.javaguides.springboot.service;

import net.javaguides.springboot.exception.FileNotFoundException;
import net.javaguides.springboot.exception.FileStorageException;
import net.javaguides.springboot.model.Document;
import net.javaguides.springboot.model.Phase;
import net.javaguides.springboot.model.Project;
import net.javaguides.springboot.repository.DocumentRepository;
import net.javaguides.springboot.repository.PhaseRepository;
import net.javaguides.springboot.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DocumentServiceImpl{

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private PhaseRepository phaseRepository;


    public Document storeFile(MultipartFile file, String phaseName) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Document dbFile = new Document();
            dbFile.setDocumentName(fileName);
            dbFile.setDocumentType(file.getContentType());

            // Find phase by their IDs
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

}
