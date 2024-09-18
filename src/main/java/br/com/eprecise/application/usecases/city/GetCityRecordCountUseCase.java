package br.com.eprecise.application.usecases.city;

import br.com.eprecise.application.inbound.city.GetCityRecordCountUseCasePort;
import br.com.eprecise.application.inbound.city.outputs.CityRecordCountOutput;
import br.com.eprecise.application.outbound.CityRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetCityRecordCountUseCase implements GetCityRecordCountUseCasePort {
    
    private final CityRepositoryPort cityRepositoryPort;

    @Override
    public CityRecordCountOutput execute() {
       return new CityRecordCountOutput(cityRepositoryPort.count());
    }

}
