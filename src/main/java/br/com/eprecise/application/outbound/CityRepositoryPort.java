package br.com.eprecise.application.outbound;

import br.com.eprecise.domain.entities.city.City;

public interface CityRepositoryPort extends RepositoryPort<City> {
    boolean existsByStateAbbreviationAndName(String stateAbbreviation, String cityName);
}
