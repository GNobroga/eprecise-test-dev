package br.com.eprecise.domain.exceptions;

public class AbbreviationAlreadyExistsException extends NoStackTraceException {
    
    public AbbreviationAlreadyExistsException(String abbreviation) {
        super(String.format("The abbreviation '%s' already exists.", abbreviation));
    }
}
