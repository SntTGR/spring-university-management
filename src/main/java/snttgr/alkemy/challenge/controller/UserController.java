package snttgr.alkemy.challenge.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import snttgr.alkemy.challenge.exceptions.InvalidRequestException;
import snttgr.alkemy.challenge.model.SchoolClass;
import snttgr.alkemy.challenge.model.Student;
import snttgr.alkemy.challenge.services.SchoolClassService;
import snttgr.alkemy.challenge.services.StudentService;

@Controller
@RequestMapping("/user")
public class UserController {

    StudentService studentService;
    SchoolClassService schoolClassService;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(StudentService studentService, SchoolClassService schoolClassService) {
        this.studentService = studentService;
        this.schoolClassService = schoolClassService;
    }

    @GetMapping
    public String greetStudent(Model model, Authentication authentication){
        //Greet student by name
        Student studentInSession = studentService.findById(Long.parseLong(authentication.getName()));

        model.addAttribute("student", studentInSession);
        model.addAttribute("classes", schoolClassService.getActiveClassesSorted());
        return "studentPage";
    }



    @GetMapping("/enroll/{classId}")
    public String enrollInClass(@PathVariable Long classId, Authentication authentication) throws InvalidRequestException{

        Student studentInSession = studentService.findById(Long.parseLong(authentication.getName()));
        SchoolClass toEnroll = schoolClassService.findClassById(classId);
        if(!studentInSession.isEnrollable(toEnroll)) throw new InvalidRequestException("Student cant enroll in this class");

        //Checks only if student is already in class
        studentInSession.addClass(toEnroll);
        studentService.saveStudent(studentInSession);

        return "redirect:/user";
    }
    @GetMapping("/unenroll/{classId}")
    public String unenrollFromClass(@PathVariable Long classId, Authentication authentication){

        Student studentInSession = studentService.findById(Long.parseLong(authentication.getName()));

        studentInSession.removeClass(schoolClassService.findClassById(classId));
        studentService.saveStudent(studentInSession);

        return "redirect:/user";
    }
}
