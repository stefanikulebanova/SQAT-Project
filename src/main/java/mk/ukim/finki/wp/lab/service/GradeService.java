package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Grade;

import java.time.LocalDateTime;
import java.util.List;

public interface GradeService {
    List<Grade> findAll();

    List<Grade> findAllById(Long id);

    List<Grade> getAllByStudentId(Long id);
    List<Grade> getAllByCourseId(Long id);

    List<Grade> getAllBetweenTimestamps(LocalDateTime from, LocalDateTime to);

    void addGrade(String courseId, String id, Character grade, LocalDateTime datetime);


}
