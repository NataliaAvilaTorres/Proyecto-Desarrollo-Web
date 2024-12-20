package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Mascota;
import java.util.List; 

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    // Contar mascotas por estado
    long countByEstado(String estado);
    // Opcional: Buscar todas las mascotas activas (por si acaso)
    List<Mascota> findByEstado(String estado);
}
