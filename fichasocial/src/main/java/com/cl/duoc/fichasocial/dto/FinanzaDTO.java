package com.cl.duoc.fichasocial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanzaDTO {

    private String rutPersona;
    private Double ingresosMensuales;
    private Double deudas;
    private String situacionLaboral;
    private String observaciones;
}