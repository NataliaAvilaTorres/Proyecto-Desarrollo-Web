package com.example.demo.service;

import java.util.Collection;
import com.example.demo.model.Mascota;

public interface MascotaService {

    public Mascota findById(Long id);
    public Collection<Mascota> findAll();

    public void deleteById(Long id);
    public void update(Mascota mascota);
    public void add(Mascota mascota);
    
}
