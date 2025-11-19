package pl.agh.edu.to.school.grade;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;
import pl.agh.edu.to.school.course.Course;
import pl.agh.edu.to.school.student.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GradeBook {

    private final Map<String, List<Grade>> studentGrades = new HashMap<>();

    public GradeBook() {
        System.out.println("GradeBook()");
    }

    @PostConstruct
    public void onServiceStarted() {
        System.out.println("GradeBook.onServiceStarted()");
    }

    @PreDestroy
    public void onServiceDestroyed() {
        System.out.println("GradeBook.onServiceDestroyed()");
    }

    public Map<String, List<Grade>> getStudentGrades() {
        return studentGrades;
    }

    public Grade assignGrade(Student student, Course course, double gradeValue) {
        List<Grade> gradeList = studentGrades.computeIfAbsent(student.getIndexNumber(), _ -> new ArrayList<>());
        Grade grade = new Grade(course, gradeValue);

        gradeList.add(grade);
        return grade;
    }

}
