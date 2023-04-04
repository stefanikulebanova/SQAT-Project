package mk.ukim.finki.wp.lab.bootstrap;

import lombok.Getter;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class DataHolder {
    public static List<Student> students = new ArrayList<Student>();
    public static List<Course> courses = new ArrayList<Course>();
    public static List<Teacher> teachers = new ArrayList<Teacher>();

//    @PostConstruct
//    public void init() {
//        students.add(new Student("marko.j","kk","Marko", "Janevski"));
//        students.add(new Student("jana.m","hhhh","Jana", "Mileva"));
//        students.add(new Student("mia.k","vdjksj","Mia", "Kosturanov"));
//        students.add(new Student("darko.b","fff","Darko", "Bilev"));
//        students.add(new Student("riste.p","dssss","Riste", "Petreski"));
//
//        Teacher teacher1=new Teacher("Marijana","Bilovska");
//        Teacher teacher2=new Teacher("Vesna","Stefanovska");
//        Teacher teacher3=new Teacher("Elena","Markovska");
//        Teacher teacher4=new Teacher("Sofija","Bodikj");
//        Teacher teacher5=new Teacher("Lile","Savova");
//        teachers.add(teacher1);
//        teachers.add(teacher2);
//        teachers.add(teacher3);
//        teachers.add(teacher4);
//        teachers.add(teacher5);
//
//        courses.add(new Course("VVKN","voved vo kompjuterski nauki",teacher1));
//        courses.add(new Course("DIANS","dizajn i arhitektura na softver",teacher2));
//        courses.add(new Course("OS","operativni sistemi",teacher3));
//        courses.add(new Course("WP","veb programiranje",teacher4));
//        courses.add(new Course("IT","internet programiranje",teacher5));
//    }
}
