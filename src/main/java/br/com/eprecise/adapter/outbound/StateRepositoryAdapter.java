package br.com.eprecise.adapter.outbound;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import br.com.eprecise.adapter.outbound.jpa.entities.StateEntityJpa;
import br.com.eprecise.adapter.outbound.jpa.repositories.StateRepositoryJpa;
import br.com.eprecise.application.outbound.StateRepositoryPort;
import br.com.eprecise.domain.entities.state.State;
import br.com.eprecise.domain.exceptions.AttributeNotFoundException;
import br.com.eprecise.domain.exceptions.EntityNotFoundException;
import br.com.eprecise.domain.filter.AttributeFilterEqual;
import br.com.eprecise.domain.filter.SearchCriteria;
import br.com.eprecise.domain.pagination.OrderType;
import br.com.eprecise.domain.pagination.Pagination;
import br.com.eprecise.domain.utils.ReflectionUtils;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class StateRepositoryAdapter implements StateRepositoryPort {

    private final StateRepositoryJpa stateRepositoryJpa;

    private final String HUMAN_NAME = "State";

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public State save(State record) {
        final StateEntityJpa entity = StateEntityJpa.from(record);
        stateRepositoryJpa.save(entity);
        return entity.toDomain();
    }

    @Override
    public List<State> findAll(SearchCriteria searchCriteria) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<StateEntityJpa> query = cb.createQuery(StateEntityJpa.class);
        final Root<StateEntityJpa> root = query.from(StateEntityJpa.class);
        final List<Predicate> predicates = new ArrayList<>();

        final List<String> fields = ReflectionUtils.getFieldNames(StateEntityJpa.class);

        for (AttributeFilterEqual equal: searchCriteria.getEqualFilters()) { 
            if (!fields.contains(equal.getFieldName())) {
                throw new AttributeNotFoundException(equal.getFieldName());
            }
            predicates.add(cb.like(cb.lower(root.get(equal.getFieldName()).as(String.class)), "%" + equal.getValue().toLowerCase() + "%"));
        }

        query.where(predicates.toArray(new Predicate[] {}));
        final OrderType orderType = searchCriteria.getPagination().getPageOrder();
        query.orderBy(OrderType.ASC.equals(orderType) ? cb.asc(root.get("id")) : cb.desc(root.get("id")));

        final TypedQuery<StateEntityJpa> entityManagerQuery = entityManager.createQuery(query);
        final Pagination pagination = searchCriteria.getPagination();
        entityManagerQuery.setMaxResults(pagination.getPageSize());
        entityManagerQuery.setFirstResult((pagination.getPageNumber() - 1) * pagination.getPageSize());
        return entityManagerQuery.getResultList().stream().map(StateEntityJpa::toDomain).collect(Collectors.toList()); 
    }

    @Override
    public State findById(String identifier) {
        return stateRepositoryJpa.findById(identifier)
            .map(StateEntityJpa::toDomain)
            .orElseThrow(() -> new EntityNotFoundException(HUMAN_NAME, identifier));
    }

    @Override
    public Long count() {
        return stateRepositoryJpa.count();
    }

    @Override
    public boolean existsByAbbreviation(String abbreviation) {
        return stateRepositoryJpa.existsByAbbreviation(abbreviation);
    }

    @Transactional
    @Override
    public void deleteById(String identifier) {
        stateRepositoryJpa.deleteById(findById(identifier).getId().getUuid().toString());
    }

    @Override
    public State findByAbbreviation(String abbreviation) {
        if (!existsByAbbreviation(abbreviation)) {
            throw new EntityNotFoundException("Entity with abbreviation '" + abbreviation + "' does not exist.");
        }
        return stateRepositoryJpa.findByAbbreviation(abbreviation)
            .map(StateEntityJpa::toDomain)
            .get();
    }
    
}
