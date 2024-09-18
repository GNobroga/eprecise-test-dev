package br.com.eprecise.application.inbound.state;

import br.com.eprecise.application.inbound.state.outputs.StateRecordOutput;
import br.com.eprecise.domain.filter.SearchCriteria;
import br.com.eprecise.domain.pagination.Page;
import br.com.eprecise.domain.usecases.InputOutputUseCase;

public interface GetAllStateUseCasePort extends InputOutputUseCase<SearchCriteria, Page<StateRecordOutput>> {}
