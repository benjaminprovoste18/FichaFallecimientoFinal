package com.cl.duoc.service_beneficios.controller;

import com.cl.duoc.service_beneficios.model.Beneficio;
import com.cl.duoc.service_beneficios.service.BeneficioService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeneficioController.class)
@DisplayName("BeneficioController - Pruebas Unitarias")
class BeneficioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BeneficioService beneficioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listar_returns200WithList() throws Exception {
        List<Beneficio> beneficios = List.of(
                Beneficio.builder().id(1L).rutFallecido("12345678-9").nombreBeneficio("Pensión").institucion("AFP").montoBeneficio(100000.0).build(),
                Beneficio.builder().id(2L).rutFallecido("98765432-1").nombreBeneficio("Seguro").institucion("ISAPRE").montoBeneficio(50000.0).build()
        );

        when(beneficioService.listar()).thenReturn(beneficios);

        mockMvc.perform(get("/beneficios").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rutFallecido").value("12345678-9"))
                .andExpect(jsonPath("$[1].rutFallecido").value("98765432-1"));

        verify(beneficioService, times(1)).listar();
    }

    @Test
    void obtenerPorId_whenExists_returns200() throws Exception {
        Long id = 1L;
        Beneficio beneficio = Beneficio.builder().id(id).rutFallecido("12345678-9").nombreBeneficio("Pensión").institucion("AFP").montoBeneficio(100000.0).build();
        when(beneficioService.buscarPorId(anyLong())).thenReturn(Optional.of(beneficio));

        mockMvc.perform(get("/beneficios/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(beneficioService, times(1)).buscarPorId(id);
    }

    @Test
    void obtenerPorRut_whenExists_returns200() throws Exception {
        String rut = "12345678-9";
        Beneficio beneficio = Beneficio.builder().id(1L).rutFallecido(rut).nombreBeneficio("Pensión").institucion("AFP").montoBeneficio(100000.0).build();
        when(beneficioService.buscarPorRut(anyString())).thenReturn(Optional.of(beneficio));

        mockMvc.perform(get("/beneficios/rut/{rut}", rut).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rutFallecido").value(rut));

        verify(beneficioService, times(1)).buscarPorRut(rut);
    }

    @Test
    void crear_returns201WithLocation() throws Exception {
        Beneficio request = Beneficio.builder().rutFallecido("55555555-5").nombreBeneficio("Ayuda").institucion("Municipio").montoBeneficio(20000.0).build();
        Beneficio saved = Beneficio.builder().id(3L).rutFallecido("55555555-5").nombreBeneficio("Ayuda").institucion("Municipio").montoBeneficio(20000.0).build();
        when(beneficioService.guardar(any(Beneficio.class))).thenReturn(saved);

        mockMvc.perform(post("/beneficios").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/beneficios/3"))
                .andExpect(jsonPath("$.id").value(3));

        verify(beneficioService, times(1)).guardar(any(Beneficio.class));
    }

    @Test
    void actualizar_returns200() throws Exception {
        Long id = 1L;
        Beneficio request = Beneficio.builder().rutFallecido("12345678-9").nombreBeneficio("Pensión").institucion("AFP").montoBeneficio(120000.0).build();
        Beneficio updated = Beneficio.builder().id(id).rutFallecido("12345678-9").nombreBeneficio("Pensión").institucion("AFP").montoBeneficio(120000.0).build();
        when(beneficioService.actualizar(anyLong(), any(Beneficio.class))).thenReturn(updated);

        mockMvc.perform(put("/beneficios/{id}", id).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.montoBeneficio").value(120000.0));

        verify(beneficioService, times(1)).actualizar(anyLong(), any(Beneficio.class));
    }

    @Test
    void testActualizar() throws Exception {
        Long id = 1L;
        Beneficio request = Beneficio.builder()
                .rutFallecido("12345678-9")
                .nombreBeneficio("Subsidio funerario")
                .institucion("SENAME")
                .montoBeneficio(750000.0)
                .build();
        Beneficio updated = Beneficio.builder()
                .id(id)
                .rutFallecido("12345678-9")
                .nombreBeneficio("Subsidio funerario")
                .institucion("SENAME")
                .montoBeneficio(750000.0)
                .build();

        when(beneficioService.actualizar(anyLong(), any(Beneficio.class))).thenReturn(updated);

        mockMvc.perform(put("/beneficios/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rutFallecido").value("12345678-9"))
                .andExpect(jsonPath("$.nombreBeneficio").value("Subsidio funerario"))
                .andExpect(jsonPath("$.institucion").value("SENAME"))
                .andExpect(jsonPath("$.montoBeneficio").value(750000.0));

        verify(beneficioService, times(1)).actualizar(anyLong(), any(Beneficio.class));
    }

    @Test
    void eliminar_returns204() throws Exception {
        Long id = 1L;
        doNothing().when(beneficioService).eliminar(anyLong());

        mockMvc.perform(delete("/beneficios/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(beneficioService, times(1)).eliminar(id);
    }
}
