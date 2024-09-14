package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Propietario;

@Repository
public interface PropietarioRepository extends JpaRepository<Propietario, Long> {
    Propietario findByCedula(String cedula);
    Propietario findByCorreo(String correo);
}