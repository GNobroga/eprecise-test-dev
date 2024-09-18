package br.com.eprecise.domain.validations;

import java.util.List;

public interface ValidationHandler {
    void appendError(ValidationError error);
    List<ValidationError> getErrors();
    boolean hasError();
}
