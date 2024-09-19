package br.com.eprecise.application.outbound;

import java.util.List;

import br.com.eprecise.domain.entities.city.City;
import br.com.eprecise.domain.pagination.Pagination;

public interface CityRepositoryPort extends RepositoryPort<City> {
    boolean existsByStateAbbreviationAndName(String stateAbbreviation, String cityName);
}
