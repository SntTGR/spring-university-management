package snttgr.alkemy.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import snttgr.alkemy.challenge.model.SchoolClass;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass,Long> {

}
