package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
