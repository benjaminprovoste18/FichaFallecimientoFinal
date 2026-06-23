package com.cl.duoc.service_familia.repository;

import com.cl.duoc.service_familia.model.Familia;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamiliaRepository extends JpaRepository<Familia, Long> {

    Optional<Familia> findByRutPersona(String rutPersona);
}
