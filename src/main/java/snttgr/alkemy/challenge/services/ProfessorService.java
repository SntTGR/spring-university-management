package snttgr.alkemy.challenge.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snttgr.alkemy.challenge.model.Professor;
import snttgr.alkemy.challenge.repository.ProfessorRepository;

import java.util.List;

@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    @Autowired
    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }


    public List<Professor> findAllProfessors(){
        return professorRepository.findAll();
    }

    public void save(Professor professor){
        professorRepository.save(professor);
    }

    //TODO: Exceptions
    public Professor findById(Long id){
        return professorRepository.findById(id).get();
    }

    public void deleteById(Long id){
        professorRepository.deleteById(id);
    }
}
