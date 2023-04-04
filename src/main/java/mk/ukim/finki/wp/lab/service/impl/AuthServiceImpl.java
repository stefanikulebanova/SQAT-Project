package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidUserCredentialException;
import mk.ukim.finki.wp.lab.model.exceptions.PasswordsDoNotMatchException;
import mk.ukim.finki.wp.lab.repository.jpa.StudentRepository;
import mk.ukim.finki.wp.lab.service.AuthService;

public class AuthServiceImpl implements AuthService {
    private final StudentRepository studentRepository;

    public AuthServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

//    @Override
//    public Student login(String username, String password) {
//        if(username==null || username.isEmpty() || password==null || password.isEmpty())
//            throw new InvalidUserCredentialException();
//        return studentRepository.findByUsernameAndPassword(username, password).orElseThrow(InvalidUserCredentialException::new);
//    }

    @Override
    public Student register(String username, String password, String repeatPassword, String name, String surname) {
        if(username==null || username.isEmpty() || password==null || password.isEmpty())
            throw new InvalidUserCredentialException();
        if(!password.equals(repeatPassword))
            throw new PasswordsDoNotMatchException();
        Student st=new Student(username,password,name,password);
        studentRepository.save(st);
        return st;
    }
}
