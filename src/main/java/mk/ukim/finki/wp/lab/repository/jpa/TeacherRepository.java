package mk.ukim.finki.wp.lab.repository.jpa;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {

    List<Teacher> findAll();
    Optional<Teacher> findById(Long id);
    //Teacher findTeacherByTeacherName(String name);
}
