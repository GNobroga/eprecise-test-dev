package br.com.eprecise.domain.validations.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.eprecise.domain.validations.ValidationError;
import br.com.eprecise.domain.validations.ValidationHandler;

public class NotificationHandler implements ValidationHandler {

    private final List<ValidationError> errors = new ArrayList<>();

    @Override
    public void appendError(ValidationError error) {
        this.errors.add(error);
    }

    @Override
    public List<ValidationError> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    @Override
    public boolean hasError() {
        return !errors.isEmpty();
    }
    
}
