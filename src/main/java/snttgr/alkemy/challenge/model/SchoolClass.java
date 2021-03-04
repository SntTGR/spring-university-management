package snttgr.alkemy.challenge.model;

import java.time.LocalTime;
import java.util.List;

public class SchoolClass {

    //@id
    private long id;
    private String name;

    private LocalTime startTime;

    private Professor professor;

    private List<Student> enrolledStudents = null;
    private int maxTickets;




    @Override
    public String toString() {
        return "SchoolClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startTime=" + startTime +
                ", professor=" + professor +
                ", enrolledStudents=" + enrolledStudents +
                ", maxTickets=" + maxTickets +
                '}';
    }
}
