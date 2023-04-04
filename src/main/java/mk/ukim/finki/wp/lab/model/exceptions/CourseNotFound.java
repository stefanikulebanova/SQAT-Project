package mk.ukim.finki.wp.lab.model.exceptions;

public class CourseNotFound extends RuntimeException{
    public CourseNotFound(Long id) {
        super(String.format("Course with id %d is not found",id));
    }
}
