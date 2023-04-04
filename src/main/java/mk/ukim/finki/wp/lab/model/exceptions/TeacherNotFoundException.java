package mk.ukim.finki.wp.lab.model.exceptions;

public class TeacherNotFoundException extends RuntimeException{
    public TeacherNotFoundException(Long id) {
        super(String.format("Teacher with id %s was not found",id.toString()));
    }
}
