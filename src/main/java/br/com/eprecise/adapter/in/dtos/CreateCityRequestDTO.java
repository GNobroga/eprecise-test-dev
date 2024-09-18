package br.com.eprecise.adapter.in.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCityRequestDTO {
    
    @NotBlank(message = "The city name must not be empty")
    private String name;

    @NotBlank(message = "The state abbreviation must not be empty")
    @Size(min = 2, max = 10, message = "The state abbreviation must be between 2 and 10 characters")
    @Pattern(regexp = "^[A-Z]{2,10}$", message = "The state abbreviation must contain only uppercase letters and be between 2 and 10 characters")
    private String stateAbbreviation;

    @NotNull(message = "The population must not be null")
    @Positive(message = "The population must be a positive number")
    private Long population;
}
