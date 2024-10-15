package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Veterinario;
import com.example.demo.service.VeterinarioService;

import java.util.List;
import java.util.ArrayList; // Importación añadida

@RestController
@RequestMapping("/api/veterinarios")
@CrossOrigin(origins = "http://localhost:4200")
public class VeterinarioController {

    @Autowired
    private VeterinarioService veterinarioService;

    // Get all veterinarios
    @GetMapping("/")
    public List<Veterinario> getAllVeterinarios() {
        return new ArrayList<>(veterinarioService.findAll());
    }

    // Get a veterinario by its ID
    @GetMapping("/{id}")
    public Veterinario getVeterinarioById(@PathVariable("id") Long id) {
        return veterinarioService.findById(id);
    }

    // Create a new veterinario
    @PostMapping("/")
    public Veterinario createVeterinario(@RequestBody Veterinario veterinario) {
        veterinarioService.add(veterinario);
        return veterinario;
    }

    // Update an existing veterinario by its ID
    @PutMapping("/{id}")
    public Veterinario updateVeterinario(@PathVariable("id") Long id, @RequestBody Veterinario veterinario) {
        veterinario.setId(id);
        veterinarioService.update(veterinario);
        return veterinario;
    }

    // Delete a veterinario by its ID
    @DeleteMapping("/{id}")
    public void deleteVeterinario(@PathVariable("id") Long id) {
        veterinarioService.deleteById(id);
    }
}
