package pl.agh.edu.to.school.notification;

import pl.agh.edu.to.school.grade.Grade;
import pl.agh.edu.to.school.student.Student;

public interface NotificationService {

    void notify(Student student, Grade grade);

}
