package br.com.eprecise.adapter.inbound.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import br.com.eprecise.application.inbound.city.CreateCityUseCasePort;
import br.com.eprecise.application.inbound.city.DeleteCityUseCasePort;
import br.com.eprecise.application.inbound.city.FindCityByNameUseCasePort;
import br.com.eprecise.application.inbound.city.GetAllCityUseCasePort;
import br.com.eprecise.application.inbound.city.GetCityByStateIdUseCasePort;
import br.com.eprecise.application.inbound.city.GetCityRecordCountUseCasePort;
import br.com.eprecise.application.inbound.city.UpdateCityUseCasePort;
import br.com.eprecise.application.inbound.state.CreateStateUseCasePort;
import br.com.eprecise.application.inbound.state.DeleteStateUseCasePort;
import br.com.eprecise.application.inbound.state.GetAllStateUseCasePort;
import br.com.eprecise.application.inbound.state.GetStateRecordCountUseCasePort;
import br.com.eprecise.application.inbound.state.UpdateStateUseCasePort;
import br.com.eprecise.application.outbound.CityRepositoryPort;
import br.com.eprecise.application.outbound.StateRepositoryPort;
import br.com.eprecise.application.usecases.city.CreateCityUseCase;
import br.com.eprecise.application.usecases.city.DeleteCityUseCase;
import br.com.eprecise.application.usecases.city.FindCityByNameUseCase;
import br.com.eprecise.application.usecases.city.GetAllCityUseCase;
import br.com.eprecise.application.usecases.city.GetCityByStateIdUseCase;
import br.com.eprecise.application.usecases.city.GetCityRecordCountUseCase;
import br.com.eprecise.application.usecases.city.UpdateCityUseCase;
import br.com.eprecise.application.usecases.state.CreateStateUseCase;
import br.com.eprecise.application.usecases.state.DeleteStateUseCase;
import br.com.eprecise.application.usecases.state.GetAllStateUseCase;
import br.com.eprecise.application.usecases.state.GetStateRecordCountUseCase;
import br.com.eprecise.application.usecases.state.UpdateStateUseCase;

@ApplicationScoped
public class UseCaseConfig {

    @Inject
    private CityRepositoryPort cityRepositoryPort;

    @Inject
    private StateRepositoryPort stateRepositoryPort;

    // States Use Cases 
    @Produces
    public GetAllStateUseCasePort getAllStateUseCasePort() {
        return new GetAllStateUseCase(stateRepositoryPort);
    }

    @Produces
    public CreateStateUseCasePort createStateUseCasePort() {
        return new CreateStateUseCase(stateRepositoryPort);
    }

    @Produces
    public UpdateStateUseCasePort updateStateUseCasePort() {
        return new UpdateStateUseCase(stateRepositoryPort);
    }

    @Produces
    public DeleteStateUseCasePort deleteStateUseCasePort() {
        return new DeleteStateUseCase(stateRepositoryPort);
    }

    @Produces
    public GetStateRecordCountUseCasePort getStateRecordUseCasePort() {
        return new GetStateRecordCountUseCase(stateRepositoryPort);
    }

    // City Use Cases
    @Produces
    private CreateCityUseCasePort createCityUseCasePort() {
        return new CreateCityUseCase(cityRepositoryPort, stateRepositoryPort);
    }

    @Produces
    private DeleteCityUseCasePort deleteCityUseCasePort() {
        return new DeleteCityUseCase(cityRepositoryPort);
    }

    @Produces
    private GetAllCityUseCasePort getAllCityUseCasePort() {
        return new GetAllCityUseCase(cityRepositoryPort, stateRepositoryPort);
    }

    @Produces
    private GetCityRecordCountUseCasePort getCityRecordCountUseCasePort() {
        return new GetCityRecordCountUseCase(cityRepositoryPort);
    }

    @Produces
    private GetCityByStateIdUseCasePort getCityByStateIdUseCasePort() {
        return new GetCityByStateIdUseCase(stateRepositoryPort, getAllCityUseCasePort());
    }

    @Produces
    private UpdateCityUseCasePort updateCityUseCasePort() {
        return new UpdateCityUseCase(cityRepositoryPort, stateRepositoryPort);
    }

    @Produces
    private FindCityByNameUseCasePort findCityByNameUseCasePort() {
        return new FindCityByNameUseCase(getAllCityUseCasePort());
    }
}
