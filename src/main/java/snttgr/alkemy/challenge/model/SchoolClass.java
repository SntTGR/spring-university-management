package snttgr.alkemy.challenge.model;

import javax.persistence.*;
import java.time.LocalTime;
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

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    /*@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "enrolled_students",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "student_record_number")
    )*/
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
        return tickets;
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
}
