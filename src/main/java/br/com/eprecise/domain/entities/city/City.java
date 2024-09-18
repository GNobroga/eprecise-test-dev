package br.com.eprecise.domain.entities.city;

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
public class City implements EntityValidable {
    private UUIDIdentifier id;
    private String name;
    private String stateId;
    private Long population;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public static City create(final String name, final String stateId, final Long population) {
        return City.builder()
            .id(new UUIDIdentifier())
            .name(name)
            .stateId(stateId)
            .population(population)
            .createdDate(LocalDateTime.now())
            .build();
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new CityValidator(this, handler).validate();
    }
}
