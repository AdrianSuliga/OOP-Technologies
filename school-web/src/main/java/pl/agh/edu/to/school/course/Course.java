package pl.agh.edu.to.school.course;

import jakarta.persistence.*;
import pl.agh.edu.to.school.student.Student;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> students = new ArrayList<>() ;

    public Course() {

    }

    public Course(String name) {
        this.name = name;
    }

    public void assignStudent(Student student) {
        students.add(student);
        student.getCourses().add(this);
    }

}
