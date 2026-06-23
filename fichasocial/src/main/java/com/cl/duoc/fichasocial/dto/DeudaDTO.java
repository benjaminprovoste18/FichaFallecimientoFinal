package com.cl.duoc.fichasocial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeudaDTO {
    private Long id;
    private String rutFallecido;
    private String tipoDeuda;
    private String institucion;
    private Double monto;
}
