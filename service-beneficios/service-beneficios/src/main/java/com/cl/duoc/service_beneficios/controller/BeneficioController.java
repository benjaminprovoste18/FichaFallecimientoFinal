package com.cl.duoc.service_beneficios.controller;

import com.cl.duoc.service_beneficios.model.Beneficio;
import com.cl.duoc.service_beneficios.service.BeneficioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/beneficios")
public class BeneficioController {

    private final BeneficioService service;

    public BeneficioController(BeneficioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Beneficio>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Beneficio> obtenerPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Beneficio> crear(@RequestBody Beneficio beneficio) {
        Beneficio creado = service.guardar(beneficio);
        return ResponseEntity.created(URI.create("/beneficios/" + creado.getId())).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Beneficio> actualizar(@PathVariable Long id, @RequestBody Beneficio beneficio) {
        try {
            Beneficio actualizado = service.actualizar(id, beneficio);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

}
