package com.cl.duoc.service_acceso.service;

import com.cl.duoc.service_acceso.model.Acceso;
import com.cl.duoc.service_acceso.repository.AccesoRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AccesoService {

    private final AccesoRepository accesoRepository;

    public AccesoService(AccesoRepository accesoRepository) {
        this.accesoRepository = accesoRepository;
    }

    public List<Acceso> listar() {
        return accesoRepository.findAll();
    }

    public Acceso guardar(Acceso acceso) {
        return accesoRepository.save(acceso);
    }

    public Acceso actualizar(Long id, Acceso acceso) {
        Acceso existente = accesoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Acceso no encontrado: " + id));

        existente.setRutPersona(acceso.getRutPersona());
        existente.setAguaPotable(acceso.getAguaPotable());
        existente.setElectricidad(acceso.getElectricidad());
        existente.setAlcantarillado(acceso.getAlcantarillado());
        existente.setTipoVivienda(acceso.getTipoVivienda());
        existente.setObservaciones(acceso.getObservaciones());

        return accesoRepository.save(existente);
    }

    public Optional<Acceso> buscarPorRutPersona(String rutPersona) {
        return accesoRepository.findByRutPersona(rutPersona);
    }

    public void eliminar(Long id) {
        accesoRepository.deleteById(id);
    }
}
