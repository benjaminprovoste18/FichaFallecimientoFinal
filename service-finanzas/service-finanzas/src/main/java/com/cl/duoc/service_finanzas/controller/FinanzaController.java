package com.cl.duoc.service_finanzas.controller;

import com.cl.duoc.service_finanzas.model.Finanza;
import com.cl.duoc.service_finanzas.service.FinanzaService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finanzas")
public class FinanzaController {

    private final FinanzaService service;

    public FinanzaController(FinanzaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Finanza>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<Finanza> crear(@RequestBody Finanza finanza) {
        Finanza guardada = service.guardar(finanza);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    @GetMapping("/{rutPersona}")
    public ResponseEntity<Finanza> buscarPorRut(@PathVariable String rutPersona) {
        return service.buscarPorRutPersona(rutPersona)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (java.util.NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
