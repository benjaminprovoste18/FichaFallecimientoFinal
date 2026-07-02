package com.cl.duoc.service_finanzas.controller;

import com.cl.duoc.service_finanzas.model.Finanza;
import com.cl.duoc.service_finanzas.service.FinanzaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/finanzas")
@Tag(name = "Finanzas", description = "Operaciones para consultar y administrar información financiera de personas fallecidas")
public class FinanzaController {

    private final FinanzaService service;

    public FinanzaController(FinanzaService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar registros financieros", description = "Obtiene la lista de todos los registros financieros registrados en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registros financieros encontrados", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Finanza.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<Finanza>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    @Operation(summary = "Crear registro financiero", description = "Registra un nuevo dato financiero para una persona fallecida.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro financiero creado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Finanza.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Finanza> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del registro financiero a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Finanza.class),
                            examples = @ExampleObject(value = "{\"rutPersona\":\"12345678-9\",\"ingresosMensuales\":1200000.0,\"deudas\":500000.0,\"situacionLaboral\":\"Cesante\",\"observaciones\":\"Sin obligaciones pendientes\"}")))
            @RequestBody Finanza finanza) {
        Finanza guardada = service.guardar(finanza);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    @GetMapping("/{rutPersona}")
    @Operation(summary = "Buscar registro financiero por RUT", description = "Busca un registro financiero utilizando el RUT de la persona asociada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro financiero encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Finanza.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró un registro financiero con el RUT indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Finanza> buscarPorRut(
            @Parameter(name = "rutPersona", description = "RUT de la persona asociada al registro financiero", required = true, example = "12345678-9")
            @PathVariable String rutPersona) {
        return service.buscarPorRutPersona(rutPersona)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar registro financiero", description = "Actualiza un registro financiero existente según su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro financiero actualizado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Finanza.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró un registro financiero con el identificador indicado"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Finanza> actualizar(
            @Parameter(name = "id", description = "Identificador del registro financiero a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del registro financiero a actualizar",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Finanza.class),
                            examples = @ExampleObject(value = "{\"rutPersona\":\"12345678-9\",\"ingresosMensuales\":1500000.0,\"deudas\":400000.0,\"situacionLaboral\":\"Empleado\",\"observaciones\":\"Actualizado\"}")))
            @RequestBody Finanza finanza) {
        return ResponseEntity.ok(service.actualizar(id, finanza));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar registro financiero", description = "Elimina un registro financiero existente según su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Registro financiero eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró un registro financiero con el identificador indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(name = "id", description = "Identificador del registro financiero a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (java.util.NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
