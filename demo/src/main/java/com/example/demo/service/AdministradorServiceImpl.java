package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Administrador;
import com.example.demo.repository.AdministradorRepository;

@Service
public class AdministradorServiceImpl implements AdministradorService {

    @Autowired
    AdministradorRepository repo;

    @Override
    public Administrador findById(Long id) {
        Optional<Administrador> adminOpt = repo.findById(id);
        return adminOpt.orElse(null);
    }

    @Override
    public List<Administrador> findAll() {
        return new ArrayList<>(repo.findAll());
    }

    @Override
    public void deleteById(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        }
    }

    @Override
    public void update(Administrador administrador) {
        if (administrador != null && administrador.getId() != null && repo.existsById(administrador.getId())) {
            repo.save(administrador);
        }
    }

    @Override
    public void add(Administrador administrador) {
        if (administrador != null) {
            repo.save(administrador);
        }
    }

}