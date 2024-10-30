package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Veterinario {

    @Id
    @GeneratedValue
    private Long id;

    private String cedula;
    private String nombre;
    private String especialidad;
    private String contrasena;
    private String correo;
    private String estado;

    

    public Veterinario(String cedula, String nombre, String especialidad, String contrasena, String correo, String estado) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.contrasena = contrasena;
        this.correo = correo;
        this.estado = estado;
    }

    
    
}
