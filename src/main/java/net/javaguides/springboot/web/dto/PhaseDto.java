package net.javaguides.springboot.web.dto;

public class PhaseDto {

    private Integer phaseId;
    private String phaseName;

    private Integer projectId;
    private String projectName;

    public PhaseDto() {
    }

    public PhaseDto(Integer phaseId, String phaseName, String projectName) {
        this.phaseId = phaseId;
        this.phaseName = phaseName;
        this.projectName = projectName;
    }

    public Integer getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(Integer phaseId) {
        this.phaseId = phaseId;
    }

    public String getPhaseName() {
        return phaseName;
    }

    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        return "PhaseDto{" +
                "phaseId=" + phaseId +
                ", phaseName='" + phaseName + '\'' +
                ", projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                '}';
    }
}
