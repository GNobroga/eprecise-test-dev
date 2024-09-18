package br.com.eprecise.application.inbound.city;

import br.com.eprecise.application.inbound.city.outputs.CityRecordOutput;
import br.com.eprecise.domain.filter.SearchCriteria;
import br.com.eprecise.domain.pagination.Page;
import br.com.eprecise.domain.usecases.InputOutputUseCase;

public interface GetAllCityUseCasePort extends InputOutputUseCase<SearchCriteria, Page<CityRecordOutput>> {}
