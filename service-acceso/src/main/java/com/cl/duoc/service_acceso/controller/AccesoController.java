package com.cl.duoc.service_acceso.controller;

import com.cl.duoc.service_acceso.model.Acceso;
import com.cl.duoc.service_acceso.service.AccesoService;
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
@RequestMapping("/acceso")
@Tag(name = "Acceso", description = "Gestión del acceso a las fichas sociales")
public class AccesoController {

    private final AccesoService accesoService;

    public AccesoController(AccesoService accesoService) {
        this.accesoService = accesoService;
    }

    @GetMapping
    @Operation(summary = "Listar registros de acceso", description = "Obtiene la lista de todos los registros de acceso registrados en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registros encontrados", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Acceso.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<Acceso>> listar() {
        return ResponseEntity.ok(accesoService.listar());
    }

    @GetMapping("/{rutPersona}")
    @Operation(summary = "Buscar acceso por RUT", description = "Busca un registro de acceso mediante el RUT de la persona asociada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro de acceso encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Acceso.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró un registro de acceso para el RUT indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Acceso> buscarPorRutPersona(
            @Parameter(name = "rutPersona", description = "RUT de la persona asociada al registro de acceso", required = true, example = "12345678-9")
            @PathVariable String rutPersona) {
        return accesoService.buscarPorRutPersona(rutPersona)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    @Operation(summary = "Crear registro de acceso", description = "Registra un nuevo estado de acceso para una persona.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro de acceso creado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Acceso.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Acceso> guardar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del acceso a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Acceso.class),
                            examples = @ExampleObject(value = "{\"rutPersona\":\"12345678-9\",\"aguaPotable\":true,\"electricidad\":true,\"alcantarillado\":true,\"tipoVivienda\":\"Casa\",\"observaciones\":\"Acceso completo\"}")))
            @RequestBody Acceso acceso) {
        Acceso creado = accesoService.guardar(acceso);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar registro de acceso", description = "Actualiza un registro de acceso existente según su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro de acceso actualizado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Acceso.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró un registro de acceso con el identificador indicado"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Acceso> actualizar(
            @Parameter(name = "id", description = "Identificador del registro de acceso a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del acceso a actualizar",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Acceso.class),
                            examples = @ExampleObject(value = "{\"rutPersona\":\"12345678-9\",\"aguaPotable\":true,\"electricidad\":true,\"alcantarillado\":true,\"tipoVivienda\":\"Casa\",\"observaciones\":\"Actualizado\"}")))
            @RequestBody Acceso acceso) {
        return ResponseEntity.ok(accesoService.actualizar(id, acceso));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar registro de acceso", description = "Elimina un registro de acceso existente según su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Registro eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró un registro de acceso con el identificador indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(name = "id", description = "Identificador del registro de acceso a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        accesoService.eliminar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
