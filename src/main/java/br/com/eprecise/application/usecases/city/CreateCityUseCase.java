package br.com.eprecise.application.usecases.city;

import java.util.Objects;

import javax.persistence.EntityExistsException;

import br.com.eprecise.application.inbound.city.CreateCityUseCasePort;
import br.com.eprecise.application.inbound.city.inputs.CreateCityInput;
import br.com.eprecise.application.inbound.city.outputs.CityIdOutput;
import br.com.eprecise.application.outbound.CityRepositoryPort;
import br.com.eprecise.application.outbound.StateRepositoryPort;
import br.com.eprecise.domain.entities.city.City;
import br.com.eprecise.domain.entities.state.State;
import br.com.eprecise.domain.exceptions.DomainInvalidException;
import br.com.eprecise.domain.exceptions.EntityConflictException;
import br.com.eprecise.domain.validations.ValidationHandler;
import br.com.eprecise.domain.validations.handlers.NotificationHandler;
import br.com.eprecise.domain.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateCityUseCase implements CreateCityUseCasePort {

    private final CityRepositoryPort cityRepositoryPort;

    private final StateRepositoryPort stateRepositoryPort;

    @Override
    public CityIdOutput execute(final CreateCityInput in) {
        final State state = stateRepositoryPort.findByAbbreviation(in.getStateAbbreviation());

        if (Objects.isNull(state)) {
            throw new EntityNotFoundException("State with abbreviation '" + in.getStateAbbreviation() + "' does not exist.");
        }

        final City city = City.create(in.getName(), state.getId().getUuid().toString(), in.getPopulation());

        final ValidationHandler handler = new NotificationHandler();
        city.validate(handler);

        if (handler.hasError()) {
            throw new DomainInvalidException(handler.getErrors());
        }

        if (cityRepositoryPort.existsByStateAbbreviationAndName(state.getAbbreviation(), in.getName())) {
            throw new EntityConflictException("A city with the name '" + in.getName() + "' already exists in the state with the abbreviation '" + state.getAbbreviation() + "'. Each state can only have one city with this name.");
        }

        final String id = cityRepositoryPort.save(city).getId().getUuid().toString();
        return new CityIdOutput(id);
    }
    
}
