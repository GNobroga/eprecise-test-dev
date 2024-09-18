package br.com.eprecise.application.inbound.state.inputs;

import lombok.Value;

@Value
public class CreateStateInput {
    private String name;
    private String abbreviation;
}
