package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Inspection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InspectionRepository extends JpaRepository<Inspection, Integer> {

}
