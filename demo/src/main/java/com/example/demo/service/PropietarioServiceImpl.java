package com.example.demo.service;

import java.util.Collection;


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
        return repo.findById(id).get(); 
    }

    @Override
    public Collection<Propietario> findAll() {
        return repo.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void update(Propietario propietario) {
        repo.save(propietario);
    }

    @Override
    public void add(Propietario propietario) {
        repo.save(propietario);
    }

    @Override
    public Propietario findByCedula(String cedula) {
        return repo.findByCedula(cedula);
    }

    @Override
    public Propietario validateLoginAndGetPropietario(String correo, String contrasena) {
        // Busca el propietario por correo
        Propietario propietario = repo.findByCorreo(correo);
        
        // Valida si el propietario existe y la contraseña coincide
        if (propietario != null && propietario.getContrasena().equals(contrasena)) {
            return propietario; // Credenciales válidas, devuelve el propietario
        }
        
        return null; // Credenciales incorrectas
    }

    @Override
    public Propietario findByCorreo(String correo) {
        return repo.findByCorreo(correo);
    }

    
}
