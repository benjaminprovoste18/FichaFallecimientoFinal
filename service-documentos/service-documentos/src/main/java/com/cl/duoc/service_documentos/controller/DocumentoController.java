package com.cl.duoc.service_documentos.controller;

import com.cl.duoc.service_documentos.model.Documento;
import com.cl.duoc.service_documentos.service.DocumentoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/documentos")
public class DocumentoController {

    private final DocumentoService documentoService;

    public DocumentoController(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    @GetMapping
    public ResponseEntity<List<Documento>> getAllDocumentos() {
        List<Documento> documentos = documentoService.getAllDocumentos();
        return ResponseEntity.ok(documentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Documento> getDocumentoById(@PathVariable Long id) {
        Documento documento = documentoService.getDocumentoById(id);
        return ResponseEntity.ok(documento);
    }

    @PostMapping
    public ResponseEntity<Documento> createDocumento(@Valid @RequestBody Documento documento) {
        Documento creado = documentoService.createDocumento(documento);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getId())
                .toUri();
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Documento> updateDocumento(@PathVariable Long id, @Valid @RequestBody Documento documento) {
        Documento actualizado = documentoService.updateDocumento(id, documento);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumento(@PathVariable Long id) {
        documentoService.deleteDocumento(id);
        return ResponseEntity.noContent().build();
    }
}
