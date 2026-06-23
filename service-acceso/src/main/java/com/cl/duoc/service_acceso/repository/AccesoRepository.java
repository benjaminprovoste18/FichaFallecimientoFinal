package com.cl.duoc.service_acceso.repository;

import com.cl.duoc.service_acceso.model.Acceso;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccesoRepository extends JpaRepository<Acceso, Long> {

    Optional<Acceso> findByRutPersona(String rutPersona);
}
