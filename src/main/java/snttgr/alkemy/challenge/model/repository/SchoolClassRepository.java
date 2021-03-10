package snttgr.alkemy.challenge.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import snttgr.alkemy.challenge.model.SchoolClass;

import java.util.List;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass,Long> {

    // NOTE: docs say that nativeQuery dynamic ordering is not supported. Using JPA's query syntax

    // SELECT * FROM class ORDER BY name")
    @Query("select c from Class as c order by c.name")
    List<SchoolClass> findAllByOrderByName();

    // SELECT * FROM class WHERE professor_id IS NOT NULL ORDER BY name
    @Query("select c from Class as c where c.professor is not null order by c.name")
    List<SchoolClass> findAllByWhereProfessorIdIsNotNullOrderByNameDesc();

}
