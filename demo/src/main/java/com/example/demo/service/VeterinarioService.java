package com.example.demo.service;

import java.util.Collection;

import com.example.demo.model.Veterinario;

public interface VeterinarioService {

    public Veterinario findById(Long id);
    public Collection<Veterinario> findAll();

    public void deleteById(Long id);
    public void update(Veterinario veterinario);
    public void add(Veterinario veterinario);

    public boolean validateLogin(String correo, String contrasena);
    
}
