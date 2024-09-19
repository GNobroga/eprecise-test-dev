package br.com.eprecise.adapter.outbound.jpa.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.eprecise.adapter.outbound.jpa.entities.CityEntityJpa;

public interface CityRepositoryJpa extends JpaRepository<CityEntityJpa, String> {
    @Query("SELECT COUNT(c) > 0 FROM CityEntityJpa c WHERE c.state.abbreviation = ?1 AND c.name = ?2")
    boolean existsByStateAbbreviationAndName(String stateAbbreviation, String cityName);

    @Query("FROM CityEntityJpa c WHERE LENGTH(:name) >= 3 AND LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<CityEntityJpa> findCitiesByNameWithLengthGreaterThanThree(@Param("name") String name, Pageable pageable);
}
