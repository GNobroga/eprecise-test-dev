package br.com.eprecise.application.usecases.state;

import java.util.List;
import java.util.stream.Collectors;

import br.com.eprecise.application.inbound.state.GetAllStateUseCasePort;
import br.com.eprecise.application.inbound.state.outputs.StateRecordOutput;
import br.com.eprecise.application.outbound.StateRepositoryPort;
import br.com.eprecise.domain.filter.SearchCriteria;
import br.com.eprecise.domain.pagination.Page;
import br.com.eprecise.domain.pagination.Pagination;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAllStateUseCase implements GetAllStateUseCasePort {

    private final StateRepositoryPort stateRepositoryPort;

    @Override
    public Page<StateRecordOutput> execute(final SearchCriteria in) {
        final Pagination pagination = in.getPagination();
        final List<StateRecordOutput> items = stateRepositoryPort.findAll(in).stream().map(StateRecordOutput::from).collect(Collectors.toList());
        final Page<StateRecordOutput> page = Page.create(pagination, items, stateRepositoryPort.count());
        return page;
    }
    
}
