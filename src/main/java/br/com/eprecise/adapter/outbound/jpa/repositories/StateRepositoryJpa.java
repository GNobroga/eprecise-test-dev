package br.com.eprecise.adapter.outbound.jpa.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eprecise.adapter.outbound.jpa.entities.StateEntityJpa;

public interface StateRepositoryJpa extends JpaRepository<StateEntityJpa, String> {
    boolean existsByAbbreviation(String abbreviation);
    Optional<StateEntityJpa> findByAbbreviationIgnoreCase(String abbreviation);
}
