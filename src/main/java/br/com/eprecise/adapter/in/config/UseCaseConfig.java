package br.com.eprecise.adapter.in.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import br.com.eprecise.application.in.city.CreateCityUseCasePort;
import br.com.eprecise.application.in.city.DeleteCityUseCasePort;
import br.com.eprecise.application.in.city.FindCityByNameUseCasePort;
import br.com.eprecise.application.in.city.GetAllCityUseCasePort;
import br.com.eprecise.application.in.city.GetCityByStateIdUseCasePort;
import br.com.eprecise.application.in.city.GetCityRecordCountUseCasePort;
import br.com.eprecise.application.in.city.UpdateCityUseCasePort;
import br.com.eprecise.application.in.state.CreateStateUseCasePort;
import br.com.eprecise.application.in.state.DeleteStateUseCasePort;
import br.com.eprecise.application.in.state.GetAllStateUseCasePort;
import br.com.eprecise.application.in.state.GetStateRecordCountUseCasePort;
import br.com.eprecise.application.in.state.UpdateStateUseCasePort;
import br.com.eprecise.application.out.CityRepositoryPort;
import br.com.eprecise.application.out.StateRepositoryPort;
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
        return new GetCityByStateIdUseCase(cityRepositoryPort, stateRepositoryPort);
    }

    @Produces
    private UpdateCityUseCasePort updateCityUseCasePort() {
        return new UpdateCityUseCase(cityRepositoryPort, stateRepositoryPort);
    }

    @Produces
    private FindCityByNameUseCasePort findCityByNameUseCasePort() {
        return new FindCityByNameUseCase(cityRepositoryPort, stateRepositoryPort);
    }
}
