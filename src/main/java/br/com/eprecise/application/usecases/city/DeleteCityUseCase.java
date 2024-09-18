package br.com.eprecise.application.usecases.city;

import br.com.eprecise.application.inbound.city.DeleteCityUseCasePort;
import br.com.eprecise.application.outbound.CityRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteCityUseCase implements DeleteCityUseCasePort {

    private final CityRepositoryPort cityRepositoryPort;
    
    @Override
    public void execute(String in) {
       cityRepositoryPort.deleteById(in);
    }
    
}
