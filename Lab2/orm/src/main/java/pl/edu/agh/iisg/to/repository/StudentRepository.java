package pl.edu.agh.iisg.to.repository;

import pl.edu.agh.iisg.to.dao.CourseDao;
import pl.edu.agh.iisg.to.dao.StudentDao;
import pl.edu.agh.iisg.to.model.Course;
import pl.edu.agh.iisg.to.model.Student;

import java.util.*;

public class StudentRepository implements Repository<Student> {
    private final StudentDao studentDao;
    private final CourseDao courseDao;

    public StudentRepository(StudentDao studentDao, CourseDao courseDao) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
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
        for (Course course : new HashSet<>(student.courseSet())) {
            course.studentSet().remove(student);
            student.courseSet().remove(course);
        }

        this.studentDao.remove(student);
    }

    public List<Student> findAllByCourseName(String courseName) {
        Optional<Course> course = this.courseDao.findByName(courseName);

        return course.map(value -> value.studentSet().stream().toList()).orElse(Collections.emptyList());
    }
}
