package snttgr.alkemy.challenge.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snttgr.alkemy.challenge.model.SchoolClass;
import snttgr.alkemy.challenge.repository.SchoolClassRepository;

import java.util.List;

@Service
public class SchoolClassService {

    //TODO: Exceptions


    private final SchoolClassRepository schoolClassRepository;

    @Autowired
    public SchoolClassService(SchoolClassRepository schoolClassRepository) {
        this.schoolClassRepository = schoolClassRepository;
    }


    public List<SchoolClass> findClasses(){
        return schoolClassRepository.findAll();
    }

    //TODO: HANDLE EXCEPTIONS
    public SchoolClass findClassById(Long id) { return schoolClassRepository.findById(id).get(); }
    public List<SchoolClass> findClassesSorted() { return schoolClassRepository.findAllByOrderByName(); }
    public List<SchoolClass> getActiveClassesSorted() { return schoolClassRepository.findAllByWhereProfessorIdIsNotNullOrderByNameDesc(); }
    public List<SchoolClass> getActiveClasses(){
        return null;
    }
    public void save(SchoolClass formSchoolClass) {
        schoolClassRepository.save(formSchoolClass);
    }
    public void deleteById(Long classId) {
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
