package net.javaguides.springboot.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;

@Entity
@Table(name = "inspection")
public class Inspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="inspection_id")
    private Integer inspectionId;
    private String inspectionName;
    private String phaseSection;
    private String constructionType;
    private Date inspectionRequestDate;
    private Date inspectionDate;

    @Lob
    private byte[] fileAttachment;

    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "phase_id", referencedColumnName = "phase_id")
    private Phase phases;

    public Inspection() {
    }

    public Inspection(String inspectionName, String phaseSection, String constructionType, Date inspectionRequestDate, Date inspectionDate, byte[] fileAttachment, String fileName, Phase phases) {
        this.inspectionName = inspectionName;
        this.phaseSection = phaseSection;
        this.constructionType = constructionType;
        this.inspectionRequestDate = inspectionRequestDate;
        this.inspectionDate = inspectionDate;
        this.fileAttachment = fileAttachment;
        this.fileName = fileName;
        this.phases = phases;
    }

    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public Integer getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(Integer inspectionId) {
        this.inspectionId = inspectionId;
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

    public String getConstructionType() {
        return constructionType;
    }

    public void setConstructionType(String constructionType) {
        this.constructionType = constructionType;
    }

    public Date getInspectionRequestDate() {
        return inspectionRequestDate;
    }

    public void setInspectionRequestDate(Date inspectionRequestDate) {
        this.inspectionRequestDate = inspectionRequestDate;
    }

    public byte[] getFileAttachment() {
        return fileAttachment;
    }

    public void setFileAttachment(byte[] fileAttachment) {
        this.fileAttachment = fileAttachment;
    }

    public Phase getPhases() {
        return phases;
    }

    public void setPhases(Phase phases) {
        this.phases = phases;
    }

    @Override
    public String toString() {
        return "Inspection{" +
                "inspectionId=" + inspectionId +
                ", inspectionName='" + inspectionName + '\'' +
                ", phaseSection='" + phaseSection + '\'' +
                ", constructionType='" + constructionType + '\'' +
                ", inspectionDate=" + inspectionRequestDate +
                ", data=" + Arrays.toString(fileAttachment) +
                ", phases=" + phases +
                '}';
    }


}
