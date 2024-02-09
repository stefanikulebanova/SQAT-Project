package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.exceptions.CourseNotFound;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InMemoryCourseRepository {

    public List<Course> findAllCourses(){
        return DataHolder.courses;
    }

    public Optional<Course> findById(Long courseId){
        return DataHolder.courses.stream().filter(c -> Objects.equals(c.getCourseId(), courseId)).findFirst();
    }

    public List<Student> findAllStudentsByCourse(Long courseId){
        if(findById(courseId).isPresent())
        return findById(courseId).get().getStudents();
        else throw new CourseNotFound(courseId);
    }

    public void deleteById(Long id){
        DataHolder.courses.removeIf(c -> c.getCourseId().equals(id));
    }

    public List<Course> deepSearch(String search) {
        List<Course> crs = DataHolder.courses.stream().filter(c -> c.getName().contains(search) || c.getDescription().contains(search) || c.getTeacher().getTeacherFullName().getName().contains(search))
                .collect(Collectors.toList());
        return crs;
    }
}
