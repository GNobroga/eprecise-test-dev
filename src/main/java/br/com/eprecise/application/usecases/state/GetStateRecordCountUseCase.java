package br.com.eprecise.application.usecases.state;

import br.com.eprecise.application.in.state.GetStateRecordCountUseCasePort;
import br.com.eprecise.application.in.state.outputs.GetStateRecordCountOutput;
import br.com.eprecise.application.out.StateRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetStateRecordCountUseCase implements GetStateRecordCountUseCasePort {

    private final StateRepositoryPort stateRepositoryPort;

    @Override
    public GetStateRecordCountOutput execute() {
        return new GetStateRecordCountOutput(stateRepositoryPort.count());
    }
    
}
