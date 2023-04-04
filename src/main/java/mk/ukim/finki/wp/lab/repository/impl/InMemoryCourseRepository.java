package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
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

//    public Course addStudentToCourse(Student student, Course course){
//
//        DataHolder.courses.stream().forEach( c -> {
//            if(Objects.equals(c.getCourseId(), course.getCourseId())) {
//                if(!c.getStudents().contains(student))
//                     c.addStudent(student);
//            }
//        });
//        if(findById(course.getCourseId()).isPresent())
//            return findById(course.getCourseId()).get();
//        else throw new CourseNotFound(course.getCourseId());
//    }

//    public Optional<Course> addCourse(String name, String description, Teacher teacher){
//        DataHolder.courses.removeIf(c -> c.getName().equals(name));
//        Course c = new Course(name,description,teacher, type);
//        DataHolder.courses.add(c);
//        return Optional.of(c);
//    }

    public void deleteById(Long id){
        DataHolder.courses.removeIf(c -> c.getCourseId().equals(id));
    }

    public List<Course> deepSearch(String search) {
        List<Course> crs = DataHolder.courses.stream().filter(c -> c.getName().contains(search) || c.getDescription().contains(search) || c.getTeacher().getTeacherFullName().getName().contains(search))
                .collect(Collectors.toList());
        return crs;
    }
}
