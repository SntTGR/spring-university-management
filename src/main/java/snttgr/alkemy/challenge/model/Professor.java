package snttgr.alkemy.challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="professors",
        uniqueConstraints = {@UniqueConstraint(name="dni_unique", columnNames = "dni")}
        )
public class Professor {

    @Id
    @SequenceGenerator(
            name="professor_sequence",
            sequenceName = "professor_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy=GenerationType.SEQUENCE,
            generator = "professor_sequence"
    )
    private Long id;


    @Column(name = "dni", nullable = false)
    private int dni;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String name;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String surname; //NOTE: should be kept?

    @JsonIgnore
    @OneToMany(
            mappedBy = "professor",
            //cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
            //NOTE: Shouldn't use Eager, look into it later.
    )
    private List<SchoolClass> assignedClasses;


    public Professor() {
    }
    public Professor(int dni, String name, String surname) {
        this.dni = dni;
        this.name = name;
        this.surname = surname;
    }
    public Professor(Long id, int dni, String name, String surname) {
        this.id = id;
        this.dni = dni;
        this.name = name;
        this.surname = surname;
    }



    public Long getId() {
        return id;
    }
    public int getDni() {
        return dni;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public Boolean getActive() {
        return !getAssignedClasses().isEmpty();
    }
    public List<SchoolClass> getAssignedClasses() {
        return assignedClasses;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setDni(int dni) {
        this.dni = dni;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAssignedClasses(List<SchoolClass> assignedClasses) {
        this.assignedClasses = assignedClasses;
    }


    @Override
    public String toString() {
        return "Professor{" +
                "id=" + id +
                ", dni=" + dni +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
