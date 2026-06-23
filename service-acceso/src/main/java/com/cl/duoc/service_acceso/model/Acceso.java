package com.cl.duoc.service_acceso.model;

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
@Table(name = "acceso")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Acceso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rutPersona;
    private Boolean aguaPotable;
    private Boolean electricidad;
    private Boolean alcantarillado;
    private String tipoVivienda;
    private String observaciones;
}
