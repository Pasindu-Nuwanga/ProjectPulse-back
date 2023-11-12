package net.javaguides.springboot.web.dto;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;

public class InspectionRequestDto {
    private String inspectionName;
    private String phaseSection;
    private String phaseName;
    private String constructionType;
    private MultipartFile fileAttachment;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date inspectionRequestDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date inspectionDate;


    public InspectionRequestDto() {
    }

    public InspectionRequestDto(String inspectionName, String phaseSection, String phaseName, String constructionType, MultipartFile fileAttachment, Date inspectionRequestDate, Date inspectionDate) {
        this.inspectionName = inspectionName;
        this.phaseSection = phaseSection;
        this.phaseName = phaseName;
        this.constructionType = constructionType;
        this.fileAttachment = fileAttachment;
        this.inspectionRequestDate = inspectionRequestDate;
        this.inspectionDate = inspectionDate;
    }

    public Date getInspectionRequestDate() {
        return inspectionRequestDate;
    }

    public void setInspectionRequestDate(Date inspectionRequestDate) {
        this.inspectionRequestDate = inspectionRequestDate;
    }

    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public String getInspectionName() {
        return inspectionName;
    }

    public void setInspectionName(String inspectionName) {
        this.inspectionName = inspectionName;
    }

    public String getPhaseSection() {
        return phaseSection;
    }

    public void setPhaseSection(String phaseSection) {
        this.phaseSection = phaseSection;
    }

    public String getPhaseName() {
        return phaseName;
    }

    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }

    public String getConstructionType() {
        return constructionType;
    }

    public void setConstructionType(String constructionType) {
        this.constructionType = constructionType;
    }

    public MultipartFile getFileAttachment() {
        return fileAttachment;
    }

    public void setFileAttachment(MultipartFile fileAttachment) {
        this.fileAttachment = fileAttachment;
    }

    @Override
    public String toString() {
        return "InspectionRequestDto{" +
                "inspectionName='" + inspectionName + '\'' +
                ", phaseSection='" + phaseSection + '\'' +
                ", phaseName='" + phaseName + '\'' +
                ", constructionType='" + constructionType + '\'' +
                ", fileAttachment=" + fileAttachment +
                ", inspectionRequestDate=" + inspectionRequestDate +
                ", inspectionDate=" + inspectionDate +
                '}';
    }
}
