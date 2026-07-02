package com.cl.duoc.service_deudas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "deudas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Deuda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rut_fallecido", nullable = false)
    @NotBlank
    private String rutFallecido;

    @Column(name = "tipo_deuda")
    @NotBlank
    private String tipoDeuda;

    @Column(name = "institucion")
    @NotBlank
    private String institucion;

    @Column(name = "monto")
    @NotNull
    @PositiveOrZero
    private Double monto;

}
