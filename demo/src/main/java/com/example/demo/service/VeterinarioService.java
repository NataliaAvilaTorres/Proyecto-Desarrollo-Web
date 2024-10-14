package com.example.demo.service;

import java.util.Collection;
import java.util.Map;

import com.example.demo.model.Veterinario;

public interface VeterinarioService {

    public Veterinario findById(Long id);
    public Collection<Veterinario> findAll();

    public void deleteById(Long id);
    public void update(Veterinario veterinario);
    public void add(Veterinario veterinario);
    // Método para obtener los KPIs
    public Map<String, Object> getVeterinarioKPIs();
    
    public boolean validateLogin(String correo, String contrasena);
    public Veterinario validateLoginAndGetVeterinario(String correo, String contrasena); // Añadir este método
}
