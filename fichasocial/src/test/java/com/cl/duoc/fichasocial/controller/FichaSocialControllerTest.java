package com.cl.duoc.fichasocial.controller;

import com.cl.duoc.fichasocial.dto.FichaSocialDTO;
import com.cl.duoc.fichasocial.dto.PersonaDTO;
import com.cl.duoc.fichasocial.service.FichaSocialService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FichaSocialController.class)
@DisplayName("FichaSocialController - Pruebas Unitarias")
class FichaSocialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FichaSocialService fichaSocialService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void obtenerFicha_returns200() throws Exception {
        String rut = "12345678-9";
        FichaSocialDTO dto = new FichaSocialDTO();
        dto.setPersona(new PersonaDTO(rut, "Juan", "Pérez", null, null, null));

        when(fichaSocialService.obtenerFichaCompleta(anyString())).thenReturn(dto);

        mockMvc.perform(get("/fichasocial/{rut}", rut).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.persona.rut").value(rut))
                .andExpect(jsonPath("$.persona.nombres").value("Juan"));

        verify(fichaSocialService, times(1)).obtenerFichaCompleta(rut);
    }
}
