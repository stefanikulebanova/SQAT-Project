package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Student;

public interface AuthService {
  //  Student login(String username, String password);
    Student register(String username, String password, String repeatPassword, String name, String surname);
}
