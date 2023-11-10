package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Document;
import net.javaguides.springboot.model.Inspection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InspectionRepository extends JpaRepository<Inspection, Integer> {

    Inspection findByFileName(String fileName);

    List<Inspection> findByPhases_PhaseName(String phaseName);
}
