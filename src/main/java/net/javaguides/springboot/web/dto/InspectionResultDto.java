package net.javaguides.springboot.web.dto;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public class InspectionResultDto {

    private String inspectionName;
    private String phaseSection;
    private String phaseName;
    private String constructionType;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date inspectionResultDate;
    private String inspectionStatus;
    private String comments;
    private MultipartFile defectFileAttachment;

    public InspectionResultDto() {
    }

    public InspectionResultDto(String inspectionName, String phaseSection, String phaseName, String constructionType, Date inspectionResultDate, String inspectionStatus, String comments, MultipartFile defectFileAttachment) {
        this.inspectionName = inspectionName;
        this.phaseSection = phaseSection;
        this.phaseName = phaseName;
        this.constructionType = constructionType;
        this.inspectionResultDate = inspectionResultDate;
        this.inspectionStatus = inspectionStatus;
        this.comments = comments;
        this.defectFileAttachment = defectFileAttachment;
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

    public Date getInspectionResultDate() {
        return inspectionResultDate;
    }

    public void setInspectionResultDate(Date inspectionResultDate) {
        this.inspectionResultDate = inspectionResultDate;
    }

    public String getInspectionStatus() {
        return inspectionStatus;
    }

    public void setInspectionStatus(String inspectionStatus) {
        this.inspectionStatus = inspectionStatus;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public MultipartFile getDefectFileAttachment() {
        return defectFileAttachment;
    }

    public void setDefectFileAttachment(MultipartFile defectFileAttachment) {
        this.defectFileAttachment = defectFileAttachment;
    }

    @Override
    public String toString() {
        return "InspectionResultDto{" +
                "inspectionName='" + inspectionName + '\'' +
                ", phaseSection='" + phaseSection + '\'' +
                ", phaseName='" + phaseName + '\'' +
                ", constructionType='" + constructionType + '\'' +
                ", inspectionResultDate=" + inspectionResultDate +
                ", inspectionStatus='" + inspectionStatus + '\'' +
                ", comments='" + comments + '\'' +
                ", defectFileAttachment=" + defectFileAttachment +
                '}';
    }
}
