package br.com.eprecise.domain.entities.state;

import java.time.LocalDateTime;

import br.com.eprecise.domain.entities.EntityValidable;
import br.com.eprecise.domain.validations.ValidationHandler;
import br.com.eprecise.domain.valueObjects.UUIDIdentifier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class State implements EntityValidable {
    private UUIDIdentifier id;
    private String name;
    private String abbreviation;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public static State create(String name, String abbreviation) {
        return State.builder()
            .id(new UUIDIdentifier())
            .name(name)
            .abbreviation(abbreviation)
            .createdDate(LocalDateTime.now())
            .build();
    }

    @Override
    public void validate(ValidationHandler handler) {
        new StateValidator(this, handler).validate();
    }
}
