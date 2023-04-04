package mk.ukim.finki.wp.lab.converter;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class TeacherName {
    private String name;
    private String surname;
}
