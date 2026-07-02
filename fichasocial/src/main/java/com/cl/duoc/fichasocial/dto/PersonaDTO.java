package com.cl.duoc.fichasocial.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonaDTO {

    @NotBlank
    private String rut;

    @NotBlank
    @Size(min = 2, max = 100)
    private String nombres;

    @NotBlank
    @Size(min = 2, max = 100)
    private String apellidos;

    @NotNull
    @Positive
    private Integer edad;

    @NotBlank
    private String direccion;

    @NotBlank
    private String fechaNacimiento;
}