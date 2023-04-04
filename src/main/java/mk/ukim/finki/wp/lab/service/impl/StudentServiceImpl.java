package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidUserCredentialException;
import mk.ukim.finki.wp.lab.repository.jpa.StudentRepository;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> listAll() {
       return studentRepository.findAll();
    }

    @Override
    public List<Student> searchByNameOrSurname(String text) {
        return studentRepository.findAllByNameContainsOrSurnameContains(text,text);
    }

    @Override
    public Optional<Student> searchByUsername(String username) {
        return studentRepository.findStudentByUsername(username);
    }

    @Override
    public Student save(String username, String password, String name, String surname) {
        if(username==null || username.isEmpty() || password==null || password.isEmpty())
            throw new InvalidUserCredentialException();

        Student st=new Student(username,password,name,surname);

        studentRepository.save(st);

        return st;
    }

    @Override
    public List<Student> deepSearch(String search) {
        return studentRepository.findAllByNameContainsOrSurnameContains(search,search);
    }

    @Override
    public Student getById(String id) {
        return studentRepository.getReferenceById(Long.valueOf(id));
    }
}
