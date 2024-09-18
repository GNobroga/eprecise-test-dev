package br.com.eprecise.application.in.city.inputs;

import br.com.eprecise.domain.pagination.Pagination;
import lombok.Value;

@Value
public class CitySearchByNameInput {
    private String cityName;
    private Pagination pagination;
}
