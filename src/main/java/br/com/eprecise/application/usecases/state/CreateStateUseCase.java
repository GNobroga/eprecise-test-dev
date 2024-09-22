package br.com.eprecise.application.usecases.state;

import br.com.eprecise.application.inbound.state.CreateStateUseCasePort;
import br.com.eprecise.application.inbound.state.inputs.CreateStateInput;
import br.com.eprecise.application.inbound.state.outputs.StateIdOutput;
import br.com.eprecise.application.outbound.StateRepositoryPort;
import br.com.eprecise.domain.entities.state.State;
import br.com.eprecise.domain.exceptions.AbbreviationAlreadyExistsException;
import br.com.eprecise.domain.exceptions.DomainInvalidException;
import br.com.eprecise.domain.validations.ValidationHandler;
import br.com.eprecise.domain.validations.handlers.NotificationHandler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateStateUseCase implements CreateStateUseCasePort {

    private final StateRepositoryPort stateRepositoryPort;

    @Override
    public StateIdOutput execute(CreateStateInput in) {

        if (stateRepositoryPort.existsByAbbreviation(in.getAbbreviation())) {
            throw new AbbreviationAlreadyExistsException(in.getAbbreviation());
        }

        final State state = State.create(in.getName(), in.getAbbreviation());
        
        final ValidationHandler handler = new NotificationHandler();

        state.validate(handler);

        if (handler.hasError()) {
            throw new DomainInvalidException(handler.getErrors());
        }

        final String id = stateRepositoryPort.save(state).get().getId().getUuid().toString();
        return new StateIdOutput(id);
    }
    
}
