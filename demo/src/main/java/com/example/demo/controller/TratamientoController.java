package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Tratamiento;
import com.example.demo.service.TratamientoService;

@RestController
@RequestMapping("/api/tratamientos")
@CrossOrigin(origins = "http://localhost:4200")
public class TratamientoController {

    @Autowired
    private TratamientoService tratamientoService;

    // Get all tratamientos
    @GetMapping("/")
    public List<Tratamiento> getAllTratamientos() {
        return new ArrayList<>(tratamientoService.findAll());
    }

    // Get a tratamiento by its ID
    @GetMapping("/{id}")
    public Tratamiento getTratamientoById(@PathVariable("id") Long id) {
        return tratamientoService.findById(id);
    }

    // Create a new tratamiento
    @PostMapping
    public Tratamiento createTratamiento(@RequestBody Tratamiento tratamiento) {
        tratamientoService.add(tratamiento);
        return tratamiento;
    }

    // Update an existing tratamiento by its ID
    @PutMapping("/{id}")
    public Tratamiento updateTratamiento(@PathVariable("id") Long id, @RequestBody Tratamiento tratamiento) {
        tratamiento.setId(id);
        tratamientoService.update(tratamiento);
        return tratamiento;
    }

    // Delete a tratamiento by its ID
    @DeleteMapping("/{id}")
    public void deleteTratamiento(@PathVariable("id") Long id) {
        tratamientoService.deleteById(id);
    }

    // Endpoint para obtener los tratamientos por veterinario
    @GetMapping("/veterinario/{veterinarioId}")
    public List<Tratamiento> getTratamientosByVeterinario(@PathVariable("veterinarioId") Long veterinarioId) {
        return tratamientoService.findByVeterinarioId(veterinarioId);
    }

}
