package com.example.demo.service;

import java.util.Collection;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

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
        Optional<Veterinario> veterinarioOpt = repo.findById(id);
        return veterinarioOpt.orElse(null);
    }

    @Override
    public List<Veterinario> findAll() {
        return new ArrayList<>(repo.findAll());
    }

    @Override
    public void deleteById(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        }
    }

    @Override
    public void update(Veterinario veterinario) {
        if (veterinario != null && veterinario.getId() != null && repo.existsById(veterinario.getId())) {
            repo.save(veterinario);
        }
    }

    @Override
    public void add(Veterinario veterinario) {
        if (veterinario != null) {
            repo.save(veterinario);
        }
    }

    @Override
    public boolean validateLogin(String correo, String contrasena) {
        Veterinario veterinario = repo.findByCorreo(correo);
        return veterinario != null && veterinario.getContrasena().equals(contrasena);
    }

    @Override
    public Veterinario validateLoginAndGetVeterinario(String correo, String contrasena) {
        Veterinario veterinario = repo.findByCorreo(correo);
        if (veterinario != null && veterinario.getContrasena().equals(contrasena)) {
            return veterinario;
        }
        return null;
    }
}
