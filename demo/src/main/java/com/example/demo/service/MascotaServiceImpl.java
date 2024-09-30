package com.example.demo.service;

import java.util.Collection;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Mascota;
import com.example.demo.repository.MascotaRepository;

@Service
public class MascotaServiceImpl implements MascotaService {

    @Autowired
    MascotaRepository repo;

    @Override
    public Mascota findById(Long id) {
        Optional<Mascota> mascotaOpt = repo.findById(id);
        return mascotaOpt.orElse(null);
    }

    @Override
    public List<Mascota> findAll() {
        return new ArrayList<>(repo.findAll());
    }

    @Override
    public void deleteById(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        }
    }

    @Override
    public void update(Mascota mascota) {
        if (mascota != null && mascota.getId() != null && repo.existsById(mascota.getId())) {
            repo.save(mascota);
        }
    }

    @Override
    public void add(Mascota mascota) {
        if (mascota != null) {
            repo.save(mascota);
        }
    }
}
