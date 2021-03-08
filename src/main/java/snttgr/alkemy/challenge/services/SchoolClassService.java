package snttgr.alkemy.challenge.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snttgr.alkemy.challenge.model.SchoolClass;
import snttgr.alkemy.challenge.repository.SchoolClassRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SchoolClassService {


    private final SchoolClassRepository schoolClassRepository;

    @Autowired
    public SchoolClassService(SchoolClassRepository schoolClassRepository) {
        this.schoolClassRepository = schoolClassRepository;
    }


    public List<SchoolClass> findClasses(){
        return schoolClassRepository.findAll();
    }


    public SchoolClass findClassById(Long id) throws NoSuchElementException { return schoolClassRepository.findById(id).orElseThrow(() -> new NoSuchElementException("schoolClass with id:"+id+" not found in database")); }
    public List<SchoolClass> findClassesSorted() { return schoolClassRepository.findAllByOrderByName(); }
    public List<SchoolClass> getActiveClassesSorted() { return schoolClassRepository.findAllByWhereProfessorIdIsNotNullOrderByNameDesc(); }

    public void save(SchoolClass formSchoolClass) {
        schoolClassRepository.save(formSchoolClass);
    }
    public void deleteById(Long classId) throws NoSuchElementException {
        SchoolClass toDelete = findClassById(classId);

        if (toDelete.getProfessor() != null) {
            toDelete.removeProfessor();
        }
        if (!toDelete.getEnrolledStudents().isEmpty()){
            toDelete.removeStudents();
        }

        schoolClassRepository.deleteById(classId);
    }
}
