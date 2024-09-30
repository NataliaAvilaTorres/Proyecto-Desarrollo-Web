package com.example.demo.service;

import java.util.Collection;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Propietario;
import com.example.demo.repository.PropietarioRepository;

@Service
public class PropietarioServiceImpl implements PropietarioService {

    @Autowired
    PropietarioRepository repo;

    @Override
    public Propietario findById(Long id) {
        Optional<Propietario> propietarioOpt = repo.findById(id);
        return propietarioOpt.orElse(null); 
    }

    @Override
    public List<Propietario> findAll() {
        return new ArrayList<>(repo.findAll());
    }

    @Override
    public void deleteById(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        }
    }

    @Override
    public void update(Propietario propietario) {
        if (propietario != null && propietario.getId() != null && repo.existsById(propietario.getId())) {
            repo.save(propietario);
        }
    }

    @Override
    public void add(Propietario propietario) {
        if (propietario != null) {
            repo.save(propietario);
        }
    }

    @Override
    public Propietario findByCedula(String cedula) {
        return repo.findByCedula(cedula);
    }

    @Override
    public Propietario validateLoginAndGetPropietario(String correo, String contrasena) {
        Propietario propietario = repo.findByCorreo(correo);
        if (propietario != null && propietario.getContrasena().equals(contrasena)) {
            return propietario; 
        }
        return null; 
    }

    @Override
    public Propietario findByCorreo(String correo) {
        return repo.findByCorreo(correo);
    }
}
