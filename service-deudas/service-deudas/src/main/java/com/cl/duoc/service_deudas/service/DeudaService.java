package com.cl.duoc.service_deudas.service;

import com.cl.duoc.service_deudas.model.Deuda;
import com.cl.duoc.service_deudas.repository.DeudaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DeudaService {

    private final DeudaRepository deudaRepository;

    public DeudaService(DeudaRepository deudaRepository) {
        this.deudaRepository = deudaRepository;
    }

    public List<Deuda> listar() {
        return deudaRepository.findAll();
    }

    public Optional<Deuda> buscarPorRut(String rut) {
        return deudaRepository.findByRutFallecido(rut);
    }

    public Optional<Deuda> buscarPorId(Long id) {
        return deudaRepository.findById(id);
    }

    public Deuda guardar(Deuda deuda) {
        return deudaRepository.save(deuda);
    }

    @Transactional
    public Optional<Deuda> actualizar(Long id, Deuda deuda) {
        return deudaRepository.findById(id).map(existing -> {
            existing.setRutFallecido(deuda.getRutFallecido());
            existing.setTipoDeuda(deuda.getTipoDeuda());
            existing.setInstitucion(deuda.getInstitucion());
            existing.setMonto(deuda.getMonto());
            return deudaRepository.save(existing);
        });
    }

    public void eliminar(Long id) {
        deudaRepository.deleteById(id);
    }

}
