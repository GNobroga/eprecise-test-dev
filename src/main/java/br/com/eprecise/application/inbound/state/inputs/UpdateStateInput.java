package br.com.eprecise.application.inbound.state.inputs;

import lombok.Value;

@Value
public class UpdateStateInput {
    private String id;
    private String name;
    private String abbreviation;
}
