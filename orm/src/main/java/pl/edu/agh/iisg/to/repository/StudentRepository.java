package pl.edu.agh.iisg.to.repository;

import pl.edu.agh.iisg.to.dao.CourseDao;
import pl.edu.agh.iisg.to.dao.GradeDao;
import pl.edu.agh.iisg.to.dao.StudentDao;
import pl.edu.agh.iisg.to.model.Course;
import pl.edu.agh.iisg.to.model.Grade;
import pl.edu.agh.iisg.to.model.Student;
import pl.edu.agh.iisg.to.session.TransactionService;

import java.util.*;
import java.util.stream.Collectors;

public class StudentRepository implements Repository<Student> {
    private final TransactionService transactionService;
    private final StudentDao studentDao;
    private final CourseDao courseDao;
    private  final GradeDao gradeDao;

    public StudentRepository(TransactionService transactionService, StudentDao studentDao, CourseDao courseDao, GradeDao gradeDao) {
        this.transactionService = transactionService;
        this.studentDao = studentDao;
        this.courseDao = courseDao;
        this.gradeDao = gradeDao;
    }

    @Override
    public Optional<Student> add(Student student) {
        return this.studentDao.create(student.firstName(), student.lastName(), student.id());
    }

    @Override
    public Optional<Student> getById(int id) {
        return this.studentDao.findByIndexNumber(id);
    }

    @Override
    public List<Student> findAll() {
        return this.studentDao.findAll();
    }

    @Override
    public void remove(Student student) {
        for (Course course : new ArrayList<>(student.courseSet())) {
            course.studentSet().remove(student);
            courseDao.save(course);
        }

        for (Grade g : new ArrayList<>(student.gradeSet())) {
            gradeDao.remove(g);
        }

        this.studentDao.remove(student);
    }

    public List<Student> findAllByCourseName(String courseName) {
        var courseOpt = courseDao.findByName(courseName);
        if (courseOpt.isEmpty()) {
            return Collections.emptyList();
        }
        Course course = courseOpt.get();
        return course.studentSet().stream()
                .sorted(Comparator.comparing(Student::lastName).thenComparing(Student::firstName))
                .collect(Collectors.toList());
    }

    public Optional<Student> findByIndexNumber(int indexNumber) {
        return studentDao.findByIndexNumber(indexNumber);
    }
}
