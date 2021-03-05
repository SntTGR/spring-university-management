package snttgr.alkemy.challenge.model;

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


    @OneToMany(
            mappedBy = "professor",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER     //TODO: Shouldn't use Eager, look into it later.
    )
    private List<SchoolClass> asignedClasses;


    public Professor() {
    }

    public Professor(int dni, String name, String surname) {
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
