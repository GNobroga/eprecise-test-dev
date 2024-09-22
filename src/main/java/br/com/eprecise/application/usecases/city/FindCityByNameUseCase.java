package br.com.eprecise.application.usecases.city;

import java.util.HashMap;
import java.util.Map;

import br.com.eprecise.application.inbound.city.FindCityByNameUseCasePort;
import br.com.eprecise.application.inbound.city.GetAllCityUseCasePort;
import br.com.eprecise.application.inbound.city.inputs.CitySearchByNameInput;
import br.com.eprecise.application.inbound.city.outputs.CityRecordOutput;
import br.com.eprecise.domain.exceptions.InsufficientCharactersException;
import br.com.eprecise.domain.filter.SearchCriteria;
import br.com.eprecise.domain.pagination.Page;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindCityByNameUseCase implements FindCityByNameUseCasePort {

    private final GetAllCityUseCasePort getAllCityUseCasePort;

    @Override
    public Page<CityRecordOutput> execute(CitySearchByNameInput in) {
        if (in.getCityName().length() < 3) {
            throw new InsufficientCharactersException("City name must be at least 3 characters long.");
        }

        final Map<String, String> params = new HashMap<>();
        params.put(SearchCriteria.FILTER_KEY, String.format("name=%s", in.getCityName()));

        final SearchCriteria searchCriteria = new SearchCriteria(params);
        searchCriteria.setPagination(in.getPagination());
      
        return getAllCityUseCasePort.execute(searchCriteria);
    }
    
}
