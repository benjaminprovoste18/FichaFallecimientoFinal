package com.cl.duoc.service_finanzas.service;

import com.cl.duoc.service_finanzas.model.Finanza;
import com.cl.duoc.service_finanzas.repository.FinanzaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FinanzaService {

    private final FinanzaRepository repository;

    public FinanzaService(FinanzaRepository repository) {
        this.repository = repository;
    }

    public List<Finanza> listar() {
        return repository.findAll();
    }

    public Finanza guardar(Finanza finanza) {
        return repository.save(finanza);
    }

    public Optional<Finanza> buscarPorRutPersona(String rutPersona) {
        return repository.findByRutPersona(rutPersona);
    }

    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new java.util.NoSuchElementException("Finanza no encontrada: " + id);
        }
        repository.deleteById(id);
    }
}
