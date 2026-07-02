package com.cl.duoc.service_personas.controller;

import com.cl.duoc.service_personas.model.Persona;
import com.cl.duoc.service_personas.service.PersonaService;
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

import static org.hamcrest.Matchers.hasSize;
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

@WebMvcTest(PersonaController.class)
@DisplayName("PersonaController - Pruebas Unitarias")
class PersonaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PersonaService personaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("listar() debe retornar status 200 con lista de personas")
    void testListar() throws Exception {
        // Arrange
        List<Persona> personas = Arrays.asList(
                Persona.builder()
                        .id(1L)
                        .rut("12345678-9")
                        .nombres("Juan")
                        .apellidos("Pérez")
                        .edad(30)
                        .direccion("Calle Principal 123")
                        .fechaNacimiento("1993-05-15")
                        .build(),
                Persona.builder()
                        .id(2L)
                        .rut("98765432-1")
                        .nombres("María")
                        .apellidos("González")
                        .edad(28)
                        .direccion("Calle Secundaria 456")
                        .fechaNacimiento("1995-08-20")
                        .build()
        );

        when(personaService.listar()).thenReturn(personas);

        // Act & Assert
        mockMvc.perform(get("/personas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].rut").value("12345678-9"))
                .andExpect(jsonPath("$[0].nombres").value("Juan"))
                .andExpect(jsonPath("$[1].rut").value("98765432-1"))
                .andExpect(jsonPath("$[1].nombres").value("María"));

        verify(personaService, times(1)).listar();
    }

    @Test
    @DisplayName("buscarPorRut() cuando existe debe retornar status 200")
    void testBuscarPorRutExistente() throws Exception {
        // Arrange
        String rut = "12345678-9";
        Persona persona = Persona.builder()
                .id(1L)
                .rut(rut)
                .nombres("Juan")
                .apellidos("Pérez")
                .edad(30)
                .direccion("Calle Principal 123")
                .fechaNacimiento("1993-05-15")
                .build();

        when(personaService.buscarPorRut(rut)).thenReturn(Optional.of(persona));

        // Act & Assert
        mockMvc.perform(get("/personas/{rut}", rut)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rut").value("12345678-9"))
                .andExpect(jsonPath("$.nombres").value("Juan"))
                .andExpect(jsonPath("$.apellidos").value("Pérez"))
                .andExpect(jsonPath("$.edad").value(30));

        verify(personaService, times(1)).buscarPorRut(rut);
    }

    @Test
    @DisplayName("buscarPorRut() cuando no existe debe retornar status 404")
    void testBuscarPorRutNoExistente() throws Exception {
        // Arrange
        String rut = "99999999-9";
        when(personaService.buscarPorRut(rut)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/personas/{rut}", rut)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(personaService, times(1)).buscarPorRut(rut);
    }

    @Test
    @DisplayName("guardar() debe retornar status 201 CREATED")
    void testGuardar() throws Exception {
        // Arrange
        Persona personaRequest = Persona.builder()
                .rut("55555555-5")
                .nombres("Pedro")
                .apellidos("López")
                .edad(35)
                .direccion("Calle Nueva 789")
                .fechaNacimiento("1988-12-01")
                .build();

        Persona personaGuardada = Persona.builder()
                .id(3L)
                .rut("55555555-5")
                .nombres("Pedro")
                .apellidos("López")
                .edad(35)
                .direccion("Calle Nueva 789")
                .fechaNacimiento("1988-12-01")
                .build();

        when(personaService.guardar(any(Persona.class))).thenReturn(personaGuardada);

        // Act & Assert
        mockMvc.perform(post("/personas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personaRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.rut").value("55555555-5"))
                .andExpect(jsonPath("$.nombres").value("Pedro"))
                .andExpect(jsonPath("$.apellidos").value("López"));

        verify(personaService, times(1)).guardar(any(Persona.class));
    }

    @Test
    @DisplayName("actualizar() debe retornar status 200 OK con la persona actualizada")
    void testActualizar() throws Exception {
        // Arrange
        Long id = 1L;
        Persona personaRequest = Persona.builder()
                .rut("12345678-9")
                .nombres("Juan Carlos")
                .apellidos("Pérez Gómez")
                .edad(31)
                .direccion("Calle Actualizada 456")
                .fechaNacimiento("1993-05-15")
                .build();

        Persona personaActualizada = Persona.builder()
                .id(id)
                .rut("12345678-9")
                .nombres("Juan Carlos")
                .apellidos("Pérez Gómez")
                .edad(31)
                .direccion("Calle Actualizada 456")
                .fechaNacimiento("1993-05-15")
                .build();

        when(personaService.actualizar(anyLong(), any(Persona.class))).thenReturn(personaActualizada);

        // Act & Assert
        mockMvc.perform(put("/personas/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personaRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rut").value("12345678-9"))
                .andExpect(jsonPath("$.nombres").value("Juan Carlos"))
                .andExpect(jsonPath("$.apellidos").value("Pérez Gómez"));

        verify(personaService, times(1)).actualizar(id, personaRequest);
    }

    @Test
    @DisplayName("eliminar() debe retornar status 204 NO_CONTENT")
    void testEliminar() throws Exception {
        // Arrange
        Long id = 1L;
        doNothing().when(personaService).eliminar(anyLong());

        // Act & Assert
        mockMvc.perform(delete("/personas/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(personaService, times(1)).eliminar(id);
    }
}
