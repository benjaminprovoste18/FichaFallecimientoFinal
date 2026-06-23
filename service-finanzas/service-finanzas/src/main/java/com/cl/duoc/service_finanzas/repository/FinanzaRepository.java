package com.cl.duoc.service_finanzas.repository;

import com.cl.duoc.service_finanzas.model.Finanza;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FinanzaRepository extends JpaRepository<Finanza, Long> {
    Optional<Finanza> findByRutPersona(String rutPersona);
}
