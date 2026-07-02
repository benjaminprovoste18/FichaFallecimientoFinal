package com.cl.duoc.service_documentos.controller;

import com.cl.duoc.service_documentos.model.Documento;
import com.cl.duoc.service_documentos.service.DocumentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Documentos", description = "Gestión de documentos asociados a la ficha social")
public class DocumentoController {

    private final DocumentoService documentoService;

    public DocumentoController(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    @GetMapping
    @Operation(summary = "Listar documentos", description = "Obtiene la lista de todos los documentos registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de documentos obtenida correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Documento.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<Documento>> getAllDocumentos() {
        List<Documento> documentos = documentoService.getAllDocumentos();
        return ResponseEntity.ok(documentos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar documento por ID", description = "Busca un documento por su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documento encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Documento.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró un documento con el identificador indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Documento> getDocumentoById(
            @Parameter(name = "id", description = "Identificador del documento", required = true, example = "1")
            @PathVariable Long id) {
        Documento documento = documentoService.getDocumentoById(id);
        return ResponseEntity.ok(documento);
    }

    @GetMapping("/rut/{rut}")
    @Operation(summary = "Buscar documento por RUT", description = "Busca un documento asociado a una persona fallecida mediante su RUT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documento encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Documento.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró un documento para el RUT indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Documento> getDocumentoByRut(
            @Parameter(name = "rut", description = "RUT de la persona fallecida asociada al documento", required = true, example = "12345678-9")
            @PathVariable String rut) {
        return documentoService.findByRut(rut)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear documento", description = "Registra un nuevo documento asociado a la ficha social.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Documento creado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Documento.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Documento> createDocumento(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del documento a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Documento.class),
                            examples = @ExampleObject(value = "{\"rutFallecido\":\"12345678-9\",\"tipoDocumento\":\"Certificado de defunción\",\"nombreDocumento\":\"certificado.pdf\",\"fechaEmision\":\"2024-10-15\"}")))
            @Valid @RequestBody Documento documento) {
        Documento creado = documentoService.createDocumento(documento);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getId())
                .toUri();
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar documento", description = "Actualiza un documento existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documento actualizado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Documento.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró un documento con el identificador indicado"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Documento> updateDocumento(
            @Parameter(name = "id", description = "Identificador del documento a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados del documento",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Documento.class),
                            examples = @ExampleObject(value = "{\"rutFallecido\":\"12345678-9\",\"tipoDocumento\":\"Certificado de defunción\",\"nombreDocumento\":\"certificado-actualizado.pdf\",\"fechaEmision\":\"2024-10-16\"}")))
            @Valid @RequestBody Documento documento) {
                Documento actualizado = documentoService.actualizar(id, documento);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar documento", description = "Elimina un documento existente según su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Documento eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró un documento con el identificador indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> deleteDocumento(
            @Parameter(name = "id", description = "Identificador del documento a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        documentoService.deleteDocumento(id);
        return ResponseEntity.noContent().build();
    }
}
