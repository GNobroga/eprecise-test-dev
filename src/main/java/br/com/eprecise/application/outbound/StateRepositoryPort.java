package br.com.eprecise.application.outbound;

import java.util.Optional;

import br.com.eprecise.domain.entities.state.State;

public interface StateRepositoryPort extends RepositoryPort<State> {
   boolean existsByAbbreviation(String abbreviation);
   Optional<State> findByAbbreviation(String abbreviation);
   boolean existsById(String id);
}
