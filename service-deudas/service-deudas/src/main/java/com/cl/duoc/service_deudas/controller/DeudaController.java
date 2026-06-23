package com.cl.duoc.service_deudas.controller;

import com.cl.duoc.service_deudas.model.Deuda;
import com.cl.duoc.service_deudas.service.DeudaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/deudas")
public class DeudaController {

    private final DeudaService deudaService;

    public DeudaController(DeudaService deudaService) {
        this.deudaService = deudaService;
    }

    @GetMapping
    public ResponseEntity<List<Deuda>> listar() {
        return ResponseEntity.ok(deudaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Deuda> obtenerPorId(@PathVariable Long id) {
        return deudaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Deuda> crear(@RequestBody Deuda deuda) {
        Deuda saved = deudaService.guardar(deuda);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Deuda> actualizar(@PathVariable Long id, @RequestBody Deuda deuda) {
        return deudaService.actualizar(id, deuda)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return deudaService.buscarPorId(id)
                .map(d -> {
                    deudaService.eliminar(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
