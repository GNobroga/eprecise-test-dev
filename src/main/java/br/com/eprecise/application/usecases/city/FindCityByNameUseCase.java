package br.com.eprecise.application.usecases.city;

import java.util.List;
import java.util.stream.Collectors;

import br.com.eprecise.application.inbound.city.FindCityByNameUseCasePort;
import br.com.eprecise.application.inbound.city.inputs.CitySearchByNameInput;
import br.com.eprecise.application.inbound.city.outputs.CityRecordOutput;
import br.com.eprecise.application.inbound.state.outputs.StateRecordOutput;
import br.com.eprecise.application.outbound.CityRepositoryPort;
import br.com.eprecise.application.outbound.StateRepositoryPort;
import br.com.eprecise.domain.entities.state.State;
import br.com.eprecise.domain.pagination.Page;
import br.com.eprecise.domain.pagination.Pagination;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindCityByNameUseCase implements FindCityByNameUseCasePort {
    
    private final CityRepositoryPort cityRepositoryPort;

    private final StateRepositoryPort stateRepositoryPort;

    @Override
    public Page<CityRecordOutput> execute(CitySearchByNameInput in) {
        final Pagination pagination = in.getPagination();
        final List<CityRecordOutput> items = cityRepositoryPort.findByName(in.getCityName(), pagination).stream()
            .map(city -> {
                final State state = stateRepositoryPort.findById(city.getStateId());
                return new CityRecordOutput(
                    city.getId().getUuid().toString(), 
                    city.getName(), 
                    city.getPopulation(), 
                    new StateRecordOutput(state.getId().getUuid().toString(), state.getName(), state.getAbbreviation()));
            }).collect(Collectors.toList());
        final Page<CityRecordOutput> page = Page.create(pagination, items, cityRepositoryPort.count());
        return page;
    }
    
}
