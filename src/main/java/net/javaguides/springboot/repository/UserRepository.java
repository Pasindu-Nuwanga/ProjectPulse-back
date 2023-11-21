package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Phase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.model.User;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	User findByEmail(String email);
	Optional<User> findOneByEmailAndPassword(String email, String password);

	List<User> findByProjectsProjectId(Integer projectId);

}
