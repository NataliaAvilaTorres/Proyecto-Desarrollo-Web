package com.example.demo.service;

import java.util.Collection;
import java.util.Map;


import com.example.demo.model.Administrador;

public interface AdministradorService {

    public Administrador findById(Long id);
    public Collection<Administrador> findAll();
    public void deleteById(Long id);
    public void update(Administrador administrador);
    public void add(Administrador administrador);
    Map<String, Object> getDashboardKPIs();
    
}