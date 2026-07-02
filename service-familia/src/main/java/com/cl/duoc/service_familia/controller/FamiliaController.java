package com.cl.duoc.service_familia.controller;

import com.cl.duoc.service_familia.model.Familia;
import com.cl.duoc.service_familia.service.FamiliaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
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

@RestController
@RequestMapping("/familia")
@Tag(name = "Familia", description = "Gestión del grupo familiar asociado a la persona fallecida")
public class FamiliaController {

    private final FamiliaService familiaService;

    public FamiliaController(FamiliaService familiaService) {
        this.familiaService = familiaService;
    }

    @GetMapping
    @Operation(summary = "Listar registros familiares", description = "Obtiene la lista de todos los grupos familiares registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Familia.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<Familia>> listar() {
        return ResponseEntity.ok(familiaService.listar());
    }

    @GetMapping("/{rutPersona}")
    @Operation(summary = "Buscar grupo familiar por RUT", description = "Busca el grupo familiar asociado a una persona mediante su RUT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grupo familiar encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Familia.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró un registro para el RUT indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Familia> buscarPorRutPersona(
            @Parameter(name = "rutPersona", description = "RUT de la persona asociada al grupo familiar", required = true, example = "12345678-9")
            @PathVariable String rutPersona) {
        return familiaService.buscarPorRutPersona(rutPersona)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    @Operation(summary = "Crear registro familiar", description = "Registra un nuevo grupo familiar para una persona fallecida.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Grupo familiar creado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Familia.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Familia> guardar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del grupo familiar a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Familia.class),
                            examples = @ExampleObject(value = "{\"rutPersona\":\"12345678-9\",\"conyuge\":\"María Pérez\",\"cantidadHijos\":2,\"direccionGrupoFamiliar\":\"Av. Siempre Viva 123\",\"observaciones\":\"Grupo estable\"}")))
            @RequestBody Familia familia) {
        Familia guardada = familiaService.guardar(familia);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar registro familiar", description = "Actualiza un registro familiar existente según su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro familiar actualizado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Familia.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró un registro familiar con el identificador indicado"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Familia> actualizar(
            @Parameter(name = "id", description = "Identificador del registro familiar a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del registro familiar a actualizar",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Familia.class),
                            examples = @ExampleObject(value = "{\"rutPersona\":\"12345678-9\",\"conyuge\":\"María Pérez\",\"cantidadHijos\":2,\"direccionGrupoFamiliar\":\"Av. Siempre Viva 123\",\"observaciones\":\"Actualizado\"}")))
            @RequestBody Familia familia) {
        return ResponseEntity.ok(familiaService.actualizar(id, familia));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar registro familiar", description = "Elimina un registro familiar existente según su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Registro eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró un registro familiar con el identificador indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(name = "id", description = "Identificador del registro familiar a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        familiaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
