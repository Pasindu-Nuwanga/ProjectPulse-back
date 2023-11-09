package net.javaguides.springboot.web.dto;

public class ProjectDto {

    private Integer projectId;
    private String projectName;

    public ProjectDto() {
    }

    public ProjectDto(Integer projectId, String projectName) {
        this.projectId = projectId;
        this.projectName = projectName;
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
        return "ProjectDto{" +
                "projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                '}';
    }
}
