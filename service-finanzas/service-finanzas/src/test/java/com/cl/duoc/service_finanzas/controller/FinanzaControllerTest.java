package com.cl.duoc.service_finanzas.controller;

import com.cl.duoc.service_finanzas.model.Finanza;
import com.cl.duoc.service_finanzas.service.FinanzaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FinanzaController.class)
@DisplayName("FinanzaController - Pruebas Unitarias")
class FinanzaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FinanzaService finanzaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("listar() retorna 200 con una lista de dos registros")
    void testListarRetorna200ConDosRegistros() throws Exception {
        List<Finanza> finanzas = Arrays.asList(
                Finanza.builder()
                        .id(1L)
                        .rutPersona("12345678-9")
                        .ingresosMensuales(1200.0)
                        .deudas(300.0)
                        .situacionLaboral("Empleado")
                        .observaciones("Sin observaciones")
                        .build(),
                Finanza.builder()
                        .id(2L)
                        .rutPersona("98765432-1")
                        .ingresosMensuales(2200.0)
                        .deudas(100.0)
                        .situacionLaboral("Independiente")
                        .observaciones("Historial limpio")
                        .build()
        );

        when(finanzaService.listar()).thenReturn(finanzas);

        mockMvc.perform(get("/finanzas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].rutPersona").value("12345678-9"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].rutPersona").value("98765432-1"));

        verify(finanzaService, times(1)).listar();
    }

    @Test
    @DisplayName("buscarPorRut() retorna 200 cuando existe")
    void testBuscarPorRutRetorna200CuandoExiste() throws Exception {
        String rutPersona = "12345678-9";
        Finanza finanza = Finanza.builder()
                .id(1L)
                .rutPersona(rutPersona)
                .ingresosMensuales(1200.0)
                .deudas(300.0)
                .situacionLaboral("Empleado")
                .observaciones("Sin observaciones")
                .build();

        when(finanzaService.buscarPorRutPersona(anyString())).thenReturn(Optional.of(finanza));

        mockMvc.perform(get("/finanzas/{rutPersona}", rutPersona)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rutPersona").value(rutPersona))
                .andExpect(jsonPath("$.ingresosMensuales").value(1200.0))
                .andExpect(jsonPath("$.deudas").value(300.0));

        verify(finanzaService, times(1)).buscarPorRutPersona(rutPersona);
    }

    @Test
    @DisplayName("buscarPorRut() retorna 404 cuando no existe")
    void testBuscarPorRutRetorna404CuandoNoExiste() throws Exception {
        String rutPersona = "99999999-9";
        when(finanzaService.buscarPorRutPersona(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get("/finanzas/{rutPersona}", rutPersona)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(finanzaService, times(1)).buscarPorRutPersona(rutPersona);
    }

    @Test
    @DisplayName("crear() retorna 201")
    void testCrearRetorna201() throws Exception {
        Finanza finanzaRequest = Finanza.builder()
                .rutPersona("55555555-5")
                .ingresosMensuales(1500.0)
                .deudas(200.0)
                .situacionLaboral("Desempleado")
                .observaciones("Busca empleo")
                .build();

        Finanza finanzaGuardada = Finanza.builder()
                .id(3L)
                .rutPersona("55555555-5")
                .ingresosMensuales(1500.0)
                .deudas(200.0)
                .situacionLaboral("Desempleado")
                .observaciones("Busca empleo")
                .build();

        when(finanzaService.guardar(any(Finanza.class))).thenReturn(finanzaGuardada);

        mockMvc.perform(post("/finanzas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(finanzaRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.rutPersona").value("55555555-5"))
                .andExpect(jsonPath("$.situacionLaboral").value("Desempleado"));

        verify(finanzaService, times(1)).guardar(any(Finanza.class));
    }

    @Test
    @DisplayName("actualizar() retorna 200 con la finanza actualizada")
    void testActualizar() throws Exception {
        Long id = 1L;
        Finanza finanzaRequest = Finanza.builder()
                .rutPersona("12345678-9")
                .ingresosMensuales(1800.0)
                .deudas(250.0)
                .situacionLaboral("Empleado")
                .observaciones("Actualizada")
                .build();

        Finanza finanzaActualizada = Finanza.builder()
                .id(id)
                .rutPersona("12345678-9")
                .ingresosMensuales(1800.0)
                .deudas(250.0)
                .situacionLaboral("Empleado")
                .observaciones("Actualizada")
                .build();

        when(finanzaService.actualizar(anyLong(), any(Finanza.class))).thenReturn(finanzaActualizada);

        mockMvc.perform(put("/finanzas/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(finanzaRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rutPersona").value("12345678-9"))
                .andExpect(jsonPath("$.ingresosMensuales").value(1800.0))
                .andExpect(jsonPath("$.situacionLaboral").value("Empleado"));

        verify(finanzaService, times(1)).actualizar(id, finanzaRequest);
    }

    @Test
    @DisplayName("eliminar() retorna 204")
    void testEliminarRetorna204() throws Exception {
        Long id = 1L;
        doNothing().when(finanzaService).eliminar(anyLong());

        mockMvc.perform(delete("/finanzas/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(finanzaService, times(1)).eliminar(id);
    }
}
