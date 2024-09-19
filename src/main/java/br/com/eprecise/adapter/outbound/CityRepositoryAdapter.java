package br.com.eprecise.adapter.outbound;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import br.com.eprecise.adapter.outbound.jpa.entities.CityEntityJpa;
import br.com.eprecise.adapter.outbound.jpa.repositories.CityRepositoryJpa;
import br.com.eprecise.application.outbound.CityRepositoryPort;
import br.com.eprecise.domain.entities.city.City;
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
public class CityRepositoryAdapter implements CityRepositoryPort {
    
    private final CityRepositoryJpa cityRepositoryJpa;

    private final String HUMAN_NAME = "City";
    
    private static final String SORT_BY = "id";
    
    private final String DOT = ".";

    @PersistenceContext
    private EntityManager entityManager;
    
    @Transactional
    @Override
    public City save(City record) {
        final CityEntityJpa entity = CityEntityJpa.from(record);
        cityRepositoryJpa.save(entity);
        return entity.toDomain();
    }
    
    @Override
    public List<City> findAll(SearchCriteria searchCriteria) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<CityEntityJpa> query = cb.createQuery(CityEntityJpa.class);
        final Root<CityEntityJpa> root = query.from(CityEntityJpa.class);
        final List<Predicate> predicates = new ArrayList<>();

        final List<String> fields = ReflectionUtils.getFieldNames(CityEntityJpa.class);


        for (AttributeFilterEqual equal: searchCriteria.getEqualFilters()) { 

            if (equal.getFieldName().contains(DOT)) {
                final String[] splitFieldName = equal.getFieldName().split(Pattern.quote(DOT));
                if (splitFieldName.length == 2) {
                   try {
                        final String fieldName =  splitFieldName[0];
                        final String targetFieldName = splitFieldName[1];
                        Join<Object, Object> join = root.join(fieldName);
                        predicates.add(cb.like(cb.lower(join.get(targetFieldName).as(String.class)), "%" + equal.getValue().toLowerCase() + "%"));
                        continue;
                   } catch (Exception error) {
                     throw new AttributeNotFoundException(equal.getFieldName());
                   }
                }
            }

            if (!fields.contains(equal.getFieldName())) {
                throw new AttributeNotFoundException(equal.getFieldName());
            }
            predicates.add(cb.like(cb.lower(root.get(equal.getFieldName()).as(String.class)), "%" + equal.getValue().toLowerCase() + "%"));
        }

        query.where(predicates.toArray(new Predicate[] {}));
        final OrderType orderType = searchCriteria.getPagination().getPageOrder();
        query.orderBy(OrderType.ASC.equals(orderType) ? cb.asc(root.get("id")) : cb.desc(root.get("id")));

        final TypedQuery<CityEntityJpa> entityManagerQuery = entityManager.createQuery(query);
        final Pagination pagination = searchCriteria.getPagination();
        entityManagerQuery.setMaxResults(pagination.getPageSize());
        entityManagerQuery.setFirstResult((pagination.getPageNumber() - 1) * pagination.getPageSize());
        return entityManagerQuery.getResultList().stream().map(CityEntityJpa::toDomain).collect(Collectors.toList()); 
    }
    @Override
    public City findById(String identifier) {
        return cityRepositoryJpa.findById(identifier)
            .map(CityEntityJpa::toDomain)
            .orElseThrow(() -> new EntityNotFoundException(HUMAN_NAME, identifier));
    }

    @Override
    public Long count() {
        return cityRepositoryJpa.count();
    }

    @Transactional
    @Override
    public void deleteById(String identifier) {
        cityRepositoryJpa.deleteById(findById(identifier).getId().getUuid().toString());
    }

    @Override
    public boolean existsByStateAbbreviationAndName(String stateAbbreviation, String cityName) {
        return cityRepositoryJpa.existsByStateAbbreviationAndName(stateAbbreviation, cityName);
    }

    // Cidades: GET por estado (pesquisa com paginação)
    @Override
    public List<City> findByStateId(String stateId, Pagination pagination) {
        final Sort.Direction sortDirection = OrderType.ASC.equals(pagination.getPageOrder()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        final Pageable pageable = PageRequest.of(
            pagination.getPageNumber() - 1, 
            pagination.getPageSize(), 
            Sort.by(sortDirection, SORT_BY)
        );
        final Page<CityEntityJpa> page = cityRepositoryJpa.findByStateId(stateId, pageable);
        return page.getContent().stream().map(CityEntityJpa::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<City> findByName(String name, Pagination pagination) {
        final Sort.Direction sortDirection = OrderType.ASC.equals(pagination.getPageOrder()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        final Pageable pageable = PageRequest.of(
            pagination.getPageNumber() - 1, 
            pagination.getPageSize(), 
            Sort.by(sortDirection, SORT_BY)
        );
        final Page<CityEntityJpa> page = cityRepositoryJpa.findCitiesByNameWithLengthGreaterThanThree(name, pageable);
        return page.getContent().stream().map(CityEntityJpa::toDomain).collect(Collectors.toList());
    }
    
}
