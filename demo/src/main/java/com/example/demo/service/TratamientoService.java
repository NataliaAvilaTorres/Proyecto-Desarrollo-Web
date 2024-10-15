package com.example.demo.service;

import java.util.Collection;
import com.example.demo.model.Tratamiento;

public interface TratamientoService {

    public Tratamiento findById(Long id);
    public Collection<Tratamiento> findAll();
    public void deleteById(Long id);
    public Tratamiento update(Tratamiento tratamiento);
    public Tratamiento add(Tratamiento tratamiento);

}
