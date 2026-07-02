package com.cl.duoc.service_deudas.controller;

import com.cl.duoc.service_deudas.model.Deuda;
import com.cl.duoc.service_deudas.service.DeudaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DeudaController.class)
class DeudaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    DeudaService deudaService;

    @Test
    void listar_returnsJsonArray() throws Exception {
        List<Deuda> list = List.of(
                Deuda.builder().id(1L).rutFallecido("1-9").build(),
                Deuda.builder().id(2L).rutFallecido("2-7").build()
        );
        when(deudaService.listar()).thenReturn(list);

        mockMvc.perform(get("/deudas").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(list.size()));
    }

    @Test
    void obtenerPorId_found() throws Exception {
        Deuda d = Deuda.builder().id(1L).rutFallecido("1-9").build();
        when(deudaService.buscarPorId(1L)).thenReturn(Optional.of(d));

        mockMvc.perform(get("/deudas/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void obtenerPorId_notFound() throws Exception {
        when(deudaService.buscarPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/deudas/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void crear_returnsCreatedAndLocation() throws Exception {
        Deuda toCreate = Deuda.builder().rutFallecido("1-9").tipoDeuda("A").institucion("X").monto(50.0).build();
        Deuda saved = Deuda.builder().id(5L).rutFallecido("1-9").tipoDeuda("A").institucion("X").monto(50.0).build();
        when(deudaService.guardar(any(Deuda.class))).thenReturn(saved);

        mockMvc.perform(post("/deudas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toCreate)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/deudas/5")))
                .andExpect(jsonPath("$.id").value(5));
    }

    @Test
    void actualizar_existing_returnsOk() throws Exception {
        Deuda update = Deuda.builder().rutFallecido("1-9").tipoDeuda("B").institucion("Y").monto(75.0).build();
        Deuda updated = Deuda.builder().id(2L).rutFallecido("1-9").tipoDeuda("B").institucion("Y").monto(75.0).build();
        when(deudaService.actualizar(eq(2L), any(Deuda.class))).thenReturn(Optional.of(updated));

        mockMvc.perform(put("/deudas/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.institucion").value("Y"));
    }

            @Test
            void testActualizar() throws Exception {
            Long id = 2L;
            Deuda request = Deuda.builder()
                .rutFallecido("12345678-9")
                .tipoDeuda("Credito automotriz")
                .institucion("Banco Santander")
                .monto(1800000.0)
                .build();
            Deuda updated = Deuda.builder()
                .id(id)
                .rutFallecido("12345678-9")
                .tipoDeuda("Credito automotriz")
                .institucion("Banco Santander")
                .monto(1800000.0)
                .build();

            when(deudaService.actualizar(eq(id), any(Deuda.class))).thenReturn(Optional.of(updated));

            mockMvc.perform(put("/deudas/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.rutFallecido").value("12345678-9"))
                .andExpect(jsonPath("$.tipoDeuda").value("Credito automotriz"))
                .andExpect(jsonPath("$.institucion").value("Banco Santander"))
                .andExpect(jsonPath("$.monto").value(1800000.0));

            verify(deudaService, times(1)).actualizar(eq(id), any(Deuda.class));
            }

    @Test
    void eliminar_existing_returnsNoContent() throws Exception {
        Deuda d = Deuda.builder().id(3L).rutFallecido("1-9").build();
        when(deudaService.buscarPorId(3L)).thenReturn(Optional.of(d));
        doNothing().when(deudaService).eliminar(3L);

        mockMvc.perform(delete("/deudas/3"))
                .andExpect(status().isNoContent());
    }

    @Test
    void eliminar_notFound_returnsNotFound() throws Exception {
        when(deudaService.buscarPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/deudas/99"))
                .andExpect(status().isNotFound());
    }

}
