package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.repository.jpa.StudentRepository;
import mk.ukim.finki.wp.lab.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final StudentRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(StudentRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public Student register(String username, String password, String repeatPassword, String name, String surname) {
        return null;
    }

    @Override
    public Optional<Student> loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findStudentByUsername(username);
    }
}

