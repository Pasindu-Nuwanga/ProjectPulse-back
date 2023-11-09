package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    Project findByProjectName(String projectName);

    Project save(Project project);
}
