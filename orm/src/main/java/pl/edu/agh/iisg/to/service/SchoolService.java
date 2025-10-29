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
import java.util.stream.Collectors;

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

    public boolean removeStudent(int indexNumber) {
        return transactionService.doAsTransaction(() -> {
            Optional<Student> studentOpt = studentRepository.findByIndexNumber(indexNumber);
            if (studentOpt.isEmpty()) {
                return false;
            }

            Student student = studentOpt.get();

            studentRepository.remove(student);
            return true;
        }).orElse(false);
    }

    public boolean enrollStudent(final Course course, final Student student) {
        return this.transactionService.doAsTransaction(() -> {
            if (course.studentSet().contains(student)) {
                return false;
            }

            course.studentSet().add(student);
            student.courseSet().add(course);

            courseDao.save(course);
            studentRepository.add(student);

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
        List<Student> students = studentRepository.findAllByCourseName(courseName);
        if (students.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, List<Float>> result = new HashMap<>();

        for (Student s : students) {
            List<Float> grades = s.gradeSet()
                    .stream()
                    .filter(g -> g.course().name().equals(courseName))
                    .map(Grade::grade)
                    .sorted()
                    .collect(Collectors.toList());
            result.put(s.fullName(), grades);
        }

        return result;
    }
}
