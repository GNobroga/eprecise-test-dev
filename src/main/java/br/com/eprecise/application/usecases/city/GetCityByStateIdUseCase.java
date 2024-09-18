package br.com.eprecise.application.usecases.city;

import java.util.List;
import java.util.stream.Collectors;

import br.com.eprecise.application.in.city.GetCityByStateIdUseCasePort;
import br.com.eprecise.application.in.city.inputs.CitySearchByStateInput;
import br.com.eprecise.application.in.city.outputs.CityRecordOutput;
import br.com.eprecise.application.in.state.outputs.StateRecordOutput;
import br.com.eprecise.application.out.CityRepositoryPort;
import br.com.eprecise.application.out.StateRepositoryPort;
import br.com.eprecise.domain.entities.state.State;
import br.com.eprecise.domain.pagination.Page;
import br.com.eprecise.domain.pagination.Pagination;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetCityByStateIdUseCase implements GetCityByStateIdUseCasePort {

    private final CityRepositoryPort cityRepositoryPort;

    private final StateRepositoryPort stateRepositoryPort;

    @Override
    public Page<CityRecordOutput> execute(CitySearchByStateInput in) {
         final Pagination pagination = in.getPagination();
        final List<CityRecordOutput> items = cityRepositoryPort.findByStateId(in.getStateId(), in.getPagination()).stream()
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
