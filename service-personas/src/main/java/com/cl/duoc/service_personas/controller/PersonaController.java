package com.cl.duoc.service_personas.controller;

import com.cl.duoc.service_personas.model.Persona;
import com.cl.duoc.service_personas.service.PersonaService;
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
@RequestMapping("/personas")
@Tag(name = "Personas", description = "Gestión de personas del sistema de ficha social")
public class PersonaController {

    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping
    @Operation(summary = "Listar personas", description = "Obtiene la lista de todas las personas registradas en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de personas obtenida correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Persona.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<Persona>> listar() {
        return ResponseEntity.ok(personaService.listar());
    }

    @GetMapping("/{rut}")
    @Operation(summary = "Buscar persona por RUT", description = "Busca una persona registrada utilizando su RUT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Persona.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró una persona con el RUT indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Persona> buscarPorRut(
            @Parameter(name = "rut", description = "RUT de la persona a buscar", required = true, example = "12345678-9")
            @PathVariable String rut) {
        return personaService.buscarPorRut(rut)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    @Operation(summary = "Crear persona", description = "Registra una nueva persona en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Persona creada correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Persona.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Persona> guardar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la persona a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Persona.class),
                            examples = @ExampleObject(value = "{\"rut\":\"12345678-9\",\"nombres\":\"Juan\",\"apellidos\":\"Pérez\",\"edad\":45,\"direccion\":\"Av. Siempre Viva 123\",\"fechaNacimiento\":\"1980-01-01\"}")))
            @RequestBody Persona persona) {
        Persona guardada = personaService.guardar(persona);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar persona", description = "Actualiza los datos de una persona existente según su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona actualizada correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Persona.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró una persona con el identificador indicado"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Persona> actualizar(
            @Parameter(name = "id", description = "Identificador de la persona a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados de la persona",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Persona.class),
                            examples = @ExampleObject(value = "{\"rut\":\"12345678-9\",\"nombres\":\"Juan\",\"apellidos\":\"Pérez\",\"edad\":46,\"direccion\":\"Av. Siempre Viva 123\",\"fechaNacimiento\":\"1980-01-01\"}")))
            @RequestBody Persona persona) {
        return ResponseEntity.ok(personaService.actualizar(id, persona));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar persona", description = "Elimina una persona existente según su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Persona eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró una persona con el identificador indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(name = "id", description = "Identificador de la persona a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        personaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
