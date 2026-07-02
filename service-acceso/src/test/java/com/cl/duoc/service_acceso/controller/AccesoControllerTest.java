package com.cl.duoc.service_acceso.controller;

import com.cl.duoc.service_acceso.model.Acceso;
import com.cl.duoc.service_acceso.service.AccesoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

@WebMvcTest(AccesoController.class)
@DisplayName("AccesoController - Pruebas Unitarias")
class AccesoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccesoService accesoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listar_returns200WithList() throws Exception {
        List<Acceso> accesos = List.of(
                Acceso.builder().id(1L).rutPersona("12345678-9").aguaPotable(true).electricidad(true).alcantarillado(false).tipoVivienda("Casa").observaciones("OK").build(),
                Acceso.builder().id(2L).rutPersona("98765432-1").aguaPotable(false).electricidad(true).alcantarillado(true).tipoVivienda("Departamento").observaciones("Revisar").build()
        );

        when(accesoService.listar()).thenReturn(accesos);

        mockMvc.perform(get("/acceso").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rutPersona").value("12345678-9"))
                .andExpect(jsonPath("$[1].rutPersona").value("98765432-1"));

        verify(accesoService, times(1)).listar();
    }

    @Test
    void buscarPorRutPersona_whenExists_returns200() throws Exception {
        String rutPersona = "12345678-9";
        Acceso acceso = Acceso.builder().id(1L).rutPersona(rutPersona).aguaPotable(true).electricidad(true).alcantarillado(true).tipoVivienda("Casa").observaciones("OK").build();
        when(accesoService.buscarPorRutPersona(anyString())).thenReturn(Optional.of(acceso));

        mockMvc.perform(get("/acceso/{rutPersona}", rutPersona).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rutPersona").value(rutPersona));

        verify(accesoService, times(1)).buscarPorRutPersona(rutPersona);
    }

    @Test
    void buscarPorRutPersona_whenNotFound_returns404() throws Exception {
        String rutPersona = "99999999-9";
        when(accesoService.buscarPorRutPersona(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get("/acceso/{rutPersona}", rutPersona).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(accesoService, times(1)).buscarPorRutPersona(rutPersona);
    }

    @Test
    void guardar_returns201() throws Exception {
        Acceso request = Acceso.builder().rutPersona("55555555-5").aguaPotable(true).electricidad(false).alcantarillado(true).tipoVivienda("Casa").observaciones("Nueva").build();
        Acceso saved = Acceso.builder().id(3L).rutPersona("55555555-5").aguaPotable(true).electricidad(false).alcantarillado(true).tipoVivienda("Casa").observaciones("Nueva").build();
        when(accesoService.guardar(any(Acceso.class))).thenReturn(saved);

        mockMvc.perform(post("/acceso").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.rutPersona").value("55555555-5"));

        verify(accesoService, times(1)).guardar(any(Acceso.class));
    }

    @Test
    void testActualizar() throws Exception {
        Long id = 1L;
        Acceso request = Acceso.builder()
                .rutPersona("12345678-9")
                .aguaPotable(true)
                .electricidad(true)
                .alcantarillado(true)
                .tipoVivienda("Casa")
                .observaciones("Actualizado")
                .build();
        Acceso updated = Acceso.builder()
                .id(id)
                .rutPersona("12345678-9")
                .aguaPotable(true)
                .electricidad(true)
                .alcantarillado(true)
                .tipoVivienda("Casa")
                .observaciones("Actualizado")
                .build();

        when(accesoService.actualizar(anyLong(), any(Acceso.class))).thenReturn(updated);

        mockMvc.perform(put("/acceso/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rutPersona").value("12345678-9"))
                .andExpect(jsonPath("$.tipoVivienda").value("Casa"))
                .andExpect(jsonPath("$.observaciones").value("Actualizado"));

        verify(accesoService, times(1)).actualizar(id, request);
    }

    @Test
    void eliminar_returns204() throws Exception {
        Long id = 1L;
        doNothing().when(accesoService).eliminar(anyLong());

        mockMvc.perform(delete("/acceso/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(accesoService, times(1)).eliminar(id);
    }
}
