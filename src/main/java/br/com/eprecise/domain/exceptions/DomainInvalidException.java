package br.com.eprecise.domain.exceptions;

import java.util.List;

import br.com.eprecise.domain.validations.ValidationError;
import lombok.Getter;

@Getter
public class DomainInvalidException extends NoStackTraceException {

    private List<ValidationError> errors;

    public DomainInvalidException(String message, List<ValidationError> errors) {
        super(message);
        this.errors = errors;
    }

    public DomainInvalidException(String message) {
        super(message);
    }

    public DomainInvalidException(List<ValidationError> errors) {
        this("Domain invalid");
    } 
}
