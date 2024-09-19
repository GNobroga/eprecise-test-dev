package br.com.eprecise.application.inbound.city.outputs;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.Value;

@Value
public class CityIdOutput {

     @Schema(
        description = "Unique identifier of the city",
        example = "123e4567-e89b-12d3-a456-426614174000"
    )
    private String cityId;
}
