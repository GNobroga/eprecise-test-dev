package br.com.eprecise.adapter.outbound.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.eprecise.domain.entities.state.State;
import br.com.eprecise.domain.valueObjects.UUIDIdentifier;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tb_states")
@SuperBuilder
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class StateEntityJpa extends EntityJpa {
    
    private String name;
    private String abbreviation;

    public static StateEntityJpa from(State source) {
        return StateEntityJpa.builder()
            .id(source.getId().getUuid().toString())
            .name(source.getName())
            .abbreviation(source.getAbbreviation())
            .createdAt(source.getCreatedDate())
            .updatedAt(source.getUpdatedDate())
            .build();
    }

    public State toDomain() {
        return State.builder()
            .id(new UUIDIdentifier(getId()))
            .name(getName())
            .abbreviation(getAbbreviation())
            .createdDate(getCreatedAt())
            .updatedDate(getUpdatedAt())
            .build();
    }
}
