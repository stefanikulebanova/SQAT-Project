package mk.ukim.finki.wp.lab.repository.jpa;

import mk.ukim.finki.wp.lab.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade,Long> {
    @Override
    List<Grade> findAll();

    List<Grade> findAllById(Long id);

    List<Grade> findAllByTimestampBetween(LocalDateTime from, LocalDateTime to);

    Optional<Grade> findGradeByCourse_CourseIdAndStudent_StudentId(Long courseId, Long studentId);

    List<Grade> findByCourse_CourseId(Long id);

    void deleteById(Long id);

    Grade save(Grade g);
}
