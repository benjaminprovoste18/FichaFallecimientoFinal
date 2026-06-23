package com.cl.duoc.service_deudas.repository;

import com.cl.duoc.service_deudas.model.Deuda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeudaRepository extends JpaRepository<Deuda, Long> {

    Optional<Deuda> findByRutFallecido(String rutFallecido);
}
