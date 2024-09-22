package br.com.eprecise.adapter.outbound;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.eprecise.adapter.outbound.jpa.entities.CityEntityJpa;
import br.com.eprecise.adapter.outbound.jpa.repositories.CityRepositoryJpa;
import br.com.eprecise.application.outbound.CityRepositoryPort;
import br.com.eprecise.domain.entities.city.City;
import lombok.Setter;
import lombok.Getter;

@ApplicationScoped
@Getter
@Setter
public class CityRepositoryAdapter extends GenericRepository<CityEntityJpa, String, City> implements CityRepositoryPort {
    
    @PersistenceContext
    private EntityManager entityManager;

    private final CityRepositoryJpa cityRepositoryJpa;

    public CityRepositoryAdapter(CityRepositoryJpa repository) {
        super(repository);
        this.cityRepositoryJpa = repository;
    }


    @Override
    public boolean existsByStateAbbreviationAndName(String stateAbbreviation, String cityName) {
        return cityRepositoryJpa.existsByStateAbbreviationAndName(stateAbbreviation, cityName);
    }


    @Override
    Class<CityEntityJpa> resolveEntityClass() {
       return CityEntityJpa.class;
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
    City toDomain(CityEntityJpa source) {
       return source.toDomain();
    }


    @Override
    CityEntityJpa toEntity(City source) {
       return CityEntityJpa.from(source);
    }


    @Override
    String resolveHumanName() {
        return "City";
    }
}
