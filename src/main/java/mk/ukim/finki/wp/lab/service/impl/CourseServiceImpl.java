package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.*;
import mk.ukim.finki.wp.lab.model.enumerations.Type;
import mk.ukim.finki.wp.lab.model.exceptions.CourseNameExistsException;
import mk.ukim.finki.wp.lab.model.exceptions.TeacherNotFoundException;
import mk.ukim.finki.wp.lab.repository.jpa.CourseRepository;
import mk.ukim.finki.wp.lab.repository.jpa.GradeRepository;
import mk.ukim.finki.wp.lab.repository.jpa.StudentRepository;
import mk.ukim.finki.wp.lab.repository.jpa.TeacherRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import org.springframework.stereotype.Service;

import javax.management.InvalidAttributeValueException;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final GradeRepository gradeRepository;


    public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository, TeacherRepository teacherRepository,
                             GradeRepository gradeRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.gradeRepository = gradeRepository;
    }

    @Override
    @Transactional
    public List<Student> listStudentsByCourse(Long courseId) {
        Course c=courseRepository.findCourseByCourseId(courseId);
        List<Student> students = c.getStudents();
        return students;
    }

    @Override
    @Transactional
    public Course addStudentInCourse(String username, Long courseId) throws InvalidAttributeValueException {
        Optional<Student> s = null;
        Course c=null;
        if(username != null && courseId != null) {
            c = courseRepository.findCourseByCourseId(courseId);
            s = studentRepository.findStudentByUsername(username);
        }

        if(s.isPresent() && c!=null) {
            List<Student> students = c.getStudents();
            if(!students.contains(s.get()))
                students.add(s.get());
            return courseRepository.save(c);
        }
        else throw new InvalidAttributeValueException();
    }

    @Override
    public Course save(String name, String description, Long teacherId, Type type) {
        Teacher t = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new TeacherNotFoundException(teacherId));
        if(courseRepository.existsCourseByNameLikeIgnoreCase(name))
            throw new CourseNameExistsException(name);
        Course c = new Course(name,description,t,type);
        return courseRepository.save(c);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        List<Grade> c = gradeRepository.findByCourse_CourseId(id);
        c.stream().forEach(g -> gradeRepository.deleteById(g.getId()));
        courseRepository.deleteCourseByCourseId(id);
    }

    @Override
    public List<Course> listCourses() {
       return  courseRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Course> getCourseById(String id) {

        return Optional.ofNullable(courseRepository.findCourseByCourseId(Long.valueOf(id)));
    }

    @Override
    @Transactional
    public void editCourse(String name, String description, Long teacherId, Long courseId, Type type) {
        Teacher t = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new TeacherNotFoundException(teacherId));

        deleteById(courseId);
        Course c = new Course(name,description,t,type);
        courseRepository.save(c);
    }

    @Override
    public List<Course> deepSearch(String search) {
        TeacherFullName t = new TeacherFullName(search,search);
        return courseRepository.findAllByTeacher_TeacherName_NameContainsIgnoreCase(search);
    }
}
