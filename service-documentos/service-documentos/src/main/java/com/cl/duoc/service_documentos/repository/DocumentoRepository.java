package com.cl.duoc.service_documentos.repository;

import com.cl.duoc.service_documentos.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    Optional<Documento> findByRutFallecido(String rutFallecido);
}
