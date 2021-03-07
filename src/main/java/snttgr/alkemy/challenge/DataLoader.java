package snttgr.alkemy.challenge;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import snttgr.alkemy.challenge.model.Professor;
import snttgr.alkemy.challenge.model.SchoolClass;
import snttgr.alkemy.challenge.model.Student;
import snttgr.alkemy.challenge.repository.ProfessorRepository;
import snttgr.alkemy.challenge.repository.SchoolClassRepository;
import snttgr.alkemy.challenge.repository.StudentRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


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
                            new Student(321, "Alejo", "Lastra"),
                            new Student(512, "Ber", "Nande")));


            schoolClassRepository.saveAll(
                    List.of(
                            new SchoolClass("Intro to circuit design", LocalTime.MIDNIGHT, 10),
                            new SchoolClass("Advanced circuit design", LocalTime.NOON, 20)));


            professorRepository.saveAll(
                    List.of(
                            new Professor(999, "Jorge", "Doe")));


            Professor profTest = professorRepository.findAll().get(0);
            SchoolClass testClass = new SchoolClass("Code golf 101", LocalTime.now(), 5);
            testClass.setProfessor(profTest);
            SchoolClass testClass2 = new SchoolClass("Code golf 102", LocalTime.now(), 10);
            testClass2.setProfessor(profTest);

            schoolClassRepository.saveAll(List.of(testClass, testClass2));

            log.debug("-------------------------------------");

            Optional<Professor> profCheck = Optional.ofNullable(professorRepository.findAll().get(0));
            List<SchoolClass> classesCheck = (schoolClassRepository.findAll());

            if (profCheck.isPresent() && !classesCheck.isEmpty()) {
                log.debug(profCheck.get() + "is teaching in: " + profCheck.get().getAsignedClasses());

                for (var schoolClass : classesCheck) {
                    log.debug(schoolClass + "being taught by: " + schoolClass.getProfessor());
                }
            }

            log.debug("-------------------------------------");


            Student student1 = studentRepository.findAll().get(0);
            Student student2 = studentRepository.findAll().get(1);
            Student student3 = studentRepository.findAll().get(2);



            student1.addClass(new SchoolClass("Bikeshedding", LocalTime.MIDNIGHT, 1));

            //TODO: Why is this producing a duplicate class
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

            //TODO: Persistence and Entity management is still a bit shaky, should read more
        };
    }
}
