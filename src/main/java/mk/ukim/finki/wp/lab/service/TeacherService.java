package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Teacher;

import java.util.List;

public interface TeacherService {
    public List<Teacher> findAll();
    Teacher save(String name,String surname);
}
