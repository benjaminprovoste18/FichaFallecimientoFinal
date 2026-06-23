package com.cl.duoc.fichasocial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanzaDTO {
    private String rut;
    private Double ingresoMensual;
    private Double deuda;
}
