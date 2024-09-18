package br.com.eprecise.adapter.inbound.dtos;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateStateRequestDTO {
    @NotBlank(message = "Name cannot be blank")
    @Length(max = 100, message = "The name cannot be longer than 100 characters.")
    private String name;

    @NotBlank(message = "Abbreviation cannot be blank")
    @Length(max = 10, min = 2, message = "Abbreviation must be between 2 and 10 characters long")
    private String abbreviation;
}
