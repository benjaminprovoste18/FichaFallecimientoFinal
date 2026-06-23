package com.cl.duoc.service_beneficios.service.impl;

import com.cl.duoc.service_beneficios.model.Beneficio;
import com.cl.duoc.service_beneficios.repository.BeneficioRepository;
import com.cl.duoc.service_beneficios.service.BeneficioService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BeneficioServiceImpl implements BeneficioService {

    private final BeneficioRepository repository;

    public BeneficioServiceImpl(BeneficioRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Beneficio> listar() {
        return repository.findAll();
    }

    @Override
    public Optional<Beneficio> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Beneficio> buscarPorRut(String rut) {
        return repository.findByRutFallecido(rut);
    }

    @Override
    public Beneficio guardar(Beneficio beneficio) {
        return repository.save(beneficio);
    }

    @Override
    public Beneficio actualizar(Long id, Beneficio beneficio) {
        return repository.findById(id).map(existing -> {
            existing.setRutFallecido(beneficio.getRutFallecido());
            existing.setNombreBeneficio(beneficio.getNombreBeneficio());
            existing.setInstitucion(beneficio.getInstitucion());
            existing.setMontoBeneficio(beneficio.getMontoBeneficio());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Beneficio no encontrado con id " + id));
    }

    @Override
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Beneficio no encontrado con id " + id);
        }
        repository.deleteById(id);
    }
}
