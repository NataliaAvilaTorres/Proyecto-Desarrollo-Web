package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Tratamiento;
import com.example.demo.repository.TratamientoRepository;

@Service
public class TratamientoServiceImpl implements TratamientoService {

    @Autowired
    TratamientoRepository repo;

    @Override
    public Tratamiento findById(Long id) {
        Optional<Tratamiento> tratamientoOpt = repo.findById(id);
        return tratamientoOpt.orElse(null);
    }

    @Override
    public List<Tratamiento> findAll() {
        return new ArrayList<>(repo.findAll());
    }

    @Override
    public void deleteById(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        }
    }

    @Override
    public void update(Tratamiento tratamiento) {
        if (tratamiento != null && tratamiento.getId() != null && repo.existsById(tratamiento.getId())) {
            repo.save(tratamiento);
        }
    }

    @Override
    public void add(Tratamiento tratamiento) {
        if (tratamiento != null) {
            repo.save(tratamiento);
        }
    }
    @Override
    public List<Tratamiento> findByVeterinarioId(Long veterinarioId) {
        return repo.findByVeterinarioId(veterinarioId);
    }

    // Implementación de las consultas adicionales

    @Override
    public int countByFechaAfter(LocalDate date) {
        return repo.countByFechaAfter(date);
    }

    @Override
    public List<Object[]> countByMedicamentoAndFechaAfter(LocalDate fecha) {
        return repo.countByMedicamentoAndFechaAfter(fecha);
    }

    @Override
    public List<Tratamiento> findByMascotaId(Long mascotaId) {
        return repo.findByMascotaId(mascotaId);

    }

    @Override
    public List<Tratamiento> findByCantidadGreaterThan(Float cantidad) {
        return repo.findByCantidadGreaterThan(cantidad);
    }

}
