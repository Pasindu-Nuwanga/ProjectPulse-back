package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Phase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableJpaRepositories
@Repository
public interface PhaseRepository extends JpaRepository<Phase, Integer> {
    Phase findByPhaseName(String phaseName);
    List<Phase> findByProjectProjectId(Integer projectId);
}

