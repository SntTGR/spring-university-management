package snttgr.alkemy.challenge.model;

import java.util.List;

public class Student {

    //@id
    private Long id;
    private Integer dni;

    private String name;
    private String surname; //NOTE: should be kept?

    private List<SchoolClass> classes;


    public Student() {
    }

    public Student(int dni, String name, String surname, List<SchoolClass> classes) {
        this.dni = dni;
        this.name = name;
        this.surname = surname;
        this.classes = classes;
    }

    public Student(Long id, int dni, String name, String surname, List<SchoolClass> classes) {
        this.id = id;
        this.dni = dni;
        this.name = name;
        this.surname = surname;
        this.classes = classes;
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

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", dni=" + dni +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", classes=" + classes +
                '}';
    }
}
