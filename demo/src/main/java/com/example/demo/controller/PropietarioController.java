package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Propietario;
import com.example.demo.model.Mascota;
import com.example.demo.service.PropietarioService;

import java.util.List;
import java.util.ArrayList; // Importación añadida

@RestController
@RequestMapping("/api/propietarios")
public class PropietarioController {

    @Autowired
    private PropietarioService propietarioService;

    // Get all propietarios
    @GetMapping("/")
    public List<Propietario> getAllPropietarios() {
        return new ArrayList<>(propietarioService.findAll());
    }

    // Get a propietario by its ID
    @GetMapping("/{id}")
    public Propietario getPropietarioById(@PathVariable("id") Long id) {
        return propietarioService.findById(id);
    }

    // Create a new propietario
    @PostMapping
    public Propietario createPropietario(@RequestBody Propietario propietario) {
        propietarioService.add(propietario);
        return propietario;
    }

    // Update an existing propietario by its ID
    @PutMapping("/{id}")
    public Propietario updatePropietario(@PathVariable("id") Long id, @RequestBody Propietario propietario) {
        propietario.setId(id);
        propietarioService.update(propietario);
        return propietario;
    }

    // Delete a propietario by its ID
    @DeleteMapping("/{id}")
    public void deletePropietario(@PathVariable("id") Long id) {
        propietarioService.deleteById(id);
    }

    // Get all mascotas belonging to a specific propietario
    @GetMapping("/{id}/mascotas")
    public List<Mascota> getMascotasByPropietario(@PathVariable("id") Long id) {
        Propietario propietario = propietarioService.findById(id);
        if (propietario != null) {
            return propietario.getMascotas();
        } else {
            return null; // Optionally handle null case better, e.g., throw an exception
        }
    }
}
