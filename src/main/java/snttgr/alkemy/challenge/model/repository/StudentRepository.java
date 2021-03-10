package snttgr.alkemy.challenge.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import snttgr.alkemy.challenge.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

}