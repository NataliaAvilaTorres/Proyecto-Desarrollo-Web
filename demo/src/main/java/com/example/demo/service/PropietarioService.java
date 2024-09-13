package com.example.demo.service;

import java.util.Collection;

import com.example.demo.model.Propietario;

public interface PropietarioService {
    
    public Propietario findById(Long id);
    public Collection<Propietario> findAll();

    public void deleteById(Long id);
    public void update(Propietario propietario);
    public void add(Propietario propietario);
    
}
