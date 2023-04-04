package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.enumerations.Type;

import javax.management.InvalidAttributeValueException;
import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Student> listStudentsByCourse(Long courseId);
    Object addStudentInCourse(String username, Long courseId) throws InvalidAttributeValueException;

    Course save(String name, String description, Long teacherId, Type type);

    void deleteById(Long id);

    List<Course> listCourses();

    Optional<Course> getCourseById(String id);

    void editCourse(String name, String description, Long teacherId, Long courseId, Type type);
    List<Course> deepSearch(String search);
}
