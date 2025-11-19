package pl.agh.edu.to.school;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import pl.agh.edu.to.school.course.Course;
import pl.agh.edu.to.school.student.Student;
import pl.agh.edu.to.school.student.StudentService;

import java.time.LocalDate;
import java.util.List;

@Service
public class SchoolInitializer {

    @Value("${school.app.version}")
    private String appVersion;

    private final StudentService studentService;

    private final Course computerNetworksCourse;
    private final Course objectOrientedProgrammingCourse;
    private final List<Course> allCourses;

    public SchoolInitializer(StudentService studentService, Course computerNetworksCourse,
                             Course objectOrientedProgrammingCourse, List<Course> allCourses) {
        this.studentService = studentService;

        Student student = new Student("Dale", "Cooper",
                LocalDate.of(1960, 5, 12), "419780", "a@b.c");

        Course course = new Course("MOwNiT");

        studentService.assignGrade(student, course, 3.5);
        this.computerNetworksCourse = computerNetworksCourse;
        this.objectOrientedProgrammingCourse = objectOrientedProgrammingCourse;
        this.allCourses = allCourses;
    }

    @PostConstruct
    public void onServiceStarted() {
        System.out.println("SchoolInitializer.onServiceStarted() [VERSION " + appVersion + "]");
    }

    @PostConstruct
    public void initComputerNetworksCourse() {
        computerNetworksCourse.getStudents().forEach(student ->
                studentService.assignGrade(student, computerNetworksCourse, 4.5));
    }

    @PostConstruct
    public void initObjectOrientedProgrammingCourse() {
        objectOrientedProgrammingCourse.getStudents().forEach(student ->
                studentService.assignGrade(student, objectOrientedProgrammingCourse, 4));
    }

    @PostConstruct
    public void initAllCourses() {
        allCourses.forEach(course ->
                course.getStudents().forEach(student -> studentService.assignGrade(student, course, 5.0)));
    }

    @PreDestroy
    public void onServiceDestroyed() {
        System.out.println("SchoolInitializer.onServiceDestroyed()");
    }

}
