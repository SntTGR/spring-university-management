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
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;


    //@Column(name = "dni", nullable = false)
    private int dni;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String name;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String surname; //NOTE: should be kept?

    @Transient
    private Boolean active; //NOTE: should be stored?


    @JsonIgnore
    @OneToMany(
            mappedBy = "professor",
            //cascade = CascadeType.ALL,
            fetch = FetchType.EAGER     //TODO: Shouldn't use Eager, look into it later.
    )
    private List<SchoolClass> asignedClasses;


    public Professor() {
    }

    public Professor(Professor oldProfessor){
        this.dni = oldProfessor.dni;
        this.name = oldProfessor.name;
        this.surname = oldProfessor.surname;
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
        return !getAsignedClasses().isEmpty();
    }

    public List<SchoolClass> getAsignedClasses() {
        return asignedClasses;
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

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setAsignedClasses(List<SchoolClass> asignedClasses) {
        this.asignedClasses = asignedClasses;
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
