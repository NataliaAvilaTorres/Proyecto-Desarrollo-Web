package com.example.demo.service;

import java.util.Optional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Tratamiento;
import com.example.demo.model.Veterinario;
import com.example.demo.repository.TratamientoRepository;
import com.example.demo.repository.VeterinarioRepository;

@Service
public class VeterinarioServiceImpl implements VeterinarioService {

    @Autowired
    VeterinarioRepository repo;

    @Autowired
    TratamientoRepository tratamientoRepo;

    @Override
    public Veterinario findById(Long id) {
        Optional<Veterinario> veterinarioOpt = repo.findById(id);
        return veterinarioOpt.orElse(null);
    }

    @Override
    public List<Veterinario> findAll() {
        return new ArrayList<>(repo.findAll());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<Veterinario> veterinarioOpt = repo.findById(id);
        
        if (veterinarioOpt.isPresent()) {
            Veterinario veterinario = veterinarioOpt.get();
            
            // Desasociar los tratamientos del veterinario: Para cada tratamiento, establecer la referencia al veterinario como null
            List<Tratamiento> tratamientos = tratamientoRepo.findByVeterinarioId(id);
            for (Tratamiento tratamiento : tratamientos) {
                tratamiento.setVeterinario(null);
                tratamientoRepo.save(tratamiento);
            }
            
            // Eliminar el veterinario
            repo.delete(veterinario);
        }
    }

    @Override
    public void update(Veterinario veterinario) {
        if (veterinario != null && veterinario.getId() != null && repo.existsById(veterinario.getId())) {
            repo.save(veterinario);
        }
    }

    @Override
    public void add(Veterinario veterinario) {
        if (veterinario != null) {
            repo.save(veterinario);
        }
    }

    @Override
    public boolean validateLogin(String correo, String contrasena) {
        Veterinario veterinario = repo.findByCorreo(correo);
        return veterinario != null && veterinario.getContrasena().equals(contrasena);
    }

    @Override
    public Veterinario validateLoginAndGetVeterinario(String correo, String contrasena) {
        Veterinario veterinario = repo.findByCorreo(correo);
        if (veterinario != null && veterinario.getContrasena().equals(contrasena)) {
            return veterinario;
        }
        return null;
    }

    public Map<String, Object> getVeterinarioKPIs() {
        Map<String, Object> kpis = new HashMap<>();
        
        // Contar veterinarios activos e inactivos
        int veterinariosActivos = repo.countByEstado("Activo");
        int veterinariosInactivos = repo.countByEstado("Inactivo");

        kpis.put("veterinariosActivos", veterinariosActivos);
        kpis.put("veterinariosInactivos", veterinariosInactivos);

        return kpis;
    }
}
