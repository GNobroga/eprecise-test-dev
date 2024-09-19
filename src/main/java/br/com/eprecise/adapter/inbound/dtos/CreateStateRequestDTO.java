package br.com.eprecise.adapter.inbound.dtos;

import javax.validation.constraints.NotBlank;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateStateRequestDTO {

    @Schema(
        description = "Name of the state to be created",
        example = "California",
        required = true
    )
    @NotBlank(message = "Name cannot be blank")
    @Length(max = 100, message = "The name cannot be longer than 100 characters.")
    private String name;

    @Schema(
        description = "Abbreviation of the state, usually a two-letter code",
        example = "CA",
        required = true
    )
    @NotBlank(message = "Abbreviation cannot be blank")
    @Length(max = 10, min = 2, message = "Abbreviation must be between 2 and 10 characters long")
    private String abbreviation;
}
