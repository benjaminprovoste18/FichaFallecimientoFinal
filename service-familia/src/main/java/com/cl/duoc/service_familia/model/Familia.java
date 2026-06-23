package com.cl.duoc.service_familia.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "familia")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Familia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rutPersona;
    private String conyuge;
    private Integer cantidadHijos;
    private String direccionGrupoFamiliar;
    private String observaciones;
}
