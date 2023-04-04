package mk.ukim.finki.wp.lab.model.exceptions;

public class InvalidUserCredentialException extends RuntimeException{
    public InvalidUserCredentialException() {
        super("Invalid user credentials");
    }
}
