package snttgr.alkemy.challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Class")
@Table(name="class")
public class SchoolClass {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;
    private LocalTime startTime;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    /*@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "enrolled_students",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "student_record_number")
    )*/
    @JsonIgnore
    @ManyToMany(mappedBy = "classes",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER)
    private List<Student> enrolledStudents = new ArrayList<>();

    @Transient //should be transient?
    private int tickets;

    private int maxTickets;

    public SchoolClass() {
    }

    public SchoolClass(String name, LocalTime startTime, int maxTickets) {
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
    /*public void addEnrolledStudent(Student enrolledStudent)
    {
        enrolledStudents.add(enrolledStudent);
        enrolledStudent.getClasses().add(this);
    }*/

    @Override
    public String toString() {
        return "SchoolClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public void removeProfessor() {
        getProfessor().getAsignedClasses().removeIf(schoolClass -> schoolClass.getId().equals(this.getId()));
        this.professor = null;
    }
    public void removeStudents() {

        for (var student: getEnrolledStudents()) {
            student.getClasses().removeIf(schoolClass -> schoolClass.getId().equals(this.getId()));
        }

        getEnrolledStudents().clear();
    }
}
