package snttgr.alkemy.challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import snttgr.alkemy.challenge.model.Professor;
import snttgr.alkemy.challenge.model.SchoolClass;
import snttgr.alkemy.challenge.model.Student;
import snttgr.alkemy.challenge.repository.ProfessorRepository;
import snttgr.alkemy.challenge.repository.SchoolClassRepository;
import snttgr.alkemy.challenge.repository.StudentRepository;

import javax.annotation.PostConstruct;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;



@Component
public class DataLoader {


    private final StudentRepository studentRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final ProfessorRepository professorRepository;

    @Autowired
    public DataLoader(StudentRepository studentRepository, SchoolClassRepository schoolClassRepository, ProfessorRepository professorRepository) {
        this.studentRepository = studentRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.professorRepository = professorRepository;
    }


    @PostConstruct
    public void loadData(){
        studentRepository.saveAll(
                List.of(
                        new Student(123,"Santiago", "Nobili"),
                        new Student(321,"Alejo", "Lastra"),
                        new Student(512,"Ber", "Nande")));


        schoolClassRepository.saveAll(
                List.of(
                        new SchoolClass("Intro to circuit design", LocalTime.MIDNIGHT, 10),
                        new SchoolClass("Advanced circuit design", LocalTime.NOON, 20)));


        professorRepository.saveAll(
                List.of(
                        new Professor(999,"Jorge","Doe")));



        Professor profTest = professorRepository.findAll().get(0);
        SchoolClass testClass = new SchoolClass("Pipo 101", LocalTime.now(), 5);
        testClass.setProfessor(profTest);
        SchoolClass testClass2 = new SchoolClass("Pipo 102", LocalTime.now(), 10);
        testClass2.setProfessor(profTest);

        schoolClassRepository.saveAll(List.of(testClass, testClass2));

        System.out.println("-------------------------------------");

        Optional<Professor> profCheck = Optional.ofNullable(professorRepository.findAll().get(0));
        List<SchoolClass> classesCheck = (schoolClassRepository.findAll());

        if(profCheck.isPresent() && !classesCheck.isEmpty()){
            System.out.println(profCheck.get() + "is teaching in: " + profCheck.get().getAsignedClasses());

            for (var schoolclass: classesCheck) {
                System.out.println(schoolclass + "being teached by: " + schoolclass.getProfessor());
            }
        }

        System.out.println("-------------------------------------");


        Student student1 = studentRepository.findAll().get(0);
        Student student2 = studentRepository.findAll().get(1);
        Student student3 = studentRepository.findAll().get(2);


        student1.addClass(new SchoolClass("Bikesheding", LocalTime.MIDNIGHT, 1));
        student2.addClass(new SchoolClass("Computers", LocalTime.MIDNIGHT, 2));


        student2.addClass(testClass);
        student2.addClass(testClass2);

        student3.addClass(testClass);

        studentRepository.saveAll(List.of(student1, student2, student3));

        System.out.println("-------------------------------------");

        for (var student: studentRepository.findAll()) {
            System.out.println(student + "is enrolled in " + student.getClasses());
        }

        System.out.println(testClass2 + " has enrolled : " + testClass2.getEnrolledStudents());
        System.out.println(testClass + " has enrolled : " + testClass.getEnrolledStudents());

        //TODO: Persistence and Entity management is still a bit shaky, should read more
    }
}
