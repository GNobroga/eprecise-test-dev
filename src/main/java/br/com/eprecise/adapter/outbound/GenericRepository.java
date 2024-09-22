package br.com.eprecise.adapter.outbound;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eprecise.adapter.outbound.jpa.entities.EntityJpa;
import br.com.eprecise.domain.entities.EntityValidable;
import br.com.eprecise.domain.exceptions.AttributeNotFoundException;
import br.com.eprecise.domain.exceptions.EntityNotFoundException;
import br.com.eprecise.domain.filter.AttributeFilter;
import br.com.eprecise.domain.filter.SearchCriteria;
import br.com.eprecise.domain.pagination.OrderType;
import br.com.eprecise.domain.pagination.Pagination;
import br.com.eprecise.domain.utils.ReflectionUtils;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
public abstract class GenericRepository<E extends EntityJpa, K, D extends EntityValidable> {

    protected static final String DOT_DELIMITER = ".";

    protected JpaRepository<E, K> repository;

    public GenericRepository(JpaRepository<E, K> repository) {
        this.repository = repository;
    }

    public List<D> findAll(SearchCriteria searchCriteria) {
        final CriteriaBuilder cb = resolveEntityManager().getCriteriaBuilder();
        final CriteriaQuery<E> query = cb.createQuery(resolveEntityClass());
        final Root<E> root = query.from(resolveEntityClass());
        final List<Predicate> predicates = new ArrayList<>();

        final List<String> fields = ReflectionUtils.getFieldNames(resolveEntityClass());

        for (final AttributeFilter attribute: searchCriteria.getAttributes()) {
            try {
                final String[] fieldNames = attribute.getFieldName().split(Pattern.quote(DOT_DELIMITER));
                if (fieldNames.length > 1) {
                    Join<Object, Object> join = null;
                    for (int i = 0 ; i < fieldNames.length ; i++) {
                        if (i == fieldNames.length - 1) {
                            continue;
                        }
                        join = Objects.isNull(join) ? root.join(fieldNames[i]) : join.join(fieldNames[i]);
                    }
                    if (Objects.isNull(join)) {
                        continue;   
                    }

                    predicates.add(cb.like(cb.lower(join.get(fieldNames[fieldNames.length - 1]).as(String.class)), "%" + attribute.getValue().toLowerCase() + "%"));
                    continue;
                }
            } catch (Exception error) {
                throw new AttributeNotFoundException(attribute.getFieldName());
            }

            if (!fields.contains(attribute.getFieldName())) {
                throw new AttributeNotFoundException(attribute.getFieldName());
            }

            predicates.add(cb.like(cb.lower(root.get(attribute.getFieldName()).as(String.class)), "%" + attribute.getValue().toLowerCase() + "%"));
        }

        query.where(predicates.toArray(new Predicate[] {}));
        final OrderType orderType = searchCriteria.getPagination().getPageOrder();
        query.orderBy(OrderType.ASC.equals(orderType) ? cb.asc(root.get(resolveOrderByAttributeName())) : cb.desc(root.get(resolveOrderByAttributeName())));

        final TypedQuery<E> entityManagerQuery = resolveEntityManager().createQuery(query);
        final Pagination pagination = searchCriteria.getPagination();
        entityManagerQuery.setMaxResults(pagination.getPageSize());
        entityManagerQuery.setFirstResult((pagination.getPageNumber() - 1) * pagination.getPageSize());
        return entityManagerQuery.getResultList().stream().map(this::toDomain).collect(Collectors.toList());    
    }

    @Transactional
    public Optional<D> save(D record) {
        final E entity = toEntity(record);
        repository.save(entity);
        return Optional.of(toDomain(entity));
    }

    public Long count() {
        return repository.count();
    }

    public Optional<D> findById(K identifier) {
        return repository.findById(identifier)
            .map(this::toDomain);
    }

    public boolean existsById(K identifier) {
        return repository.existsById(identifier);
    }

    @Transactional
    public void deleteById(K identifier) {
        findById(identifier) .orElseThrow(() -> new EntityNotFoundException(resolveHumanName(), identifier));
        repository.deleteById(identifier);
    }

    abstract Class<E> resolveEntityClass();

    abstract EntityManager resolveEntityManager();

    abstract String resolveOrderByAttributeName();

    abstract D toDomain(E source);

    abstract E toEntity(D source);

    abstract String resolveHumanName();

    
}
