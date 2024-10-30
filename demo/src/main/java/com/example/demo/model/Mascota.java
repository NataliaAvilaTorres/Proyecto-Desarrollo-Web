package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;


@Entity
@Data
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String raza;
    private int edad;
    private double peso;
    private String enfermedad;
    private String fotoUrl;
    private String estado;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "propietario_id")
    @JsonIgnoreProperties("mascotas")  // Evita la recursión infinita ignorando la lista de mascotas de Propietario
    private Propietario propietario;

    @OneToMany(mappedBy = "mascota", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false)
    @JsonIgnoreProperties("mascota")  // Ignora la referencia a mascota en Tratamiento
    private List<Tratamiento> tratamientos = new ArrayList<>();


    // Constructor vacío
    public Mascota() {}

    // Constructor completo
    public Mascota(String nombre, String raza, int edad, double peso, String enfermedad, String fotoUrl, String estado) {
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.peso = peso;
        this.enfermedad = enfermedad;
        this.fotoUrl = fotoUrl;
        this.estado = estado;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
