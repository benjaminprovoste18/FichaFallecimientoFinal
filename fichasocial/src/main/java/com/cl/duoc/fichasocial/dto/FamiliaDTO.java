package com.cl.duoc.fichasocial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamiliaDTO {

    private String rutPersona;
    private String conyuge;
    private Integer cantidadHijos;
    private String direccionGrupoFamiliar;
    private String observaciones;
}