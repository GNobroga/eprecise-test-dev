package br.com.eprecise.application.usecases.state;

import java.util.Objects;

import br.com.eprecise.application.inbound.state.UpdateStateUseCasePort;
import br.com.eprecise.application.inbound.state.inputs.UpdateStateInput;
import br.com.eprecise.application.outbound.StateRepositoryPort;
import br.com.eprecise.domain.entities.state.State;
import br.com.eprecise.domain.exceptions.AbbreviationAlreadyExistsException;
import br.com.eprecise.domain.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateStateUseCase implements UpdateStateUseCasePort {

    private final StateRepositoryPort stateRepositoryPort;

    @Override
    public void execute(final UpdateStateInput in) {
        final State target = stateRepositoryPort.findById(in.getId());

        // Essa verificação só é feita caso o stateRepositoryPort.findById não retorne exceção, o que não é o caso.
        if (Objects.isNull(target)) {
            throw new EntityNotFoundException(State.class.getName(), in.getId());
        }

        if (!target.getAbbreviation().equalsIgnoreCase(in.getAbbreviation()) && stateRepositoryPort.existsByAbbreviation(in.getAbbreviation())) {
            throw new AbbreviationAlreadyExistsException(in.getAbbreviation());
        }

        target.setName(in.getName());
        target.setAbbreviation(in.getAbbreviation());
        stateRepositoryPort.save(target);
    }
    
}
