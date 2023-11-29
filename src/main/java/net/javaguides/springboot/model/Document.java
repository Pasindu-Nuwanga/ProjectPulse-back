package net.javaguides.springboot.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;

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

    @Column(name = "upload_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date uploadDate;

    private String alertMessage;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "phase_id", referencedColumnName = "phase_id")
    private Phase phases;


    public Document() {
    }

    public Document(String documentName, String documentType, byte[] data, Date uploadDate, String alertMessage, Phase phases) {
        this.documentName = documentName;
        this.documentType = documentType;
        this.data = data;
        this.uploadDate = uploadDate;
        this.alertMessage = alertMessage;
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

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
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
                ", uploadDate=" + uploadDate +
                ", alertMessage='" + alertMessage + '\'' +
                ", phases=" + phases +
                '}';
    }
}
