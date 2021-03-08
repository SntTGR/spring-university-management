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

        model.addAttribute("classes", schoolClassService.findClasses());

        return "adminClasses";
    }


    @GetMapping("/classes/add")
    public String addClass(Model model){
        model.addAttribute("operation", "Adding class");
        SchoolClass schoolClass = new SchoolClass();
        model.addAttribute("schoolClass", schoolClass);
        return "adminClassRegister";
    }

    @GetMapping("/classes/edit/{classId}")
    public String editClass(Model model, @PathVariable Long classId){
        model.addAttribute("operation", "Edit class");
        model.addAttribute("schoolClass", schoolClassService.findClassById(classId));

        return "adminClassRegister";
    }


    @PostMapping("/classes/save")
    public String saveClass(@ModelAttribute("schoolClass") SchoolClass formSchoolClass) throws InvalidRequestException {
        //only validation check is if they are not blank and not null
        if( formSchoolClass.getStartTime()  == null ||
            formSchoolClass.getName()       == null ||
            formSchoolClass.getName().isBlank()) throw new InvalidRequestException("School class invalid form");

        if (formSchoolClass.getId() != null) {
            formSchoolClass.setProfessor(schoolClassService.findClassById(formSchoolClass.getId()).getProfessor());
        }

        schoolClassService.save(formSchoolClass);
        return "redirect:/admin/classes";
    }

    @GetMapping("/classes/delete/{classId}")
    public String deleteClass(@PathVariable Long classId){
        schoolClassService.deleteById(classId);
        return "redirect:/admin/classes";
    }

    @GetMapping("/classes/assign/{classId}")
    public String assignClass(@PathVariable Long classId, Model model){
        model.addAttribute("professors", professorService.findAllProfessors());
        return "adminProfessorsAssignment";
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


        model.addAttribute("professors", professorService.findAllProfessors());

        return "adminProfessors";
    }

    @GetMapping("/professors/add")
    public String addProfessor(Model model){
        model.addAttribute("operation", "Adding professor");
        Professor professor = new Professor();
        model.addAttribute("professor", professor);
        return "adminProfessorRegister";
    }

    @GetMapping("/professors/edit/{professorId}")
    public String editProfessor(Model model, @PathVariable Long professorId){
        model.addAttribute("operation", "Edit professor");
        model.addAttribute(professorService.findById(professorId));

        return "adminProfessorRegister";
    }



    @PostMapping("/professors/save")
    public String saveProfessor(@ModelAttribute("professor") Professor formProfessor) throws InvalidRequestException{

        //only validation check is if they are not blank and not null
        if( formProfessor.getSurname()      == null ||
            formProfessor.getSurname().isBlank()    ||
            formProfessor.getName()         == null ||
            formProfessor.getName().isBlank())
                throw new InvalidRequestException("Professor invalid form");


        if (formProfessor.getId() != null) {
            formProfessor.setAssignedClasses(professorService.findById(formProfessor.getId()).getAssignedClasses());
        }

        //System.out.println(toSave + " - " + formProfessor);

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
