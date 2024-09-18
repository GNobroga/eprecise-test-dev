package br.com.eprecise.application.inbound.city;

import br.com.eprecise.application.inbound.city.inputs.CreateCityInput;
import br.com.eprecise.application.inbound.city.outputs.CityIdOutput;
import br.com.eprecise.domain.usecases.InputOutputUseCase;

public interface CreateCityUseCasePort extends InputOutputUseCase<CreateCityInput, CityIdOutput> {}
