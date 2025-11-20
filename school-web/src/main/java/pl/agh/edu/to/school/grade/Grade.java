package pl.agh.edu.to.school.grade;

import jakarta.persistence.*;
import pl.agh.edu.to.school.course.Course;
import pl.agh.edu.to.school.student.Student;

@Entity
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double gradeValue;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Grade() {

    }

    public Grade(double gradeValue, Course course) {
        this.gradeValue = gradeValue;
        this.course = course;
    }
}
