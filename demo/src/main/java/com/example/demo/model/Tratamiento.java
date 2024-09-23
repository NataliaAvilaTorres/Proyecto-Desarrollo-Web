package com.example.demo.model;

import java.sql.Date;
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
    @JoinColumn(name = "id_mascota", nullable = false) // Cambiamos a usar el id de mascota
    private Mascota mascota;

    @ManyToOne
    @JoinColumn(name = "id_veterinario", nullable = false) // Cambiamos a usar el id de veterinario (cedula podría funcionar pero id es más estándar)
    private Veterinario veterinario;

    @ManyToOne
    @JoinColumn(name = "id_medicamento", nullable = false) // Cambiamos a usar el id de medicamento, no el nombre
    private Medicamento medicamento;

    public Tratamiento() {
    }

    public Tratamiento(Date fecha) {
        this.fecha = fecha;
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
