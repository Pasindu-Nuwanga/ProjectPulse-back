package net.javaguides.springboot.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "document")
public class Document {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String documentId;
    private String documentName;
    private String documentType;

    //Large Object
    @Lob
    private byte[] data;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "phase_id", referencedColumnName = "phase_id")
    private Phase phases;


    public Document() {
    }

    public Document(String documentName, String documentType, byte[] data, Phase phases) {
        this.documentName = documentName;
        this.documentType = documentType;
        this.data = data;
        this.phases = phases;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Phase getPhases() {
        return phases;
    }

    public void setPhases(Phase phases) {
        this.phases = phases;
    }

    @Override
    public String toString() {
        return "Document{" +
                "documentId='" + documentId + '\'' +
                ", documentName='" + documentName + '\'' +
                ", documentType='" + documentType + '\'' +
                ", data=" + Arrays.toString(data) +
                ", phases=" + phases +
                '}';
    }
}
