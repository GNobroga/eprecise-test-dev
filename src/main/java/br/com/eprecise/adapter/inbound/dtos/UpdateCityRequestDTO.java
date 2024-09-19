package br.com.eprecise.adapter.inbound.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateCityRequestDTO {
    
    @Schema(
        description = "Updated name of the city",
        example = "Los Angeles",
        required = true
    )
    @NotBlank(message = "The city name must not be empty")
    private String name;

    @Schema(
        description = "Updated state abbreviation where the city is located, must be uppercase",
        example = "CA",
        required = true
    )
    @NotBlank(message = "The state abbreviation must not be empty")
    @Size(min = 2, max = 10, message = "The state abbreviation must be between 2 and 10 characters")
    @Pattern(regexp = "^[A-Z]{2,10}$", message = "The state abbreviation must contain only uppercase letters and be between 2 and 10 characters")
    private String stateAbbreviation;

    @Schema(
        description = "Updated population of the city",
        example = "4000000",
        required = true
    )
    @NotNull(message = "The population must not be null")
    @Positive(message = "The population must be a positive number")
    private Long population;
}
