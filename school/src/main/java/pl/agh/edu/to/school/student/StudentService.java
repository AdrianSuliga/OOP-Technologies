package pl.agh.edu.to.school.student;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;
import pl.agh.edu.to.school.course.Course;
import pl.agh.edu.to.school.grade.GradeService;

@Service
public class StudentService {

    private final GradeService gradeService;

    public StudentService(GradeService gradeService) {
        this.gradeService = gradeService;
        System.out.println("StudentService()");
    }

    @PostConstruct
    public void onServiceStarted() {
        System.out.println("StudentService.onServiceStarted()");
    }

    @PreDestroy
    public void onServiceDestroyed() {
        System.out.println("StudentService.onServiceDestroyed()");
    }

    public GradeService getGradeService() {
        return gradeService;
    }

    public void assignGrade(Student student, Course course, double gradeValue) {
        this.gradeService.assignGrade(student, course, gradeValue);
    }

}
