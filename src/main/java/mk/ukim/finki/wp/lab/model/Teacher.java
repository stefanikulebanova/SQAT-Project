package mk.ukim.finki.wp.lab.model;

import lombok.Data;
import mk.ukim.finki.wp.lab.converter.TeacherName;
import mk.ukim.finki.wp.lab.converter.TeacherNameConverter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = TeacherNameConverter.class)
    private TeacherFullName teacherFullName;


    @Embedded
    private TeacherName teacherName;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfEmployment;

    public Teacher(){}

    public Teacher(String name, String surname) {
        this.teacherFullName=new TeacherFullName();
        this.teacherFullName.setName(name);
        this.teacherFullName.setSurname(surname);
    }

    public Long getId() {
        return id;
    }

}
