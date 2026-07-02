package com.cl.duoc.service_acceso.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank
    private String rutPersona;

    @NotNull
    private Boolean aguaPotable;

    @NotNull
    private Boolean electricidad;

    @NotNull
    private Boolean alcantarillado;

    @NotBlank
    private String tipoVivienda;

    @Size(max = 255)
    private String observaciones;
}
