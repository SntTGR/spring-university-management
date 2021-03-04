package snttgr.alkemy.challenge.services;

import org.springframework.stereotype.Service;
import snttgr.alkemy.challenge.model.Student;

import snttgr.alkemy.challenge.Application;

import java.util.List;

@Service //bean component
public class StudentService {

    //DEBUG:
    private static final List<Student> students = List.of(
            new Student(
                    10L, 10, "Santiago", "Nobili", null
            ),
            new Student(
                    11L, 20, "Alejo", "Lastra", null
            )
    );

    public List<Student> getStudents(){
        if (Application.DEBUG) return students;
        else return null;
    }
}
