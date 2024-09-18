package br.com.eprecise.domain.usecases;

public interface InputOutputUseCase<In, Out> {
    Out execute(In in);
}
