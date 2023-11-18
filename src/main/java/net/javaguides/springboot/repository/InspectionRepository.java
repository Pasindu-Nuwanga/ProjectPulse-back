package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Document;
import net.javaguides.springboot.model.Inspection;
import net.javaguides.springboot.model.Phase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
@EnableJpaRepositories
@Repository
public interface InspectionRepository extends JpaRepository<Inspection, Integer> {

    Inspection findByFileName(String fileName);

    List<Inspection> findByInspectionName(String inspectionName);

    List<Inspection> findByPhasesPhaseId(Integer phaseId);

}
