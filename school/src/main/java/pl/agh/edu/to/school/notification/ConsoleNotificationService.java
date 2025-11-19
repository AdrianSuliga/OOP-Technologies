package pl.agh.edu.to.school.notification;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.agh.edu.to.school.grade.Grade;
import pl.agh.edu.to.school.student.Student;

@Service
@Profile("test")
public class ConsoleNotificationService implements NotificationService {

    public ConsoleNotificationService() {
        System.out.println("ConsoleNotificationService()");
    }

    @PostConstruct
    public void onServiceStarted() {
        System.out.println("ConsoleNotificationService.onServiceStarted()");
    }

    @PreDestroy
    public void onServiceDestroyed() {
        System.out.println("ConsoleNotificationService.onServiceDestroyed()");
    }

    @Override
    public void notify(Student student, Grade grade) {
        System.out.println(student.getFullName() + " received grade " + grade.getValue() + " on " + grade.getCourse().getName() + " course");
    }

}
