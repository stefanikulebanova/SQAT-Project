package mk.ukim.finki.wp.lab.repository.jpa;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.enumerations.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

      List<Course> findAll();
      Course findCourseByCourseId(Long id);
      void deleteCourseByCourseId(Long id);

      boolean existsCourseByNameLikeIgnoreCase(String name);
      List<Course> findAllByTeacher_TeacherName_NameContainsIgnoreCase(String text);
     // List<Course> findAllByTeacher_TeacherFullName_NameLikeOrNameContainsIgnoreCaseOrDescriptionContainsIgnoreCase(String name, String description);

      Course save(Course c);

      List<Course> findByType(Type type);

}
