package com.example.demo.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
public class Tratamiento {

    @Id
    @GeneratedValue
    private Long id;

    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "id_mascota", nullable = true)
    @JsonIgnoreProperties("tratamientos")  // Ignora la lista de tratamientos en Mascota
    private Mascota mascota;



    @ManyToOne
    @JoinColumn(name = "id_veterinario", nullable = true)
    private Veterinario veterinario;

    @ManyToOne
    @JoinColumn(name = "id_medicamento", nullable = true)
    @JsonIgnoreProperties("tratamientos")  // Ignora la lista de tratamientos en Medicamento
    private Medicamento medicamento;
  // No se ignora porque solo queremos mostrar este medicamento


    // Nuevo campo para almacenar la cantidad de medicamento
    private float cantidad;  // Aquí almacenamos la cantidad asignada del medicamento

  

    public Tratamiento(Date fecha, float cantidad) {
        this.fecha = fecha;
        this.cantidad = cantidad;
    }

    
}
