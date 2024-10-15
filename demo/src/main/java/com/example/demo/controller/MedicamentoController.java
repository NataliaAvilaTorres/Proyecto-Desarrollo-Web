package com.example.demo.controller;

import com.example.demo.model.Medicamento;
import com.example.demo.model.Tratamiento;
import com.example.demo.service.MedicamentoService;
import com.example.demo.service.TratamientoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    @Autowired
    private MedicamentoService medicamentoService;

    @Autowired
    private TratamientoService tratamientoService;

    // Obtener todos los medicamentos
    @GetMapping("/")
    public ResponseEntity<List<Medicamento>> getAllMedicamentos() {
        System.out.println("Obteniendo todos los medicamentos...");
        List<Medicamento> medicamentos = List.copyOf(medicamentoService.findAll());
        System.out.println("Medicamentos encontrados: " + medicamentos.size());
        return ResponseEntity.ok(medicamentos);
    }

    // Obtener un medicamento por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Medicamento> getMedicamentoById(@PathVariable Long id) {
        System.out.println("Obteniendo medicamento con ID: " + id);
        Medicamento medicamento = medicamentoService.findById(id);
        if (medicamento != null) {
            return ResponseEntity.ok(medicamento);
        } else {
            System.err.println("Medicamento no encontrado.");
            return ResponseEntity.notFound().build();
        }
    }

    // Crear un tratamiento (asociado a un medicamento)
    @PostMapping("/tratamiento")
    public ResponseEntity<Tratamiento> createTratamiento(@RequestBody Tratamiento tratamiento) {
        System.out.println("Tratamiento recibido: " + tratamiento);
        Tratamiento nuevoTratamiento = tratamientoService.add(tratamiento);
        if (nuevoTratamiento != null) {
            System.out.println("Tratamiento creado: " + nuevoTratamiento);
            return ResponseEntity.ok(nuevoTratamiento);
        } else {
            System.err.println("Error al crear el tratamiento.");
            return ResponseEntity.status(500).build();
        }
    }

    // Actualizar un medicamento por su ID
    @PutMapping("/{id}")
    public ResponseEntity<Medicamento> updateMedicamento(
            @PathVariable Long id, @RequestBody Medicamento medicamento) {
        System.out.println("Medicamento recibido para actualizar: " + medicamento);
        medicamento.setId(id);
        Medicamento updatedMedicamento = medicamentoService.update(medicamento);
        if (updatedMedicamento != null) {
            System.out.println("Medicamento actualizado: " + updatedMedicamento);
            return ResponseEntity.ok(updatedMedicamento);
        } else {
            System.err.println("Error al actualizar el medicamento.");
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un medicamento por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicamento(@PathVariable Long id) {
        System.out.println("Eliminando medicamento con ID: " + id);
        medicamentoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
