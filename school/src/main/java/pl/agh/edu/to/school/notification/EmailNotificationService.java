package pl.agh.edu.to.school.notification;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.agh.edu.to.school.grade.Grade;
import pl.agh.edu.to.school.student.Student;

@Service
@Profile("dev")
@DependsOn("greenMailHandler")
public class EmailNotificationService implements NotificationService {

    private final JavaMailSender mailSender;

    public EmailNotificationService(JavaMailSender mailSender) {
        System.out.println("EmailNotificationService()");
        this.mailSender = mailSender;
    }

    @PostConstruct
    public void onServiceStarted() {
        System.out.println("EmailNotificationService.onServiceStarted()");
    }

    @PreDestroy
    public void onServiceDestroyed() {
        System.out.println("EmailNotificationService.onServiceDestroyed()");
    }

    @Override
    public void notify(Student student, Grade grade) {
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setFrom("nauczyciel@agh.edu.pl");
        msg.setTo(student.getEmail());
        msg.setSubject("New grade: " + grade.course().getName());
        msg.setText("You received a new grade: " + grade.value());

        mailSender.send(msg);
    }

}
