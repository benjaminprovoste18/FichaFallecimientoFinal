package com.cl.duoc.fichasocial.controller;

import com.cl.duoc.fichasocial.dto.FichaSocialDTO;
import com.cl.duoc.fichasocial.service.FichaSocialService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fichasocial")
@RequiredArgsConstructor
public class FichaSocialController {

    private final FichaSocialService fichaSocialService;

    @GetMapping("/{rut}")
    @Operation(summary = "Obtener ficha social completa por RUT")
    public ResponseEntity<FichaSocialDTO> obtenerFicha(@PathVariable String rut) {
        FichaSocialDTO dto = fichaSocialService.obtenerFichaCompleta(rut);
        return ResponseEntity.ok(dto);
    }
}
