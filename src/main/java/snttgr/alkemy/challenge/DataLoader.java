package snttgr.alkemy.challenge;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import snttgr.alkemy.challenge.model.Professor;
import snttgr.alkemy.challenge.model.SchoolClass;
import snttgr.alkemy.challenge.model.Student;
import snttgr.alkemy.challenge.model.repository.ProfessorRepository;
import snttgr.alkemy.challenge.model.repository.SchoolClassRepository;
import snttgr.alkemy.challenge.model.repository.StudentRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;


@Configuration
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

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {


            studentRepository.saveAll(
                    List.of(
                            new Student(123, "Santiago", "Nobili"),
                            new Student(321, "Amanda", "Bower"),
                            new Student(111, "Leonard", "Grant")));


            schoolClassRepository.saveAll(
                    List.of(
                            new SchoolClass("Intro to circuit design", LocalTime.MIDNIGHT, 10),
                            new SchoolClass("Advanced circuit design", LocalTime.NOON, 20),
                            new SchoolClass("Algorithms and data structures", LocalTime.of(16,30),1)));


            professorRepository.saveAll(
                    List.of(new Professor(12445618, "Jorji", "Costava"),
                            new Professor(12345678, "Leonhard", "Euler"),
                            new Professor(98765432, "Sir Jorge", "Doe")));


            if (Application.DEBUG) {

                Professor profTest = professorRepository.findAll().get(0);
                SchoolClass testClass = new SchoolClass("Code golf 101", LocalTime.of(1, 25), 5);
                testClass.setProfessor(profTest);
                SchoolClass testClass2 = new SchoolClass("Code golf 102", LocalTime.of(1, 25), 10);
                testClass2.setProfessor(profTest);

                schoolClassRepository.saveAll(List.of(testClass, testClass2));

                log.debug("-------------------------------------");

                Optional<Professor> profCheck = Optional.ofNullable(professorRepository.findAll().get(0));
                List<SchoolClass> classesCheck = (schoolClassRepository.findAll());

                if (profCheck.isPresent() && !classesCheck.isEmpty()) {
                    log.debug(profCheck.get() + "is teaching in: " + profCheck.get().getAssignedClasses());

                    for (var schoolClass : classesCheck) {
                        log.debug(schoolClass + "being taught by: " + schoolClass.getProfessor());
                    }
                }

                log.debug("-------------------------------------");


                Student student1 = studentRepository.findAll().get(0);
                Student student2 = studentRepository.findAll().get(1);
                Student student3 = studentRepository.findAll().get(2);

                student1.addClass(new SchoolClass("Bikeshedding", LocalTime.MIDNIGHT, 1));

                //NOTE: Why is this producing a duplicate class
                //student2.addClass(new SchoolClass("Computer", LocalTime.MIDNIGHT, 2));

                student2.addClass(testClass);
                student2.addClass(testClass2);

                student3.addClass(testClass);

                studentRepository.saveAll(List.of(student1, student2, student3));

                log.debug("-------------------------------------");

                for (var student : studentRepository.findAll()) {
                    log.debug(student + "is enrolled in " + student.getClasses());
                }


                log.debug(testClass2 + " has enrolled : " + testClass2.getEnrolledStudents());
                log.debug(testClass + " has enrolled : " + testClass.getEnrolledStudents());

                //NOTE: Persistence and Entity management is still a bit shaky, should probably read more

            }
        };
    }
}
