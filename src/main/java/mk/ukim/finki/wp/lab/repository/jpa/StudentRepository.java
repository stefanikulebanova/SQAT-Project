package mk.ukim.finki.wp.lab.repository.jpa;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    List<Student> findAll();

    List<Student> findAllByNameContainsOrSurnameContains(String name, String surname);

    Student save(Student s);

    Optional<Student> findStudentByUsername(String text);

    List<Student> findAllByNameOrSurnameOrUsernameLike(String name, String surname, String username);

}
