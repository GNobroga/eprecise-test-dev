package br.com.eprecise.application.usecases.state;

import br.com.eprecise.application.in.state.DeleteStateUseCasePort;
import br.com.eprecise.application.out.StateRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteStateUseCase implements DeleteStateUseCasePort {

    private final StateRepositoryPort stateRepositoryPort;

    @Override
    public void execute(String in) {
       stateRepositoryPort.deleteById(in);
    }
    
}
