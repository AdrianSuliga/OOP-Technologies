package pl.edu.agh.iisg.to.service;

import jakarta.transaction.Transaction;
import org.hibernate.Session;
import pl.edu.agh.iisg.to.dao.CourseDao;
import pl.edu.agh.iisg.to.dao.GradeDao;
import pl.edu.agh.iisg.to.dao.StudentDao;
import pl.edu.agh.iisg.to.model.Course;
import pl.edu.agh.iisg.to.model.Grade;
import pl.edu.agh.iisg.to.model.Student;
import pl.edu.agh.iisg.to.repository.StudentRepository;
import pl.edu.agh.iisg.to.session.TransactionService;

import java.util.*;

public class SchoolService {

    private final TransactionService transactionService;

    private final StudentRepository studentRepository;

    private final CourseDao courseDao;

    private final GradeDao gradeDao;

    public SchoolService(TransactionService transactionService, StudentRepository studentRepository, CourseDao courseDao, GradeDao gradeDao) {
        this.transactionService = transactionService;
        this.studentRepository = studentRepository;
        this.courseDao = courseDao;
        this.gradeDao = gradeDao;
    }

    public boolean enrollStudent(final Course course, final Student student) {
        return this.transactionService.doAsTransaction(() -> {
            if (course.studentSet().contains(student)) {
                return false;
            }

            course.studentSet().add(student);
            return true;
        }).orElse(false);
    }

    public boolean removeStudent(int indexNumber) {
        return this.transactionService.doAsTransaction(() -> {
            Optional<Student> student = studentRepository.getById(indexNumber);
            if (student.isEmpty()) {
                return true;
            }

            studentRepository.remove(student.get());
            return true;
        }).orElse(false);
    }

    public boolean gradeStudent(final Student student, final Course course, final float gradeValue) {
        return this.transactionService.doAsTransaction(() -> {
            Grade grade = new Grade(student, course, gradeValue);

            student.gradeSet().add(grade);
            course.gradeSet().add(grade);

            gradeDao.save(grade);

            return true;
        }).orElse(false);
    }

    public Map<String, List<Float>> getStudentGrades(String courseName) {
        Optional<Course> course = courseDao.findByName(courseName);

        if (course.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, List<Float>> result = new HashMap<>();

        for (Student student : course.get().studentSet()) {
            List<Float> grades = new ArrayList<>();

            for (Grade grade : student.gradeSet()) {
                grades.add(grade.grade());
            }

            result.put(student.firstName() + " " + student.lastName(), grades);
        }

        return result;
    }
}
