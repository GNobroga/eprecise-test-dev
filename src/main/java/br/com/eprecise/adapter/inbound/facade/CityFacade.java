package br.com.eprecise.adapter.inbound.facade;

import javax.enterprise.context.ApplicationScoped;

import br.com.eprecise.application.inbound.city.CreateCityUseCasePort;
import br.com.eprecise.application.inbound.city.DeleteCityUseCasePort;
import br.com.eprecise.application.inbound.city.FindCityByNameUseCasePort;
import br.com.eprecise.application.inbound.city.GetAllCityUseCasePort;
import br.com.eprecise.application.inbound.city.GetCityByStateIdUseCasePort;
import br.com.eprecise.application.inbound.city.GetCityRecordCountUseCasePort;
import br.com.eprecise.application.inbound.city.UpdateCityUseCasePort;
import br.com.eprecise.application.inbound.city.inputs.CitySearchByNameInput;
import br.com.eprecise.application.inbound.city.inputs.CitySearchByStateInput;
import br.com.eprecise.application.inbound.city.inputs.CreateCityInput;
import br.com.eprecise.application.inbound.city.inputs.UpdateCityInput;
import br.com.eprecise.application.inbound.city.outputs.CityIdOutput;
import br.com.eprecise.application.inbound.city.outputs.CityRecordCountOutput;
import br.com.eprecise.application.inbound.city.outputs.CityRecordOutput;
import br.com.eprecise.domain.filter.SearchCriteria;
import br.com.eprecise.domain.pagination.Page;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class CityFacade {
     
    private final CreateCityUseCasePort createCityUseCasePort;

    private final DeleteCityUseCasePort deleteCityUseCasePort;

    private final GetAllCityUseCasePort getAllCityUseCasePort;

    private final UpdateCityUseCasePort updateCityUseCasePort;

    private final GetCityRecordCountUseCasePort getCityRecordCountUseCasePort;

    private final GetCityByStateIdUseCasePort getCityByStateIdUseCasePort;

    private final FindCityByNameUseCasePort findCityByNameUseCasePort;

    public Page<CityRecordOutput> findAll(SearchCriteria searchCriteria) {
        return getAllCityUseCasePort.execute(searchCriteria);
    }

    public CityIdOutput create(CreateCityInput input) {
        return createCityUseCasePort.execute(input);
    }

    public void update(UpdateCityInput input) {
        updateCityUseCasePort.execute(input);
    }

    public CityRecordCountOutput count() {
        return getCityRecordCountUseCasePort.execute();
    }

    public Page<CityRecordOutput> findByStateId(CitySearchByStateInput input) {
        return getCityByStateIdUseCasePort.execute(input);
    }

    public Page<CityRecordOutput> findByName(CitySearchByNameInput input) {
        return findCityByNameUseCasePort.execute(input);
    }

    public boolean deleteById(String identifier) {
        try {
            deleteCityUseCasePort.execute(identifier);
            return true;
        } catch (Exception error) {
            return false;
        }
    }

}
