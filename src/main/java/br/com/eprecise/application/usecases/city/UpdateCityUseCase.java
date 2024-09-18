package br.com.eprecise.application.usecases.city;

import java.util.Objects;

import br.com.eprecise.application.inbound.city.UpdateCityUseCasePort;
import br.com.eprecise.application.inbound.city.inputs.UpdateCityInput;
import br.com.eprecise.application.outbound.CityRepositoryPort;
import br.com.eprecise.application.outbound.StateRepositoryPort;
import br.com.eprecise.domain.entities.city.City;
import br.com.eprecise.domain.entities.state.State;
import br.com.eprecise.domain.exceptions.DomainInvalidException;
import br.com.eprecise.domain.exceptions.EntityConflictException;
import br.com.eprecise.domain.exceptions.EntityNotFoundException;
import br.com.eprecise.domain.validations.ValidationHandler;
import br.com.eprecise.domain.validations.handlers.NotificationHandler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateCityUseCase implements UpdateCityUseCasePort {
    
    private final CityRepositoryPort cityRepositoryPort;

    private final StateRepositoryPort stateRepositoryPort;

    @Override
    public void execute(UpdateCityInput in) {

        final City city = cityRepositoryPort.findById(in.getId());

        if (Objects.isNull(city)) {
            throw new EntityNotFoundException("City not found with ID: " + in.getId());
        }

        final State state = stateRepositoryPort.findById(in.getStateAbbreviation());

        if (Objects.isNull(state)) {
            throw new EntityNotFoundException("Entity with abbreviation '" + in.getStateAbbreviation() + "' does not exist.");
        }

        final ValidationHandler handler = new NotificationHandler();

        city.validate(handler);

        if (handler.hasError()) {
            throw new DomainInvalidException(handler.getErrors());
        }

        if (city.getName().equalsIgnoreCase(in.getName()) && cityRepositoryPort.existsByStateAbbreviationAndName(state.getAbbreviation(), in.getName())) {
            throw new EntityConflictException(
                String.format("A city with the name '%s' already exists in the state with the abbreviation '%s'. Each state can only have one city with this name.",
                              in.getName(), state.getAbbreviation()));
        }

        city.setName(in.getName());
        city.setStateId(state.getAbbreviation());
        city.setPopulation(in.getPopulation());
        cityRepositoryPort.save(city);
    }
}
