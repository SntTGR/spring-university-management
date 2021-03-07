package snttgr.alkemy.challenge.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import snttgr.alkemy.challenge.model.Student;
import snttgr.alkemy.challenge.services.SchoolClassService;
import snttgr.alkemy.challenge.services.StudentService;

@Controller
@RequestMapping("/user/{studentId}")
public class UserController {

    StudentService studentService;
    SchoolClassService schoolClassService;

    Student studentInSession = null;

    @Autowired
    public UserController(StudentService studentService, SchoolClassService schoolClassService) {
        this.studentService = studentService;
        this.schoolClassService = schoolClassService;
    }

    //TODO: Dropdown for class description

    @GetMapping
    public String greetStudent(Model model, @PathVariable Long studentId){
        //Greet student by name

        studentInSession = studentService.findById(studentId);

        //model.addAttribute("studentId", studentId);
        model.addAttribute("name",studentInSession.getName());
        model.addAttribute("classes", schoolClassService.getActiveClassesSorted());
        return "test";
    }



    //TODO: Add exceptions to both of these.
    @GetMapping("/enroll/{classId}")
    public String enrollInClass(@PathVariable Long studentId, @PathVariable Long classId){
        studentInSession = studentService.findById(studentId);

        studentInSession.addClass(schoolClassService.findClassById(classId));
        studentService.saveStudent(studentInSession);

        return "redirect:/user/{studentId}";
    }
    @GetMapping("/unenroll/{classId}")
    public String unenrollFromClass(@PathVariable Long studentId, @PathVariable Long classId){
        studentInSession = studentService.findById(studentId);

        studentInSession.removeClass(schoolClassService.findClassById(classId));
        studentService.saveStudent(studentInSession);

        return "redirect:/user/{studentId}";
    }

    //TODO: Student log out
}
