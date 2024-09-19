package br.com.eprecise.domain.exceptions;

public class InsufficientCharactersException extends NoStackTraceException {

    public InsufficientCharactersException(String message) {
        super(message);
    }
    
}
