package com.cl.duoc.fichasocial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonaDTO {
    private String rut;
    private String nombre;
    private String apellido;
    private String email;
}
