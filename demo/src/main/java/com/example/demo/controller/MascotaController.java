package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Mascota;
import com.example.demo.model.Propietario;
import com.example.demo.service.MascotaService;
import com.example.demo.service.PropietarioService;
import com.example.demo.model.Tratamiento;


import java.util.List;
import java.util.ArrayList; // Importación añadida

@RestController
@RequestMapping("/api/mascotas")
@CrossOrigin(origins = "http://localhost:4200")
public class MascotaController {

    @Autowired
    MascotaService mascotaService;

    @Autowired
    PropietarioService propietarioService;

    @GetMapping("/")
    public List<Mascota> getAllMascotas() {
        return new ArrayList<>(mascotaService.findAll());
    }

    @GetMapping("/{id}")
    public Mascota getMascotaById(@PathVariable("id") Long id) {
        return mascotaService.findById(id);
    }

    @PostMapping("/")
    public Mascota createMascota(@RequestBody Mascota mascota) {
        System.out.println("Recibiendo datos de la mascota: ");
        System.out.println("Nombre: " + mascota.getNombre());
        System.out.println("Raza: " + mascota.getRaza());
        System.out.println("Edad: " + mascota.getEdad());
        System.out.println("Peso: " + mascota.getPeso());
        System.out.println("Enfermedad: " + mascota.getEnfermedad());
        System.out.println("Foto URL: " + mascota.getFotoUrl());
        System.out.println("Estado: " + mascota.getEstado());
    
        // Verificar que el propietario existe en la solicitud
        if (mascota.getPropietario() != null && mascota.getPropietario().getCedula() != null) {
            // Imprimir los datos del propietario
            System.out.println("Buscando propietario con cédula: " + mascota.getPropietario().getCedula());
    
            // Buscar propietario en la base de datos usando la cédula
            Propietario propietario = propietarioService.findByCedula(mascota.getPropietario().getCedula());
    
            // Si no se encuentra el propietario, lanzar una excepción
            if (propietario == null) {
                System.out.println("Propietario no encontrado");
                throw new RuntimeException("Propietario no encontrado con la cédula: " + mascota.getPropietario().getCedula());
            }
    
            // Asignar el propietario encontrado a la mascota
            mascota.setPropietario(propietario);
        } else {
            System.out.println("Propietario no válido o cédula no proporcionada");
            throw new RuntimeException("Propietario no válido o cédula no proporcionada");
        }
    
        // Guardar la mascota
        mascotaService.add(mascota);
        System.out.println("Mascota guardada con éxito");
    
        return mascota;
    }


    @PutMapping("/{id}")
    public Mascota updateMascota(@PathVariable("id") Long id, @RequestBody Mascota mascota) {
        mascota.setId(id);
        mascotaService.update(mascota);
        return mascota;
    }

    @DeleteMapping("/{id}")
    public void deleteMascota(@PathVariable("id") Long id) {
        mascotaService.deleteById(id);
    }

    @GetMapping("/{id}/historial")
    public List<Tratamiento> getHistorialMedico(@PathVariable("id") Long id) {
        Mascota mascota = mascotaService.findById(id);
        if (mascota != null) {
            return mascota.getTratamientos();  // Devuelve los tratamientos
        } else {
            return null;  // Manejar el caso de null de mejor manera si es necesario
        }
    }

}
