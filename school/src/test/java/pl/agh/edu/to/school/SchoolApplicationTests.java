package pl.agh.edu.to.school;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.agh.edu.to.school.course.Course;
import pl.agh.edu.to.school.grade.Grade;
import pl.agh.edu.to.school.student.Student;
import pl.agh.edu.to.school.student.StudentService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class SchoolApplicationTests {

    @Autowired
    private StudentService studentService;

	@Test
	void testGrading() {
        // Given
        Student student = new Student("Jan", "Kowalski", LocalDate.of(1999, 8, 19),
                "1234556789", "kowal@student.agh.edu.pl");
        Course course = new Course("Historia myśli marksistowskiej");

        // When
        studentService.assignGrade(student, course, 4.0);

        // Then
        List<Grade> grades = studentService.getGradeService().getGradeBook().getStudentGrades().get("1234556789");
        boolean contains = false;
        for (Grade grade : grades) {
            if (grade.getCourse().getName().equals(course.getName()) && Math.abs(grade.getValue() - 4.0) < 0.000001) {
                contains = true;
                break;
            }
        }

        assertTrue(contains);
	}

}
