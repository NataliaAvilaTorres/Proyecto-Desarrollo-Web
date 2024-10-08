package com.example.demo.service;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Mascota;
import com.example.demo.model.Tratamiento;
import com.example.demo.repository.MascotaRepository;
import com.example.demo.repository.TratamientoRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MascotaServiceImpl implements MascotaService {

    @Autowired
    MascotaRepository repo;

    @Autowired
    TratamientoRepository tratamientoRepository; // Inyectar el TratamientoRepository

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
    @Transactional // Agrega esta anotación para asegurar que la operación sea atómica
    public void deleteById(Long id) {
        // Verificar si la mascota existe
        if (repo.existsById(id)) {
            // Primero eliminar todos los tratamientos asociados a la mascota
            List<Tratamiento> tratamientos = tratamientoRepository.findByMascotaId(id);
            if (!tratamientos.isEmpty()) {
                tratamientoRepository.deleteAll(tratamientos);
            }
            
            // Luego eliminar la mascota
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
