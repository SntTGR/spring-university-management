package snttgr.alkemy.challenge.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snttgr.alkemy.challenge.model.Student;


import snttgr.alkemy.challenge.model.repository.StudentRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service //bean component
public class StudentService {


    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findStudents(){ return studentRepository.findAll(); }
    public Student findById(Long id) throws NoSuchElementException{ return studentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("student with record:"+id+" not found in database")); }
    public void saveStudent(Student student){ studentRepository.save(student); }
}
