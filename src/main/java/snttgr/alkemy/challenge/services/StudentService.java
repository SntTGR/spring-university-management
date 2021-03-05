package snttgr.alkemy.challenge.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snttgr.alkemy.challenge.model.Student;

import snttgr.alkemy.challenge.Application;
import snttgr.alkemy.challenge.repository.StudentRepository;

import java.util.List;

@Service //bean component
public class StudentService {


    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    //TODO: Cleanup
    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public void saveStudent(Student student){
        studentRepository.save(student);
    }
}
