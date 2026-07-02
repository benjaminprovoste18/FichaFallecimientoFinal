package com.cl.duoc.fichasocial.controller;

import com.cl.duoc.fichasocial.dto.FichaSocialDTO;
import com.cl.duoc.fichasocial.service.FichaSocialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fichasocial")
@RequiredArgsConstructor
@Tag(name = "Ficha Social", description = "Consulta consolidada de la ficha social completa")
public class FichaSocialController {

    private final FichaSocialService fichaSocialService;

    @GetMapping("/{rut}")
    @Operation(summary = "Obtener ficha social completa por RUT", description = "Recupera la información consolidada de una persona fallecida a partir de su RUT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ficha social encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FichaSocialDTO.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró información para el RUT indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<FichaSocialDTO> obtenerFicha(
            @Parameter(name = "rut", description = "RUT de la persona fallecida", required = true, example = "12345678-9")
            @PathVariable String rut) {
        FichaSocialDTO dto = fichaSocialService.obtenerFichaCompleta(rut);
        return ResponseEntity.ok(dto);
    }
}
