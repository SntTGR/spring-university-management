package snttgr.alkemy.challenge.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snttgr.alkemy.challenge.model.Professor;
import snttgr.alkemy.challenge.model.repository.ProfessorRepository;

import java.util.List;
import java.util.NoSuchElementException;

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


    public Professor findById(Long id) throws NoSuchElementException {
        return professorRepository.findById(id).orElseThrow(() -> new NoSuchElementException("professor with id:"+id+" not found in database"));
    }

    public void deleteById(Long id) throws NoSuchElementException {
        if (!professorRepository.existsById(id)) throw new NoSuchElementException("professor with id:"+id+" not found in database");
        professorRepository.deleteById(id);
    }
}
