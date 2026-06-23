package com.cl.duoc.service_deudas.model;

import jakarta.persistence.*;
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
    private String rutFallecido;

    @Column(name = "tipo_deuda")
    private String tipoDeuda;

    @Column(name = "institucion")
    private String institucion;

    @Column(name = "monto")
    private Double monto;

}
