package mk.ukim.finki.wp.lab.mockito;


import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.enumerations.Type;
import mk.ukim.finki.wp.lab.model.exceptions.TeacherNotFoundException;
import mk.ukim.finki.wp.lab.repository.jpa.CourseRepository;
import mk.ukim.finki.wp.lab.repository.jpa.GradeRepository;
import mk.ukim.finki.wp.lab.repository.jpa.StudentRepository;
import mk.ukim.finki.wp.lab.repository.jpa.TeacherRepository;
import mk.ukim.finki.wp.lab.model.exceptions.CourseNotFound;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.impl.CourseServiceImpl;
import mk.ukim.finki.wp.lab.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.management.InvalidAttributeValueException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @MockBean
    private CourseRepository courseRepository;
    @MockBean
    private  StudentRepository studentRepository;
    @MockBean
    private  TeacherRepository teacherRepository;
    @MockBean
    private  GradeRepository gradeRepository;

    Student s;
    Course c;
    Teacher t;
    List<Student> students;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        this.courseService = Mockito.spy(new CourseServiceImpl(courseRepository, studentRepository, teacherRepository, gradeRepository));
        s = new Student("st1","studentpass","student1","surname");
        //Student s1 = new Student("s1", "s1");
        Mockito.when(studentRepository.findStudentByUsername("st1")).thenReturn(Optional.of(s));
        Mockito.when(studentRepository.save(Mockito.any(Student.class))).thenReturn(s);

        t = new Teacher("t","T");
        Mockito.when(teacherRepository.save(Mockito.any(Teacher.class))).thenReturn(t);
        Mockito.when(teacherRepository.findById(Long.valueOf(1))).thenReturn(Optional.ofNullable(t));

        c = new Course("c","course",t, Type.ELECTIVE);
        students = new ArrayList<>();
        students.add(s);
        c.setStudents(students);
        Mockito.when(courseRepository.findCourseByCourseId(Long.valueOf(1))).thenReturn(c);
        Mockito.when(courseRepository.existsCourseByNameLikeIgnoreCase("")).thenReturn(false);
        Mockito.when(courseRepository.save(Mockito.any(Course.class))).thenReturn(c);
    }


    @Test
    // C1 - T, C2 - F
    public void listStudentsTest1() {
        Long courseId = Long.valueOf(1);

        assertEquals(students, this.courseService.listStudentsByCourse(courseId));
    }
    @Test
    // C1 - F, C2 - T
    public void listStudentsTest2() {
        Long courseId = Long.valueOf(-1);

        assertThrows("CourseNotFound exception expected", CourseNotFound.class, () -> {
            this.courseService.listStudentsByCourse(courseId);
            });
    }

    @Test
    // A1 B2 C1 D2 - TFTF
    public void addStudentTest1() throws InvalidAttributeValueException {
        Long courseId = Long.valueOf(1);
        String username = "st1";
        assertEquals(s, this.courseService.addStudentInCourse(username,courseId));
    }

    @Test
    // A2 B1 C1 D2 - FTTF
    public void addStudentTest2() {
        Long courseId = Long.valueOf(-1);
        String username = "st1";

        assertThrows("CourseNotFound exception expected", CourseNotFound.class, () -> {
            this.courseService.addStudentInCourse(username,courseId);
        });
    }

    @Test
    // A1 B2 C2 D1 - TFFT
    public void addStudentTest3() {
        Long courseId = Long.valueOf(1);
        String username = "st";

        assertThrows("Student username should not be found", InvalidAttributeValueException.class, () -> {
            this.courseService.addStudentInCourse(username, courseId);
        });
    }

    @Test
    // TFTFTFTF
    public void saveCourseTest1(){
        String name = "";
        String desc = "desc";
        Long teacherId = Long.valueOf(1);
        Type type = Type.ELECTIVE;

        assertEquals(c,this.courseService.save(name,desc,teacherId,type));
    }

    @Test
    // FTTFTFTF
    public void saveCourseTest2(){
        String name = null;
        String desc = "desc";
        Long teacherId = Long.valueOf(1);
        Type type = Type.ELECTIVE;

        assertThrows("Course name is not defined", InvalidAttributeValueException.class, () -> {
            this.courseService.save(name,desc,teacherId,type);
        });
    }

    @Test
    // TFFFTTTF
    public void saveCourseTest3(){
        String name = "";
        String desc = null;
        Long teacherId = Long.valueOf(1);
        Type type = Type.ELECTIVE;

        assertThrows("Course desc is not defined", InvalidAttributeValueException.class, () -> {
            this.courseService.save(name,desc,teacherId,type);
        });
    }

    @Test
    // TFFTTFTF
    public void saveCourseTest4(){
        String name = "";
        String desc = "";
        Long teacherId = Long.valueOf(1);
        Type type = Type.ELECTIVE;

        assertEquals(c,this.courseService.save(name,desc,teacherId,type));
    }

    @Test
    // TFTFFFTF
    public void saveCourseTest5(){
        String name = "";
        String desc = "desc";
        Long teacherId = null;
        Type type = Type.ELECTIVE;

        assertThrows("Teacher id is not defined", NullPointerException.class, () -> {
            this.courseService.save(name,desc,teacherId,type);
        });
    }

    @Test
    // TFTFTFFF
    public void saveCourseTest6(){
        String name = "";
        String desc = "desc";
        Long teacherId = Long.valueOf(-1);
        Type type = Type.ELECTIVE;

        assertThrows("Teacher id is not valid", TeacherNotFoundException.class, () -> {
            this.courseService.save(name,desc,teacherId,type);
        });
    }

    @Test
    // TFTFTFFT
    public void saveCourseTest7(){
        String name = "";
        String desc = "desc";
        Long teacherId = Long.valueOf(1);
        Type type = null;

        assertThrows("type is not defined", InvalidAttributeValueException.class, () -> {
            this.courseService.save(name,desc,teacherId,type);
        });
    }

    @Test
    // TFF
    public void getCourseTest1(){
        String courseId = "1";
        assertEquals(c,this.courseService.getCourseById(courseId));
    }

    @Test
    // FTF
    public void getCourseTest2(){
        String courseId = null;

        assertThrows("Course id is null", NumberFormatException.class, () -> {
            this.courseService.getCourseById(courseId);
        });
    }

    @Test
    // FFT
    public void getCourseTest3(){
        String courseId = "";

        assertThrows("Course id is empty", NumberFormatException.class, () -> {
            this.courseService.getCourseById(courseId);
        });
    }

}