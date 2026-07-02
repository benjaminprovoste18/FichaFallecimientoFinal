package com.cl.duoc.service_finanzas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "finanzas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Finanza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rut_persona", nullable = false, unique = true)
    @NotBlank
    private String rutPersona;

    @Column(name = "ingresos_mensuales")
    @NotNull
    @Positive
    private Double ingresosMensuales;

    @Column(name = "deudas")
    @NotNull
    @PositiveOrZero
    private Double deudas;

    @Column(name = "situacion_laboral")
    @NotBlank
    private String situacionLaboral;

    @Column(name = "observaciones")
    @Size(max = 255)
    private String observaciones;
}
