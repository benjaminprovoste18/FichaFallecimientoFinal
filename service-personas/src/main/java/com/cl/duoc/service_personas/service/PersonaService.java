package com.cl.duoc.service_personas.service;

import com.cl.duoc.service_personas.model.Persona;
import com.cl.duoc.service_personas.repository.PersonaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {

    private final PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    public List<Persona> listar() {
        return personaRepository.findAll();
    }

    public Persona guardar(Persona persona) {
        return personaRepository.save(persona);
    }

    public Optional<Persona> buscarPorRut(String rut) {
        return personaRepository.findByRut(rut);
    }

    public void eliminar(Long id) {
        personaRepository.deleteById(id);
    }
}
