package snttgr.alkemy.challenge.model;

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
    @GeneratedValue(strategy=GenerationType.AUTO)
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

    public void addClass(SchoolClass c){
        classes.add(c);
        c.getEnrolledStudents().add(this);
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
