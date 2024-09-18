package br.com.eprecise.domain.entities;

import br.com.eprecise.domain.validations.ValidationHandler;

public interface EntityValidable {
    void validate(ValidationHandler handler);
}
