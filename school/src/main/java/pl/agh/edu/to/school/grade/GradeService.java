package pl.agh.edu.to.school.grade;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;
import pl.agh.edu.to.school.course.Course;
import pl.agh.edu.to.school.notification.ConsoleNotificationService;
import pl.agh.edu.to.school.notification.EmailNotificationService;
import pl.agh.edu.to.school.notification.NotificationService;
import pl.agh.edu.to.school.student.Student;

import javax.management.Notification;

@Service
public class GradeService {

    private final NotificationService notificationService;

    private final GradeBook gradeBook;

    public GradeService(GradeBook gradebook, NotificationService notificationService) {
        this.notificationService = notificationService;
        this.gradeBook = gradebook;
        System.out.println("GradeService()");
    }

    @PostConstruct
    public void onServiceStarted() {
        System.out.println("GradeService.onServiceStarted()");
    }

    @PreDestroy
    public void onServiceDestroyed() {
        System.out.println("GradeService.onServiceDestroyed()");
    }

    public GradeBook getGradeBook() {
        return gradeBook;
    }

    public void assignGrade(Student student, Course course, double gradeValue) {
        this.notificationService.notify(student, this.gradeBook.assignGrade(student, course, gradeValue));
    }

}
