package br.com.eprecise.application.usecases.state;

import br.com.eprecise.application.inbound.state.GetStateRecordCountUseCasePort;
import br.com.eprecise.application.inbound.state.outputs.GetStateRecordCountOutput;
import br.com.eprecise.application.outbound.StateRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetStateRecordCountUseCase implements GetStateRecordCountUseCasePort {

    private final StateRepositoryPort stateRepositoryPort;

    @Override
    public GetStateRecordCountOutput execute() {
        return new GetStateRecordCountOutput(stateRepositoryPort.count());
    }
    
}
