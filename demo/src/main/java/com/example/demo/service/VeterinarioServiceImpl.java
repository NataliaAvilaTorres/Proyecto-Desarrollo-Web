package com.example.demo.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Veterinario;
import com.example.demo.repository.VeterinarioRepository;

@Service
public class VeterinarioServiceImpl implements VeterinarioService {

    @Autowired
    VeterinarioRepository repo;

    @Override
    public Veterinario findById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public Collection<Veterinario> findAll() {
        return repo.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void update(Veterinario veterinario) {
        repo.save(veterinario);
    }

    @Override
    public void add(Veterinario veterinario) {
        repo.save(veterinario);
    }

    @Override
    public boolean validateLogin(String correo, String contrasena) {
        Veterinario veterinario = repo.findByCorreo(correo);
        return veterinario != null && veterinario.getContrasena().equals(contrasena);
    }
    
}
