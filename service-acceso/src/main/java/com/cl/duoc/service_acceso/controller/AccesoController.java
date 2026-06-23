package com.cl.duoc.service_acceso.controller;

import com.cl.duoc.service_acceso.model.Acceso;
import com.cl.duoc.service_acceso.service.AccesoService;
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
@RequestMapping("/acceso")
public class AccesoController {

    private final AccesoService accesoService;

    public AccesoController(AccesoService accesoService) {
        this.accesoService = accesoService;
    }

    @GetMapping
    public ResponseEntity<List<Acceso>> listar() {
        return ResponseEntity.ok(accesoService.listar());
    }

    @GetMapping("/{rutPersona}")
    public ResponseEntity<Acceso> buscarPorRutPersona(@PathVariable String rutPersona) {
        return accesoService.buscarPorRutPersona(rutPersona)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Acceso> guardar(@RequestBody Acceso acceso) {
        Acceso creado = accesoService.guardar(acceso);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        accesoService.eliminar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
