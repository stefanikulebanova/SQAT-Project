package mk.ukim.finki.wp.lab.mockito;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.enumerations.Type;
import mk.ukim.finki.wp.lab.model.exceptions.CourseNameExistsException;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidUserCredentialException;
import mk.ukim.finki.wp.lab.model.exceptions.TeacherNotFoundException;
import mk.ukim.finki.wp.lab.repository.jpa.CourseRepository;
import mk.ukim.finki.wp.lab.repository.jpa.StudentRepository;
import mk.ukim.finki.wp.lab.repository.jpa.TeacherRepository;
import mk.ukim.finki.wp.lab.service.impl.CourseServiceImpl;
import mk.ukim.finki.wp.lab.service.impl.StudentServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class AddCourseTest {
    @Mock
    private StudentRepository studentRepository;

    private StudentServiceImpl studentService;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.studentService = Mockito.spy(new StudentServiceImpl(studentRepository));
        Student s = new Student("ss", "ss");
        Mockito.when(studentRepository.save(Mockito.any(Student.class))).thenReturn(s);
    }

    @Test
    public void usernameEmptyTest() {
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidUserCredentialException.class,
                () -> this.studentService.save("", "password", "name", "surname"));
        Mockito.verify(this.studentService).save("", "password", "name", "surname");

    }

    @Test
    public void usernameNullTest() {
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidUserCredentialException.class,
                () -> this.studentService.save(null, "password", "name", "surname"));
        Mockito.verify(this.studentService).save(null, "password", "name", "surname");

    }

    @Test
    public void passwordNullTest() {
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidUserCredentialException.class,
                () -> this.studentService.save("username", null, "name", "surname"));
        Mockito.verify(this.studentService).save("username", null, "name", "surname");
    }

    @Test
    public void passwordEmptyTest() {
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidUserCredentialException.class,
                () -> this.studentService.save("username", "", "name", "surname"));
        Mockito.verify(this.studentService).save("username", "", "name", "surname");
    }

    @Test
    public void successTest() {
        Student st = this.studentService.save("username", "password", "name", "surname");
        Assert.assertNotNull(st);
    }
}