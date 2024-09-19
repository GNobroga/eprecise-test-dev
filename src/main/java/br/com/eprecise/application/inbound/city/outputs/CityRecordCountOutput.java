package br.com.eprecise.application.inbound.city.outputs;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.Value;

@Value
public class CityRecordCountOutput {
    @Schema(
        description = "The number of cities recorded",
        example = "2500"
    )
    private Long count;
}
