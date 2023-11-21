package net.javaguides.springboot.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "phase")
public class Phase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="phase_id")
    private Integer phaseId;
    private String phaseName;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    private Project project;


    public Phase() {
    }

    public Phase(Integer phaseId, String phaseName, Project project) {
        this.phaseId = phaseId;
        this.phaseName = phaseName;
        this.project = project;
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "Phase{" +
                "phaseId=" + phaseId +
                ", phaseName='" + phaseName + '\'' +
                ", project=" + project +
                '}';
    }
}
