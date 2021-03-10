package snttgr.alkemy.challenge.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import snttgr.alkemy.challenge.model.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor,Long> {

}
