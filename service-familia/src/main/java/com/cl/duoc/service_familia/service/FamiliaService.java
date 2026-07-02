package com.cl.duoc.service_familia.service;

import com.cl.duoc.service_familia.model.Familia;
import com.cl.duoc.service_familia.repository.FamiliaRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class FamiliaService {

    private final FamiliaRepository familiaRepository;

    public FamiliaService(FamiliaRepository familiaRepository) {
        this.familiaRepository = familiaRepository;
    }

    public List<Familia> listar() {
        return familiaRepository.findAll();
    }

    public Familia guardar(Familia familia) {
        return familiaRepository.save(familia);
    }

    public Familia actualizar(Long id, Familia familia) {
        Familia existente = familiaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Familia no encontrada: " + id));

        existente.setRutPersona(familia.getRutPersona());
        existente.setConyuge(familia.getConyuge());
        existente.setCantidadHijos(familia.getCantidadHijos());
        existente.setDireccionGrupoFamiliar(familia.getDireccionGrupoFamiliar());
        existente.setObservaciones(familia.getObservaciones());

        return familiaRepository.save(existente);
    }

    public Optional<Familia> buscarPorRutPersona(String rutPersona) {
        return familiaRepository.findByRutPersona(rutPersona);
    }

    public void eliminar(Long id) {
        if (familiaRepository.existsById(id)) {
            familiaRepository.deleteById(id);
        }
    }
}
