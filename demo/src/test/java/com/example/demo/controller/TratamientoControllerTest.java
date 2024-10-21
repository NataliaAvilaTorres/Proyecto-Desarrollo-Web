package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.model.Tratamiento;
import com.example.demo.service.TratamientoService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TratamientoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TratamientoService tratamientoService;

    @InjectMocks
    private TratamientoController tratamientoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tratamientoController).build();
    }

    // Helper method to convert objects to JSON strings
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 1. GET request test - Obtener tratamiento por ID
    @Test
    public void testGetTratamientoById() throws Exception {
        Tratamiento tratamiento = new Tratamiento(Date.valueOf("2024-10-15"), 2.0f);
        tratamiento.setId(1L);

        when(tratamientoService.findById(1L)).thenReturn(tratamiento);

        mockMvc.perform(get("/api/tratamientos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.cantidad").value(2.0f))
                .andDo(print());

        verify(tratamientoService, times(1)).findById(1L);
    }

    // 2. POST request test - Crear un nuevo tratamiento
    @Test
    public void testCreateTratamiento() throws Exception {
        Tratamiento tratamiento = new Tratamiento(Date.valueOf("2024-10-15"), 2.0f);
        tratamiento.setId(1L);

        // Corregido con doNothing para el método void
        doNothing().when(tratamientoService).add(any(Tratamiento.class));

        mockMvc.perform(post("/api/tratamientos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(tratamiento)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.cantidad").value(2.0f))
                .andDo(print());

        verify(tratamientoService, times(1)).add(any(Tratamiento.class));
    }

    // 3. PUT request test - Actualizar un tratamiento
    @Test
    public void testUpdateTratamiento() throws Exception {
        Tratamiento tratamiento = new Tratamiento(Date.valueOf("2024-10-15"), 3.0f);
        tratamiento.setId(1L);

        // Corregido con doNothing para el método void
        doNothing().when(tratamientoService).update(any(Tratamiento.class));

        mockMvc.perform(put("/api/tratamientos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(tratamiento)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidad").value(3.0f))
                .andDo(print());

        verify(tratamientoService, times(1)).update(any(Tratamiento.class));
    }

    // 4. DELETE request test - Eliminar un tratamiento
    @Test
    public void testDeleteTratamiento() throws Exception {
        // Corregido con doNothing para el método void
        doNothing().when(tratamientoService).deleteById(1L);

        mockMvc.perform(delete("/api/tratamientos/1"))
                .andExpect(status().isOk())
                .andDo(print());

        verify(tratamientoService, times(1)).deleteById(1L);
    }

    // 5. GET request test with additional parameter - Obtener tratamientos por veterinario
    @Test
    public void testGetTratamientosByVeterinario() throws Exception {
        Tratamiento tratamiento1 = new Tratamiento(Date.valueOf("2024-10-15"), 2.0f);
        tratamiento1.setId(1L);
        Tratamiento tratamiento2 = new Tratamiento(Date.valueOf("2024-10-16"), 3.0f);
        tratamiento2.setId(2L);

        when(tratamientoService.findByVeterinarioId(1L)).thenReturn(Arrays.asList(tratamiento1, tratamiento2));

        mockMvc.perform(get("/api/tratamientos/veterinario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andDo(print());

        verify(tratamientoService, times(1)).findByVeterinarioId(1L);
    }
}
