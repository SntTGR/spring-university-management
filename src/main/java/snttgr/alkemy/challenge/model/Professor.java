package snttgr.alkemy.challenge.model;

import java.util.List;

public class Professor {

    private long id;
    private int dni;

    private String name;
    private String surname; //NOTE: should be kept?

    private Boolean active; //NOTE: should be stored?

    private List<SchoolClass> schoolClasses;
}
