package br.com.eprecise.application.outbound;

import java.util.List;

import br.com.eprecise.domain.entities.EntityValidable;
import br.com.eprecise.domain.filter.SearchCriteria;

public interface RepositoryPort<E extends EntityValidable> {
    E save(E record);
    List<E> findAll(SearchCriteria searchCriteria); 
    E findById(String identifier);
    Long count();
    void deleteById(String identifier);
}
