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
        Propietario propietario = propietarioService.findByCedula(mascota.getPropietario().getCedula());
        mascota.setPropietario(propietario);
        mascotaService.add(mascota);
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
