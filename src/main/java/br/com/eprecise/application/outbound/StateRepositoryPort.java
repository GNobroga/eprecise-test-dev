package br.com.eprecise.application.outbound;

import br.com.eprecise.domain.entities.state.State;

public interface StateRepositoryPort extends RepositoryPort<State> {
   boolean existsByAbbreviation(String abbreviation);
   State findByAbbreviation(String abbreviation);
}
