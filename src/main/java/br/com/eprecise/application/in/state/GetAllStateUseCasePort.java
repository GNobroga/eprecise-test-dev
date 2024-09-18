package br.com.eprecise.application.in.state;

import br.com.eprecise.application.in.state.outputs.StateRecordOutput;
import br.com.eprecise.domain.filter.SearchCriteria;
import br.com.eprecise.domain.pagination.Page;
import br.com.eprecise.domain.usecases.InputOutputUseCase;

public interface GetAllStateUseCasePort extends InputOutputUseCase<SearchCriteria, Page<StateRecordOutput>> {}
