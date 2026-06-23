package com.cl.duoc.fichasocial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccesoDTO {
    private String rut;
    private boolean habilitado;
    private LocalDateTime ultimoAcceso;
}
