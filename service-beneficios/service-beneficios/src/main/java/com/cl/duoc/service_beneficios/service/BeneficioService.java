package com.cl.duoc.service_beneficios.service;

import com.cl.duoc.service_beneficios.model.Beneficio;
import java.util.List;
import java.util.Optional;

public interface BeneficioService {

    List<Beneficio> listar();

    Optional<Beneficio> buscarPorId(Long id);

    Beneficio guardar(Beneficio beneficio);

    Beneficio actualizar(Long id, Beneficio beneficio);

    void eliminar(Long id);

}
