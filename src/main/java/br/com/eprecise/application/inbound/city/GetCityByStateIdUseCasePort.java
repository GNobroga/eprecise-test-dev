package br.com.eprecise.application.inbound.city;

import br.com.eprecise.application.inbound.city.inputs.CitySearchByStateInput;
import br.com.eprecise.application.inbound.city.outputs.CityRecordOutput;
import br.com.eprecise.domain.pagination.Page;
import br.com.eprecise.domain.usecases.InputOutputUseCase;

public interface GetCityByStateIdUseCasePort extends InputOutputUseCase<CitySearchByStateInput, Page<CityRecordOutput>> {}
