package com.cl.duoc.service_documentos.controller;

import com.cl.duoc.service_documentos.model.Documento;
import com.cl.duoc.service_documentos.service.DocumentoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
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

@WebMvcTest(DocumentoController.class)
@DisplayName("DocumentoController - Pruebas Unitarias")
class DocumentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DocumentoService documentoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllDocumentos_returns200WithList() throws Exception {
        List<Documento> documentos = List.of(
                Documento.builder().id(1L).rutFallecido("12345678-9").tipoDocumento("DNI").nombreDocumento("Cedula").fechaEmision(LocalDate.of(2023, 1, 1)).build(),
                Documento.builder().id(2L).rutFallecido("98765432-1").tipoDocumento("Acta").nombreDocumento("Defunción").fechaEmision(LocalDate.of(2024, 2, 2)).build()
        );

        when(documentoService.getAllDocumentos()).thenReturn(documentos);

        mockMvc.perform(get("/documentos").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rutFallecido").value("12345678-9"))
                .andExpect(jsonPath("$[1].rutFallecido").value("98765432-1"));

        verify(documentoService, times(1)).getAllDocumentos();
    }

    @Test
    void getDocumentoById_whenExists_returns200() throws Exception {
        Long id = 1L;
        Documento documento = Documento.builder().id(id).rutFallecido("12345678-9").tipoDocumento("DNI").nombreDocumento("Cedula").fechaEmision(LocalDate.of(2023, 1, 1)).build();
        when(documentoService.getDocumentoById(anyLong())).thenReturn(documento);

        mockMvc.perform(get("/documentos/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(documentoService, times(1)).getDocumentoById(id);
    }

    @Test
    void getDocumentoByRut_whenExists_returns200() throws Exception {
        String rut = "12345678-9";
        Documento documento = Documento.builder().id(1L).rutFallecido(rut).tipoDocumento("DNI").nombreDocumento("Cedula").fechaEmision(LocalDate.of(2023, 1, 1)).build();
        when(documentoService.findByRut(anyString())).thenReturn(Optional.of(documento));

        mockMvc.perform(get("/documentos/rut/{rut}", rut).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rutFallecido").value(rut));

        verify(documentoService, times(1)).findByRut(rut);
    }

    @Test
    void getDocumentoByRut_whenNotFound_returns404() throws Exception {
        String rut = "99999999-9";
        when(documentoService.findByRut(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get("/documentos/rut/{rut}", rut).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(documentoService, times(1)).findByRut(rut);
    }

    @Test
    void createDocumento_returns201WithLocation() throws Exception {
        Documento request = Documento.builder().rutFallecido("55555555-5").tipoDocumento("DNI").nombreDocumento("Cedula").fechaEmision(LocalDate.of(2025, 5, 5)).build();
        Documento saved = Documento.builder().id(3L).rutFallecido("55555555-5").tipoDocumento("DNI").nombreDocumento("Cedula").fechaEmision(LocalDate.of(2025, 5, 5)).build();
        when(documentoService.createDocumento(any(Documento.class))).thenReturn(saved);

        mockMvc.perform(post("/documentos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.endsWith("/documentos/3")))
                .andExpect(jsonPath("$.id").value(3));

        verify(documentoService, times(1)).createDocumento(any(Documento.class));
    }

    @Test
    void updateDocumento_returns200() throws Exception {
        Long id = 1L;
        Documento request = Documento.builder().rutFallecido("12345678-9").tipoDocumento("DNI").nombreDocumento("Cedula").fechaEmision(LocalDate.of(2023, 1, 1)).build();
        Documento updated = Documento.builder().id(id).rutFallecido("12345678-9").tipoDocumento("DNI").nombreDocumento("Cedula").fechaEmision(LocalDate.of(2023, 1, 1)).build();
        when(documentoService.actualizar(anyLong(), any(Documento.class))).thenReturn(updated);

        mockMvc.perform(put("/documentos/{id}", id).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(documentoService, times(1)).actualizar(anyLong(), any(Documento.class));
    }

    @Test
    void testActualizar() throws Exception {
        Long id = 1L;
        Documento request = Documento.builder()
                .rutFallecido("12345678-9")
                .tipoDocumento("Certificado")
                .nombreDocumento("certificado-actualizado.pdf")
                .fechaEmision(LocalDate.of(2024, 10, 16))
                .build();
        Documento updated = Documento.builder()
                .id(id)
                .rutFallecido("12345678-9")
                .tipoDocumento("Certificado")
                .nombreDocumento("certificado-actualizado.pdf")
                .fechaEmision(LocalDate.of(2024, 10, 16))
                .build();

        when(documentoService.actualizar(anyLong(), any(Documento.class))).thenReturn(updated);

        mockMvc.perform(put("/documentos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rutFallecido").value("12345678-9"))
                .andExpect(jsonPath("$.tipoDocumento").value("Certificado"))
                .andExpect(jsonPath("$.nombreDocumento").value("certificado-actualizado.pdf"));

        verify(documentoService, times(1)).actualizar(anyLong(), any(Documento.class));
    }

    @Test
    void deleteDocumento_returns204() throws Exception {
        Long id = 1L;
        doNothing().when(documentoService).deleteDocumento(anyLong());

        mockMvc.perform(delete("/documentos/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(documentoService, times(1)).deleteDocumento(id);
    }
}
