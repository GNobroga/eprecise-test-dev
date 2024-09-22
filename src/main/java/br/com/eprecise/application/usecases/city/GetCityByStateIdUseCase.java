package br.com.eprecise.application.usecases.city;

import java.util.HashMap;
import java.util.Map;

import br.com.eprecise.application.inbound.city.GetAllCityUseCasePort;
import br.com.eprecise.application.inbound.city.GetCityByStateIdUseCasePort;
import br.com.eprecise.application.inbound.city.inputs.CitySearchByStateInput;
import br.com.eprecise.application.inbound.city.outputs.CityRecordOutput;
import br.com.eprecise.application.outbound.StateRepositoryPort;
import br.com.eprecise.domain.exceptions.EntityNotFoundException;
import br.com.eprecise.domain.filter.SearchCriteria;
import br.com.eprecise.domain.pagination.Page;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetCityByStateIdUseCase implements GetCityByStateIdUseCasePort {

    private final StateRepositoryPort stateRepositoryPort;

    private final GetAllCityUseCasePort getAllCityUseCasePort;

    @Override
    public Page<CityRecordOutput> execute(CitySearchByStateInput in) {
        if (!stateRepositoryPort.existsById(in.getStateId())) {
            throw new EntityNotFoundException("State with ID " + in.getStateId() + " not found.");
        }
        final Map<String, String> params = new HashMap<>();
        params.put(SearchCriteria.FILTER_KEY, String.format("state.id=%s", in.getStateId()));

        final SearchCriteria searchCriteria = new SearchCriteria(params);
        searchCriteria.setPagination(in.getPagination());

        return getAllCityUseCasePort.execute(searchCriteria);
    }
    
}
