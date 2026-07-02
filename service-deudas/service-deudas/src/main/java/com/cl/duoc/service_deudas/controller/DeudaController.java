package com.cl.duoc.service_deudas.controller;

import com.cl.duoc.service_deudas.model.Deuda;
import com.cl.duoc.service_deudas.service.DeudaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/deudas")
@Tag(name = "Deudas", description = "Gestión de deudas registradas de la persona fallecida")
public class DeudaController {

    private final DeudaService deudaService;

    public DeudaController(DeudaService deudaService) {
        this.deudaService = deudaService;
    }

    @GetMapping
    @Operation(summary = "Listar deudas", description = "Obtiene la lista de todas las deudas registradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de deudas obtenida correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Deuda.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<Deuda>> listar() {
        return ResponseEntity.ok(deudaService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar deuda por ID", description = "Busca una deuda por su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deuda encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Deuda.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró una deuda con el identificador indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Deuda> obtenerPorId(
            @Parameter(name = "id", description = "Identificador de la deuda", required = true, example = "1")
            @PathVariable Long id) {
        return deudaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/rut/{rut}")
    @Operation(summary = "Buscar deuda por RUT", description = "Busca una deuda asociada a una persona fallecida mediante su RUT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deuda encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Deuda.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró una deuda para el RUT indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Deuda> obtenerPorRut(
            @Parameter(name = "rut", description = "RUT de la persona fallecida asociada a la deuda", required = true, example = "12345678-9")
            @PathVariable String rut) {
        return deudaService.buscarPorRut(rut)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear deuda", description = "Registra una nueva deuda asociada a la persona fallecida.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Deuda creada correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Deuda.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Deuda> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la deuda a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Deuda.class),
                            examples = @ExampleObject(value = "{\"rutFallecido\":\"12345678-9\",\"tipoDeuda\":\"Credito hipotecario\",\"institucion\":\"Banco Estado\",\"monto\":2500000.0}")))
            @RequestBody Deuda deuda) {
        Deuda saved = deudaService.guardar(deuda);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar deuda", description = "Actualiza una deuda existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deuda actualizada correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Deuda.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró una deuda con el identificador indicado"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Deuda> actualizar(
            @Parameter(name = "id", description = "Identificador de la deuda a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados de la deuda",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Deuda.class),
                            examples = @ExampleObject(value = "{\"rutFallecido\":\"12345678-9\",\"tipoDeuda\":\"Credito automotriz\",\"institucion\":\"Banco Santander\",\"monto\":1800000.0}")))
            @RequestBody Deuda deuda) {
        return deudaService.actualizar(id, deuda)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar deuda", description = "Elimina una deuda existente según su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deuda eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró una deuda con el identificador indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(name = "id", description = "Identificador de la deuda a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        return deudaService.buscarPorId(id)
                .map(d -> {
                    deudaService.eliminar(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
