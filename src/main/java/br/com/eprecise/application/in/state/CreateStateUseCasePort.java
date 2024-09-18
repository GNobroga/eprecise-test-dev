package br.com.eprecise.application.in.state;

import br.com.eprecise.application.in.state.inputs.CreateStateInput;
import br.com.eprecise.application.in.state.outputs.StateIdOutput;
import br.com.eprecise.domain.usecases.InputOutputUseCase;

public interface CreateStateUseCasePort extends InputOutputUseCase<CreateStateInput, StateIdOutput> {}
