package com.cl.duoc.fichasocial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeneficioDTO {
    private Long id;
    private String rutFallecido;
    private String nombreBeneficio;
    private String institucion;
    private Double montoBeneficio;
}
