package com.cl.duoc.service_finanzas.service;

import com.cl.duoc.service_finanzas.model.Finanza;
import com.cl.duoc.service_finanzas.repository.FinanzaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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

    public Finanza actualizar(Long id, Finanza finanza) {
        Finanza existente = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Finanza no encontrada: " + id));

        existente.setRutPersona(finanza.getRutPersona());
        existente.setIngresosMensuales(finanza.getIngresosMensuales());
        existente.setDeudas(finanza.getDeudas());
        existente.setSituacionLaboral(finanza.getSituacionLaboral());
        existente.setObservaciones(finanza.getObservaciones());

        return repository.save(existente);
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
