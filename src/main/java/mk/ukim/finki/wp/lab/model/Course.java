package mk.ukim.finki.wp.lab.model;

import lombok.Data;
import mk.ukim.finki.wp.lab.model.enumerations.Type;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Transactional
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;
    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private Type type;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Student> students;
    @ManyToOne
    private Teacher teacher;

    public Course(String name, String description, Teacher teacher, Type type) {
        this.name = name;
        this.teacher = teacher;
        this.description = description;
        this.type=type;
        students=new ArrayList<>();
    }

    public Course() {
    }

}
