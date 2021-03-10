package snttgr.alkemy.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import snttgr.alkemy.challenge.exceptions.InvalidRequestException;
import snttgr.alkemy.challenge.model.Professor;
import snttgr.alkemy.challenge.model.SchoolClass;
import snttgr.alkemy.challenge.services.ProfessorService;
import snttgr.alkemy.challenge.services.SchoolClassService;


import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final SchoolClassService schoolClassService;
    private final ProfessorService professorService;

    @Autowired
    public AdminController(SchoolClassService schoolClassService, ProfessorService professorService) {
        this.schoolClassService = schoolClassService;
        this.professorService = professorService;
    }

    @GetMapping()
    public String redirectToClasses(){
        return "redirect:/admin/classes";
    }

    @GetMapping("/classes")
    public String schoolClassesIndex(Model model){

        model.addAttribute("newSchoolClass", new SchoolClass());
        model.addAttribute("classes", schoolClassService.findClasses());
        return "admin/classes";
    }

    @PostMapping("/classes/save")
    public String saveClass(@ModelAttribute("schoolClass") SchoolClass schoolClass) throws InvalidRequestException {
        //only validation check is if they are not blank and not null
        if( schoolClass.getStartTime()  == null ||
            schoolClass.getName()       == null ||
            schoolClass.getName().isBlank()) throw new InvalidRequestException("School class invalid form");

        if (schoolClass.getId() != null) {
            schoolClass.setProfessor(schoolClassService.findClassById(schoolClass.getId()).getProfessor());
        }

        schoolClassService.save(schoolClass);
        return "redirect:/admin/classes";
    }

    @GetMapping("/classes/delete/{classId}")
    public String deleteClass(@PathVariable Long classId){
        schoolClassService.deleteById(classId);
        return "redirect:/admin/classes";
    }

    @GetMapping("/classes/assign/{classId}")
    public String assignClass(@PathVariable Long classId, Model model){
        model.addAttribute("classId",classId);
        model.addAttribute("professors", professorService.findAllProfessors());
        return "admin/professorsAssignment";
    }

    @GetMapping("/classes/assign/{classId}/{professorId}")
    public String assignClassProfessor(@PathVariable Long classId, @PathVariable Long professorId){

        SchoolClass schoolClass = schoolClassService.findClassById(classId);

        if(professorId != 0){
            Professor professor = professorService.findById(professorId);
            schoolClass.setProfessor(professor);
        }else{
            schoolClass.setProfessor(null);
        }

        schoolClassService.save(schoolClass);
        return "redirect:/admin/classes";
    }






    //NOTE: should extract it to another controller?

    @GetMapping("/professors")
    public String professorsIndex(Model model){

        model.addAttribute("newProfessor", new Professor());
        model.addAttribute("professors", professorService.findAllProfessors());

        return "admin/professors";
    }


    @PostMapping("/professors/save")
    public String saveProfessor(@ModelAttribute("professor") Professor formProfessor) throws InvalidRequestException{

        //only validation check is if they are not blank and not null
        if( formProfessor.getSurname()      == null ||
            formProfessor.getSurname().isBlank()    ||
            formProfessor.getName()         == null ||
            formProfessor.getName().isBlank()       ||
            formProfessor.getDni() <= 0)
                throw new InvalidRequestException("Professor invalid form");


        if (formProfessor.getId() != null) {
            formProfessor.setAssignedClasses(professorService.findById(formProfessor.getId()).getAssignedClasses());
        }



        professorService.save(formProfessor);

        return "redirect:/admin/professors";
    }

    @GetMapping("/professors/delete/{professorId}")
    public String deleteProfessor(@PathVariable Long professorId){


        List<SchoolClass> classes = professorService.findById(professorId).getAssignedClasses();
        for (var schoolClass : classes) {
            schoolClass.setProfessor(null);
        }

        professorService.deleteById(professorId);

        return "redirect:/admin/professors";
    }
}
