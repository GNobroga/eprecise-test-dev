package br.com.eprecise.application.inbound.city.outputs;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import br.com.eprecise.application.inbound.state.outputs.StateRecordOutput;
import lombok.Value;

@Value
public class CityRecordOutput {

    @Schema(description = "Unique identifier of the city", example = "123e4567-e89b-12d3-a456-426614174010")
    private String id;

    @Schema(description = "Name of the city", example = "Castelo")
    private String name;

    @Schema(description = "Population of the city", example = "8419000")
    private Long population;

    @Schema(description = "State where the city is located", implementation = StateRecordOutput.class)
    private StateRecordOutput state;
}