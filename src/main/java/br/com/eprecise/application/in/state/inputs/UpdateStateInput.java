package br.com.eprecise.application.in.state.inputs;

import lombok.Value;

@Value
public class UpdateStateInput {
    private String id;
    private String name;
    private String abbreviation;
}
