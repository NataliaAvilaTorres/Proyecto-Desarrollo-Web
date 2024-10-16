package com.example.demo.service;

import java.util.Collection;
import com.example.demo.model.Tratamiento;
import java.util.List;  

public interface TratamientoService {

    public Tratamiento findById(Long id);
    public Collection<Tratamiento> findAll();
    public void deleteById(Long id);
    public void update(Tratamiento tratamiento);
    public void add(Tratamiento tratamiento);
    public List<Tratamiento> findByVeterinarioId(Long veterinarioId);  // Agregar este método

}
