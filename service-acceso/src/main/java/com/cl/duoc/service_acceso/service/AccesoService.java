package com.cl.duoc.service_acceso.service;

import com.cl.duoc.service_acceso.model.Acceso;
import com.cl.duoc.service_acceso.repository.AccesoRepository;
import java.util.List;
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

    public Optional<Acceso> buscarPorRutPersona(String rutPersona) {
        return accesoRepository.findByRutPersona(rutPersona);
    }

    public void eliminar(Long id) {
        accesoRepository.deleteById(id);
    }
}
