package net.javaguides.springboot.web.dto;

import java.util.Date;

public class DocumentDto {
    private String documentId;
    private String documentName;
    private String documentDownloadUrl;
    private String documentType;
    private Long documentSize;
    private String phaseName;
    private Date uploadDate;
    private String alertMessage;

    public DocumentDto() {
    }

    public DocumentDto(String documentId, String documentName, String documentDownloadUrl, String documentType,
                       Long documentSize, String phaseName, Date uploadDate, String alertMessage) {
        this.documentId = documentId;
        this.documentName = documentName;
        this.documentDownloadUrl = documentDownloadUrl;
        this.documentType = documentType;
        this.documentSize = documentSize;
        this.phaseName = phaseName;
        this.uploadDate = uploadDate;
        this.alertMessage = alertMessage;
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

    public String getDocumentDownloadUrl() {
        return documentDownloadUrl;
    }

    public void setDocumentDownloadUrl(String documentDownloadUrl) {
        this.documentDownloadUrl = documentDownloadUrl;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public Long getDocumentSize() {
        return documentSize;
    }

    public void setDocumentSize(Long documentSize) {
        this.documentSize = documentSize;
    }

    public String getPhaseName() {
        return phaseName;
    }

    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
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

    @Override
    public String toString() {
        return "DocumentDto{" +
                "documentId='" + documentId + '\'' +
                ", documentName='" + documentName + '\'' +
                ", documentDownloadUrl='" + documentDownloadUrl + '\'' +
                ", documentType='" + documentType + '\'' +
                ", documentSize=" + documentSize +
                ", phaseName='" + phaseName + '\'' +
                ", uploadDate=" + uploadDate +
                ", alertMessage='" + alertMessage + '\'' +
                '}';
    }
}

