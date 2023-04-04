package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.repository.jpa.CourseRepository;
import mk.ukim.finki.wp.lab.repository.jpa.GradeRepository;
import mk.ukim.finki.wp.lab.repository.jpa.StudentRepository;
import mk.ukim.finki.wp.lab.service.GradeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public GradeServiceImpl(GradeRepository gradeRepository, CourseRepository courseRepository, StudentRepository studentRepository) {
        this.gradeRepository = gradeRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }


    @Override
    public List<Grade> findAll() {
        return gradeRepository.findAll();
    }

    @Override
    public List<Grade> findAllById(Long id) {
        return gradeRepository.findAllById(id);
    }

    @Override
    @Transactional
    public List<Grade> getAllByStudentId(Long id) {
        List<Grade> l = gradeRepository.findAll();
        return l.stream().filter(g -> g.getStudent().getStudentId().equals(id)).collect(Collectors.toList());
    }

    @Override
    public List<Grade> getAllByCourseId(Long id) {
        return gradeRepository.findByCourse_CourseId(id);
    }

    @Override
    public List<Grade> getAllBetweenTimestamps(LocalDateTime from, LocalDateTime to) {
        return gradeRepository.findAllByTimestampBetween(from,to);
    }

    @Override
    public void addGrade(String courseId, String id, Character grade, LocalDateTime datetime) {
        Course c = courseRepository.findCourseByCourseId(Long.valueOf(courseId));
        Student st = studentRepository.getReferenceById(Long.valueOf(id));
        Grade g = new Grade(grade,st,c,datetime);
        Optional<Grade> find = gradeRepository.findGradeByCourse_CourseIdAndStudent_StudentId(Long.valueOf(courseId), Long.valueOf(id));
        if(find.isEmpty())
           gradeRepository.save(g);
        else{
            gradeRepository.deleteById(find.get().getId());
            gradeRepository.save(g);
        }
    }
}
