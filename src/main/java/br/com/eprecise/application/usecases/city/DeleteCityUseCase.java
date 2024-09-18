package br.com.eprecise.application.usecases.city;

import br.com.eprecise.application.in.city.DeleteCityUseCasePort;
import br.com.eprecise.application.out.CityRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteCityUseCase implements DeleteCityUseCasePort {

    private final CityRepositoryPort cityRepositoryPort;
    
    @Override
    public void execute(String in) {
       cityRepositoryPort.deleteById(in);
    }
    
}
