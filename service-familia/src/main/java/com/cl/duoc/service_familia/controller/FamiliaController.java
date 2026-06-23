package com.cl.duoc.service_familia.controller;

import com.cl.duoc.service_familia.model.Familia;
import com.cl.duoc.service_familia.service.FamiliaService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/familia")
public class FamiliaController {

    private final FamiliaService familiaService;

    public FamiliaController(FamiliaService familiaService) {
        this.familiaService = familiaService;
    }

    @GetMapping
    public ResponseEntity<List<Familia>> listar() {
        return ResponseEntity.ok(familiaService.listar());
    }

    @GetMapping("/{rutPersona}")
    public ResponseEntity<Familia> buscarPorRutPersona(@PathVariable String rutPersona) {
        return familiaService.buscarPorRutPersona(rutPersona)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Familia> guardar(@RequestBody Familia familia) {
        Familia guardada = familiaService.guardar(familia);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        familiaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
