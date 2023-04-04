package mk.ukim.finki.wp.lab.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long studentId;
    String username;
    String password;
    String name;
    String surname;

    public Student(String username, String password, String name, String surname){
        this.username=username;
        this.name=name;
        this.password=password;
        this.surname=surname;
    }
    public Student(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Student() {
    }

    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
