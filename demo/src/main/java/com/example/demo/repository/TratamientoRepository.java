package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Tratamiento;
import java.util.List;

public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {
    // Método para obtener todos los tratamientos asociados a una mascota por su ID
    List<Tratamiento> findByMascotaId(Long mascotaId);
}
