package com.cl.duoc.service_beneficios.repository;

import com.cl.duoc.service_beneficios.model.Beneficio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BeneficioRepository extends JpaRepository<Beneficio, Long> {

    Optional<Beneficio> findByRutFallecido(String rutFallecido);
}
