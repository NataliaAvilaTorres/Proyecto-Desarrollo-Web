package com.example.demo.service;

import com.example.demo.model.Tratamiento;

import java.time.LocalDate;
import java.util.List;  

public interface TratamientoService {

    public Tratamiento findById(Long id);
    public List<Tratamiento> findAll();
    public void deleteById(Long id);
    public void update(Tratamiento tratamiento);
    public void add(Tratamiento tratamiento);


    // Nuevas consultas
    public List<Tratamiento> findByMascotaId(Long mascotaId);
    public List<Tratamiento> findByCantidadGreaterThan(Float cantidad);
    public List<Tratamiento> findByVeterinarioId(Long veterinarioId);
    public int countByFechaAfter(LocalDate date);
    public List<Object[]> countByMedicamentoAndFechaAfter(LocalDate fecha);


}