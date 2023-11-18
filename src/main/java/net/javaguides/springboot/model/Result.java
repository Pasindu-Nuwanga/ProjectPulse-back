//Inspection Result Entity
package net.javaguides.springboot.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;

@Entity
@Table(name = "result")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="result_id")
    private Integer resultId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date inspectionResultDate;
    private String inspectionStatus;
    @Lob
    private byte[] defectFileAttachment;

    private String defectFileName;

    private String comments;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "inspection_id", referencedColumnName = "inspection_id")
    private Inspection inspections;

    public Result() {
    }

    public Result(Date inspectionResultDate, String inspectionStatus, byte[] defectFileAttachment, String defectFileName, String comments, Inspection inspections) {
        this.inspectionResultDate = inspectionResultDate;
        this.inspectionStatus = inspectionStatus;
        this.defectFileAttachment = defectFileAttachment;
        this.defectFileName = defectFileName;
        this.comments = comments;
        this.inspections = inspections;
    }

    public Integer getResultId() {
        return resultId;
    }

    public void setResultId(Integer resultId) {
        this.resultId = resultId;
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

    public byte[] getDefectFileAttachment() {
        return defectFileAttachment;
    }

    public void setDefectFileAttachment(byte[] defectFileAttachment) {
        this.defectFileAttachment = defectFileAttachment;
    }

    public String getDefectFileName() {
        return defectFileName;
    }

    public void setDefectFileName(String defectFileName) {
        this.defectFileName = defectFileName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Inspection getInspections() {
        return inspections;
    }

    public void setInspections(Inspection inspections) {
        this.inspections = inspections;
    }

    @Override
    public String toString() {
        return "Result{" +
                "resultId=" + resultId +
                ", inspectionResultDate=" + inspectionResultDate +
                ", inspectionStatus='" + inspectionStatus + '\'' +
                ", defectFileAttachment=" + Arrays.toString(defectFileAttachment) +
                ", defectFileName='" + defectFileName + '\'' +
                ", comments='" + comments + '\'' +
                ", inspections=" + inspections +
                '}';
    }
}
