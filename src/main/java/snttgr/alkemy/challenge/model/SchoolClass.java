package snttgr.alkemy.challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Class")
@Table(name="class")
public class SchoolClass {

    @Id
    @SequenceGenerator(
            name="class_sequence",
            sequenceName = "class_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy=GenerationType.SEQUENCE,
            generator = "class_sequence"
    )
    @Column(
            name="id",
            updatable = false
    )
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String name;

    @Column(nullable = false)
    private LocalTime startTime;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;


    @JsonIgnore
    @ManyToMany(mappedBy = "classes",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER)
    private List<Student> enrolledStudents = new ArrayList<>();

    @Column(nullable = false)
    private int maxTickets;

    public SchoolClass() {
    }
    public SchoolClass(String name, LocalTime startTime, int maxTickets) {
        this.name = name;
        this.startTime = startTime;
        this.maxTickets = maxTickets;
    }
    public SchoolClass(Long id, String name, LocalTime startTime, int maxTickets) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.maxTickets = maxTickets;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public Professor getProfessor() {
        return professor;
    }
    public int getTickets() {
        return getMaxTickets() - getEnrolledStudents().size();
    }
    public int getMaxTickets() {
        return maxTickets;
    }
    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    public void setMaxTickets(int maxTickets) {
        this.maxTickets = maxTickets;
    }

    public boolean isEnrolled(Long id){
        for (var student: getEnrolledStudents()) {
            if (student.getId().equals(id)) return true;
        }
        return false;
    }




    public void setEnrolledStudents(List<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }
    public void addEnrolledStudent(Student enrolledStudent) {
        enrolledStudents.add(enrolledStudent);
        enrolledStudent.getClasses().add(this);
    }

    @Override
    public String toString() {
        return "SchoolClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public void removeProfessor() {
        getProfessor().getAssignedClasses().removeIf(schoolClass -> schoolClass.getId().equals(this.getId()));
        this.professor = null;
    }
    public void removeStudents() {

        for (var student: getEnrolledStudents()) {
            student.getClasses().removeIf(schoolClass -> schoolClass.getId().equals(this.getId()));
        }

        getEnrolledStudents().clear();
    }
}
