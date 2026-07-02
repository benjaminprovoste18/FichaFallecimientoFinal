package com.cl.duoc.service_personas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "personas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
