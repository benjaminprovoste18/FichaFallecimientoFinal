package com.cl.duoc.fichasocial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamiliaDTO {
    private String rut;
    private int integrantes;
    private List<String> miembrosNombres;
}
