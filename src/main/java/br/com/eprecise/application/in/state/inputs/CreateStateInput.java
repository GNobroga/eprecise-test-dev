package br.com.eprecise.application.in.state.inputs;

import lombok.Value;

@Value
public class CreateStateInput {
    private String name;
    private String abbreviation;
}
