package br.com.eprecise.application.inbound.city.inputs;

import lombok.Value;

@Value
public class UpdateCityInput {
    private String id;
    private String name;
    private String stateAbbreviation;
    private Long population;
}
