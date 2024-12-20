package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Veterinario;

@Repository
public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {

    Veterinario findByCorreo(String correo);
    // Contar veterinarios por estado activo/inactivo
    int countByEstado(String estado);
    
 
}
