package com.cl.duoc.fichasocial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FichaSocialDTO {
    private PersonaDTO persona;
    private FinanzaDTO finanza;
    private FamiliaDTO familia;
    private AccesoDTO acceso;
    private BienDTO bien;
    private DefuncionDTO defuncion;
    private DocumentoDTO documento;
    private BeneficioDTO beneficio;
    private DeudaDTO deuda;
}
