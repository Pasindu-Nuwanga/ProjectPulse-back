package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Inspection;
import net.javaguides.springboot.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableJpaRepositories
@Repository
public interface ResultRepository extends JpaRepository<Result, Integer> {

    Result findByDefectFileName(String defectFileName);

    List<Result> findByInspectionsInspectionName(String inspectionName);

    List<Result> findByInspectionsInspectionId(Integer phaseId);
}
