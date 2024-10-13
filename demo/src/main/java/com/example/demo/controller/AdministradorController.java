package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Administrador;
import com.example.demo.service.AdministradorService;

@RestController
@RequestMapping("/api/administradores")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    // Get all administradores
    @GetMapping("/")
    public List<Administrador> getAllAdministradores() {
        return new ArrayList<>(administradorService.findAll());
    }

    // Get an admin by its ID
    @GetMapping("/{id}")
    public Administrador getAdministradorById(@PathVariable("id") Long id) {
        return administradorService.findById(id);
    }

    // Create a new admin
    @PostMapping
    public Administrador createAdministrador(@RequestBody Administrador administrador) {
        administradorService.add(administrador);
        return administrador;
    }

    // Update an existing admin by its ID
    @PutMapping("/{id}")
    public Administrador updateAdministrador(@PathVariable("id") Long id, @RequestBody Administrador administrador) {
        administrador.setId(id);
        administradorService.update(administrador);
        return administrador;
    }

    // Delete an admin by its ID
    @DeleteMapping("/{id}")
    public void deleteAdministrador(@PathVariable("id") Long id) {
        administradorService.deleteById(id);
    }

    // Endpoint para obtener los KPIs del dashboard
    @GetMapping("/dashboard")
    public Map<String, Object> getDashboardKPIs() {
        return administradorService.getDashboardKPIs();
    }

}