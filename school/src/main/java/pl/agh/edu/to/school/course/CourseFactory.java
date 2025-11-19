package pl.agh.edu.to.school.course;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.agh.edu.to.school.student.Student;

import java.time.LocalDate;

@Configuration
public class CourseFactory {

    public CourseFactory() {
        System.out.println("CourseFactory()");
    }

    @PostConstruct
    public void onServiceStarted() {
        System.out.println("CourseFactory.onServiceStarted()");
    }

    @PreDestroy
    public void onServiceDestroyed() {
        System.out.println("CourseFactory.onServiceDestroyed()");
    }

    @Bean
    public Course computerNetworksCourse() {
        var student = new Student("Piotr", "Budynek", LocalDate.of(1990, 11, 7),
                "22334455", "budynek@student.agh.edu.pl");
        var course = new Course("Sieci komputerowe");

        course.enrollStudent(student);
        return course;
    }

    @Bean
    public Course objectOrientedProgrammingCourse() {
        var student = new Student("Adam", "Izworskie", LocalDate.of(1980, 1, 12),
                "5544332211", "izworski@student.agh.edu.pl");
        var course = new Course("Programowanie obiektowe");

        course.enrollStudent(student);
        return course;
    }

}
