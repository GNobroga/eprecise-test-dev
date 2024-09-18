package br.com.eprecise.application.inbound.city.inputs;

import br.com.eprecise.domain.pagination.Pagination;
import lombok.Value;

@Value
public class CitySearchByStateInput {
    private String stateId;
    private Pagination pagination;
}