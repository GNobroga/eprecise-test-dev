package br.com.eprecise.adapter.inbound.facade;

import javax.enterprise.context.ApplicationScoped;

import br.com.eprecise.application.inbound.state.CreateStateUseCasePort;
import br.com.eprecise.application.inbound.state.DeleteStateUseCasePort;
import br.com.eprecise.application.inbound.state.GetAllStateUseCasePort;
import br.com.eprecise.application.inbound.state.GetStateRecordCountUseCasePort;
import br.com.eprecise.application.inbound.state.UpdateStateUseCasePort;
import br.com.eprecise.application.inbound.state.inputs.CreateStateInput;
import br.com.eprecise.application.inbound.state.inputs.UpdateStateInput;
import br.com.eprecise.application.inbound.state.outputs.GetStateRecordCountOutput;
import br.com.eprecise.application.inbound.state.outputs.StateIdOutput;
import br.com.eprecise.application.inbound.state.outputs.StateRecordOutput;
import br.com.eprecise.domain.filter.SearchCriteria;
import br.com.eprecise.domain.pagination.Page;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class StateFacade {
    
    private final GetAllStateUseCasePort getAllStateUseCasePort;

    private final CreateStateUseCasePort createStateUseCasePort;

    private final UpdateStateUseCasePort updateStateUseCasePort;

    private final DeleteStateUseCasePort deleteStateUseCasePort;

    private final GetStateRecordCountUseCasePort getStateRecordCountUseCasePort;

    public Page<StateRecordOutput> findAll(SearchCriteria searchCriteria) {
        return getAllStateUseCasePort.execute(searchCriteria);
    }

    public StateIdOutput create(CreateStateInput input) {
        return createStateUseCasePort.execute(input);
    }

    public void update(UpdateStateInput input) {
        updateStateUseCasePort.execute(input);
    }

    public boolean deleteById(String identifier) {
        try {
            deleteStateUseCasePort.execute(identifier);
            return true;
        } catch(Exception error) {
            return false;
        } 
    }

    public GetStateRecordCountOutput count() {
        return getStateRecordCountUseCasePort.execute();
    }
}
