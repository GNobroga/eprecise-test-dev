package br.com.eprecise.domain.entities.state;

import java.util.Objects;

import br.com.eprecise.domain.validations.ValidationError;
import br.com.eprecise.domain.validations.ValidationHandler;
import br.com.eprecise.domain.validations.Validator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StateValidator implements Validator {

    private final State target;

    private final ValidationHandler handler;

    @Override
    public void validate() {
         if (Objects.isNull(target.getName()) || target.getName().trim().isEmpty()) {
            handler.appendError(new ValidationError("The name cannot be null or empty."));
        } else if (target.getName().length() > 100) {
            handler.appendError(new ValidationError("The name cannot be longer than 100 characters."));
        }

        if (Objects.isNull(target.getAbbreviation()) || target.getAbbreviation().trim().isEmpty()) {
            handler.appendError(new ValidationError("The abbreviation cannot be null or empty."));
        } else if ( target.getAbbreviation().length() < 2 || target.getAbbreviation().length() > 10) {
            handler.appendError(new ValidationError("The abbreviation must be between 2 and 10 characters."));
        }
    }
    
}
