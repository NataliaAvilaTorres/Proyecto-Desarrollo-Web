package com.example.demo.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
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

    public Tratamiento() {
    }

    public Tratamiento(Date fecha, float cantidad) {
        this.fecha = fecha;
        this.cantidad = cantidad;
    }

    // Getters y setters para cantidad
    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }
}
