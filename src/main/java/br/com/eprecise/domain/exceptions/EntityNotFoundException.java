package br.com.eprecise.domain.exceptions;

public class EntityNotFoundException extends NoStackTraceException {

    public EntityNotFoundException(String humanName, String identifier) {
        super(String.format("Entity %s with identifier %s not found.", humanName, identifier));
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
    
}
