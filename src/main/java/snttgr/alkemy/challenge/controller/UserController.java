package snttgr.alkemy.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import snttgr.alkemy.challenge.model.Student;
import snttgr.alkemy.challenge.services.StudentService;

import java.util.List;

@RestController
@RequestMapping("/user-id")
public class UserController {

    private final StudentService studentService;

    @Autowired
    public UserController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping
    public List<Student> students(){
        return studentService.getStudents();
    }
}
