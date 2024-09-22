package br.com.eprecise.adapter.outbound;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.eprecise.adapter.outbound.jpa.entities.StateEntityJpa;
import br.com.eprecise.adapter.outbound.jpa.repositories.StateRepositoryJpa;
import br.com.eprecise.application.outbound.StateRepositoryPort;
import br.com.eprecise.domain.entities.state.State;
import lombok.Getter;
import lombok.Setter;

@ApplicationScoped
@Getter
@Setter
public class StateRepositoryAdapter extends GenericRepository<StateEntityJpa, String, State> implements StateRepositoryPort {

    private final StateRepositoryJpa stateRepositoryJpa;

    @PersistenceContext
    private EntityManager entityManager;

    
    public StateRepositoryAdapter(StateRepositoryJpa repository) {
        super(repository);
        this.stateRepositoryJpa = repository;
    }

    @Override
    public boolean existsByAbbreviation(String abbreviation) {
        return stateRepositoryJpa.existsByAbbreviation(abbreviation);
    }

    @Override
    public Optional<State> findByAbbreviation(String abbreviation) {
        return stateRepositoryJpa.findByAbbreviationIgnoreCase(abbreviation.toLowerCase())
            .map(StateEntityJpa::toDomain);
    }

    @Override
    Class<StateEntityJpa> resolveEntityClass() {
        return StateEntityJpa.class;
    }

    @Override
    EntityManager resolveEntityManager() {
        return entityManager;
    }

    @Override
    String resolveOrderByAttributeName() {
       return "id";
    }

    @Override
    State toDomain(StateEntityJpa source) {
       return source.toDomain();
    }

    @Override
    StateEntityJpa toEntity(State source) {
       return StateEntityJpa.from(source);
    }

    @Override
    String resolveHumanName() {
        return "State";
    }

    
}
