package pl.edu.agh.school;

import java.util.Collections;
import java.util.List;

import jakarta.inject.Inject;
import pl.edu.agh.logger.Logger;
import pl.edu.agh.school.persistence.PersistenceManager;
import pl.edu.agh.school.persistence.SerializablePersistenceManager;

public class SchoolDAO {

    public Logger log;

    private final List<Teacher> teachers;

    private final List<SchoolClass> classes;

    private final PersistenceManager manager;

    /*public SchoolDAO() {
        manager = new SerializablePersistenceManager();
        teachers = manager.loadTeachers();
        classes = manager.loadClasses();
    }*/

    @Inject
    public SchoolDAO(PersistenceManager manager) {
        this.manager = manager;
        teachers = this.manager.loadTeachers();
        classes = this.manager.loadClasses();
    }

    @Inject
    public void setLog(Logger log) {
        this.log = log;
    }

    public void addTeacher(Teacher teacher) {
        if (!teachers.contains(teacher)) {
            teachers.add(teacher);
            manager.saveTeachers(teachers);
            log.log("Added " + teacher.toString());
        }
    }

    public void addClass(SchoolClass newClass) {
        if (!classes.contains(newClass)) {
            classes.add(newClass);
            manager.saveClasses(classes);
            log.log("Added " + newClass.toString());
        }
    }

    public List<SchoolClass> getClasses() {
        return Collections.unmodifiableList(classes);
    }

    public List<Teacher> getTeachers() {
        return Collections.unmodifiableList(teachers);
    }
}
