package br.com.eprecise.application.in.city;

import br.com.eprecise.application.in.city.inputs.CreateCityInput;
import br.com.eprecise.application.in.city.outputs.CityIdOutput;
import br.com.eprecise.domain.usecases.InputOutputUseCase;

public interface CreateCityUseCasePort extends InputOutputUseCase<CreateCityInput, CityIdOutput> {}
