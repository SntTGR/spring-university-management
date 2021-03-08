package snttgr.alkemy.challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import snttgr.alkemy.challenge.exceptions.InvalidRequestException;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name="dni_unique", columnNames = "dni")
        }
)
public class Student {


    @Id
    @SequenceGenerator(
            name="student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy=GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    @Column(
            name="record_number",
            updatable = false
    )
    private Long id;


    @Column(name = "dni",
            nullable = false)
    private Integer dni;


    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;
    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String surname; //NOTE: should be kept?


    /*@ManyToMany(mappedBy = "enrolledStudents",
                cascade = CascadeType.ALL,
                fetch = FetchType.EAGER)*/
    @Column(nullable = false)
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "student_class",
            joinColumns = @JoinColumn(name = "student_record_number"),
            inverseJoinColumns = @JoinColumn(name = "class_id")
    )
    private List<SchoolClass> classes = new ArrayList<>();


    public Student() {
    }
    public Student(int dni, String name, String surname) {
        this.dni = dni;
        this.name = name;
        this.surname = surname;
    }
    public Student(Long id, int dni, String name, String surname) {
        this.id = id;
        this.dni = dni;
        this.name = name;
        this.surname = surname;
    }

    public Long getId() {
        return id;
    }
    public Integer getDni() {
        return dni;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public List<SchoolClass> getClasses() {
        return classes;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setDni(Integer dni) {
        this.dni = dni;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setClasses(List<SchoolClass> classes) {
        this.classes = classes;
    }

    public boolean isEnrollable(SchoolClass c){
        return c.getTickets() > 0 && getClasses().stream().noneMatch(schoolClass -> schoolClass.getStartTime().equals(c.getStartTime()));
    }
    public void addClass(SchoolClass c) throws InvalidRequestException {
        if(c.isEnrolled(id)) throw new InvalidRequestException("Student cant enroll in this class");
        classes.add(c);
        c.getEnrolledStudents().add(this);
    }
    public void removeClass(SchoolClass classById) {
        for (var schoolClass : classes) {
            if (schoolClass.getId().equals(classById.getId())){
                schoolClass.getEnrolledStudents().removeIf(student -> student.getId().equals(this.getId()));

                classes.remove(schoolClass);
                break;
            }
        }
    }


    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", dni=" + dni +
                ", name='" + name + '\'' +
                '}';
    }

}
