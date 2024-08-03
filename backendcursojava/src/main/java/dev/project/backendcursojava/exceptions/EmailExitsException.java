package dev.project.backendcursojava.exceptions;

public class EmailExitsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EmailExitsException(String message) {
        super(message);
    }
    
}
