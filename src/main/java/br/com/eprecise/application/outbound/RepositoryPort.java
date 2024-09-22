package br.com.eprecise.application.outbound;

import java.util.List;
import java.util.Optional;

import br.com.eprecise.domain.entities.EntityValidable;
import br.com.eprecise.domain.filter.SearchCriteria;

public interface RepositoryPort<E extends EntityValidable> {
    Optional<E> save(E record);
    List<E> findAll(SearchCriteria searchCriteria); 
    Optional<E> findById(String identifier);
    Long count();
    void deleteById(String identifier);
}
