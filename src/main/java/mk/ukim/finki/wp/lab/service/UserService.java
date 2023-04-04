package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService {

    Student register(String username, String password, String repeatPassword, String name, String surname);
    Optional<Student> loadUserByUsername(String username);

}
