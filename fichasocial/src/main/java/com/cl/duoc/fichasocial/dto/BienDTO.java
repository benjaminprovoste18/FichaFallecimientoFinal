package com.cl.duoc.fichasocial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BienDTO {
    private Long id;
    private String rutFallecido;
    private String tipoBien;
    private String descripcion;
    private Double valorEstimado;
}
