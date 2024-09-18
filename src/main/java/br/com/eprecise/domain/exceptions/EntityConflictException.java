package br.com.eprecise.domain.exceptions;

public class EntityConflictException extends NoStackTraceException {

    public EntityConflictException(String message) {
        super(message);
    }
    
}
