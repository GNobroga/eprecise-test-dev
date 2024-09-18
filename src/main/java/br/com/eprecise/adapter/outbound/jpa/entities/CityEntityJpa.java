package br.com.eprecise.adapter.outbound.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.eprecise.domain.entities.city.City;
import br.com.eprecise.domain.valueObjects.UUIDIdentifier;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tb_cities")
@SuperBuilder
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class CityEntityJpa extends EntityJpa {
    
    private String name;

    private Long population;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private StateEntityJpa state;

    public static CityEntityJpa from(City source) {
        return CityEntityJpa.builder()
            .id(source.getId().getUuid().toString())
            .name(source.getName())
            .population(source.getPopulation())
            .state(StateEntityJpa.builder().id(source.getStateId()).build())
            .createdAt(source.getCreatedDate())
            .updatedAt(source.getUpdatedDate())
            .build();
    }

    public City toDomain() {
        return City.builder()
            .id(new UUIDIdentifier(getId()))
            .name(getName())
            .population(getPopulation())
            .stateId(getState().getId())
            .createdDate(getCreatedAt())
            .updatedDate(getUpdatedAt())
            .build();
    }
}
