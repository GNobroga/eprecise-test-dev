package br.com.eprecise.application.inbound.state;

import br.com.eprecise.application.inbound.state.inputs.CreateStateInput;
import br.com.eprecise.application.inbound.state.outputs.StateIdOutput;
import br.com.eprecise.domain.usecases.InputOutputUseCase;

public interface CreateStateUseCasePort extends InputOutputUseCase<CreateStateInput, StateIdOutput> {}
