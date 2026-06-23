package com.cl.duoc.fichasocial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonaDTO {

    private String rut;
    private String nombres;
    private String apellidos;
    private Integer edad;
    private String direccion;
    private String fechaNacimiento;
}