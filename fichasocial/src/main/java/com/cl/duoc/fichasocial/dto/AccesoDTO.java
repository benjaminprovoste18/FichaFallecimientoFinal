package com.cl.duoc.fichasocial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccesoDTO {

    private String rutPersona;
    private Boolean aguaPotable;
    private Boolean electricidad;
    private Boolean alcantarillado;
    private String tipoVivienda;
    private String observaciones;
}