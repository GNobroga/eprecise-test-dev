package br.com.eprecise.domain.entities.city;

import java.util.Objects;

import br.com.eprecise.domain.validations.ValidationError;
import br.com.eprecise.domain.validations.ValidationHandler;
import br.com.eprecise.domain.validations.Validator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CityValidator implements Validator {

    private final City target;

    private final ValidationHandler handler;

    @Override
    public void validate() {
        if (Objects.isNull(target.getName()) || target.getName().trim().isEmpty()) {
            handler.appendError(new ValidationError("The city name cannot be null or empty."));
        } else if (target.getName().length() > 255) {
            handler.appendError(new ValidationError("The city name must be less than or equal to 255 characters."));
        }

        if (Objects.isNull(target.getStateId())) {
            handler.appendError(new ValidationError("The state ID cannot be null."));
        }

        if (Objects.isNull(target.getPopulation()) || target.getPopulation() < 0) {
            handler.appendError(new ValidationError("The city population cannot be null or negative."));
        }
    }
    
}
