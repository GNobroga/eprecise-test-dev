package br.com.eprecise.adapter.outbound.jpa.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.eprecise.adapter.outbound.jpa.entities.CityEntityJpa;

public interface CityRepositoryJpa extends JpaRepository<CityEntityJpa, String> {
    @Query("select count(c) > 0 from CityEntityJpa c join fetch c.state s where s.abbreviation = ?1 and c.name = ?2")
    boolean existsByStateAbbreviationAndName(String stateAbbreviation, String cityName);

    Page<CityEntityJpa> findByStateId(String stateId, Pageable pageable);

    @Query("FROM CityEntityJpa c WHERE LENGTH(c.name) >= 3 AND LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<CityEntityJpa> findCitiesByNameWithLengthGreaterThanThree(@Param("name") String name, Pageable pageable);
}
