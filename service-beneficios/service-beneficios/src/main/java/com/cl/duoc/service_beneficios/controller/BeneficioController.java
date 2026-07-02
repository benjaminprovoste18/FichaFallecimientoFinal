package com.cl.duoc.service_beneficios.controller;

import com.cl.duoc.service_beneficios.model.Beneficio;
import com.cl.duoc.service_beneficios.service.BeneficioService;
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

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/beneficios")
@Tag(name = "Beneficios", description = "Gestión de beneficios sociales asociados a la persona fallecida")
public class BeneficioController {

    private final BeneficioService service;

    public BeneficioController(BeneficioService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar beneficios", description = "Obtiene la lista de todos los beneficios registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de beneficios obtenida correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Beneficio.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<Beneficio>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar beneficio por ID", description = "Busca un beneficio por su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Beneficio encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Beneficio.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró un beneficio con el identificador indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Beneficio> obtenerPorId(
            @Parameter(name = "id", description = "Identificador del beneficio", required = true, example = "1")
            @PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/rut/{rut}")
    @Operation(summary = "Buscar beneficio por RUT", description = "Busca un beneficio asociado a una persona fallecida mediante su RUT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Beneficio encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Beneficio.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró un beneficio para el RUT indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Beneficio> obtenerPorRut(
            @Parameter(name = "rut", description = "RUT de la persona fallecida asociada al beneficio", required = true, example = "12345678-9")
            @PathVariable String rut) {
        return service.buscarPorRut(rut)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear beneficio", description = "Registra un nuevo beneficio social asociado a la persona fallecida.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Beneficio creado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Beneficio.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Beneficio> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del beneficio a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Beneficio.class),
                            examples = @ExampleObject(value = "{\"rutFallecido\":\"12345678-9\",\"nombreBeneficio\":\"Subsidio funeral\",\"institucion\":\"MINSAL\",\"montoBeneficio\":500000.0}")))
            @RequestBody Beneficio beneficio) {
        Beneficio creado = service.guardar(beneficio);
        return ResponseEntity.created(URI.create("/beneficios/" + creado.getId())).body(creado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar beneficio", description = "Actualiza un beneficio existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Beneficio actualizado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Beneficio.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró un beneficio con el identificador indicado"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Beneficio> actualizar(
            @Parameter(name = "id", description = "Identificador del beneficio a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados del beneficio",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Beneficio.class),
                            examples = @ExampleObject(value = "{\"rutFallecido\":\"12345678-9\",\"nombreBeneficio\":\"Subsidio funerario\",\"institucion\":\"SENAME\",\"montoBeneficio\":750000.0}")))
            @RequestBody Beneficio beneficio) {
        try {
            Beneficio actualizado = service.actualizar(id, beneficio);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar beneficio", description = "Elimina un beneficio existente según su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Beneficio eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró un beneficio con el identificador indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(name = "id", description = "Identificador del beneficio a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
