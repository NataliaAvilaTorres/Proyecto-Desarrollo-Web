package com.example.demo.service;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.model.Tratamiento;
import com.example.demo.repository.TratamientoRepository;

public class TratamientoServiceTest {

    @Mock
    private TratamientoRepository tratamientoRepository;

    @InjectMocks
    private TratamientoServiceImpl tratamientoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 1. Prueba para findById con mock
    @Test
    public void testFindById() {
        Tratamiento tratamiento = new Tratamiento(Date.valueOf("2024-10-15"), 2.0f);
        tratamiento.setId(1L);

        when(tratamientoRepository.findById(1L)).thenReturn(Optional.of(tratamiento));

        Tratamiento result = tratamientoService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    // 2. Prueba para findAll con mock
    @Test
    public void testFindAll() {
        Tratamiento tratamiento1 = new Tratamiento(Date.valueOf("2024-10-15"), 2.0f);
        tratamiento1.setId(1L);
        Tratamiento tratamiento2 = new Tratamiento(Date.valueOf("2024-10-16"), 3.0f);
        tratamiento2.setId(2L);

        when(tratamientoRepository.findAll()).thenReturn(Arrays.asList(tratamiento1, tratamiento2));

        List<Tratamiento> result = tratamientoService.findAll();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
    }

    // 3. Prueba para deleteById con mock
    @Test
    public void testDeleteById() {
        // Se asegura de que existsById devuelva true
        when(tratamientoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(tratamientoRepository).deleteById(1L);

        tratamientoService.deleteById(1L);

        verify(tratamientoRepository, times(1)).deleteById(1L);
    }

    // 4. Prueba para update con mock
    @Test
    public void testUpdate() {
        Tratamiento tratamiento = new Tratamiento(Date.valueOf("2024-10-15"), 2.0f);
        tratamiento.setId(1L);

        when(tratamientoRepository.existsById(1L)).thenReturn(true);
        when(tratamientoRepository.save(tratamiento)).thenReturn(tratamiento);

        tratamientoService.update(tratamiento);

        verify(tratamientoRepository, times(1)).save(tratamiento);
    }

    // 5. Prueba para add con mock
    @Test
    public void testAdd() {
        Tratamiento tratamiento = new Tratamiento(Date.valueOf("2024-10-15"), 2.0f);
        tratamiento.setId(1L);

        when(tratamientoRepository.save(tratamiento)).thenReturn(tratamiento);

        tratamientoService.add(tratamiento);

        verify(tratamientoRepository, times(1)).save(tratamiento);
    }
}
