package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Medicamento;
import com.example.demo.service.MedicamentoService;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    @Autowired
    private MedicamentoService medicamentoService;

    // Get all medicamentos
    @GetMapping("/")
    public List<Medicamento> getAllMedicamentos() {
        return new ArrayList<>(medicamentoService.findAll());
    }

    // Get a medicamento by its ID
    @GetMapping("/{id}")
    public Medicamento getMedicamentoById(@PathVariable("id") Long id) {
        return medicamentoService.findById(id);
    }

    // Create a new medicamento
    @PostMapping
    public Medicamento createMedicamento(@RequestBody Medicamento medicamento) {
        medicamentoService.add(medicamento);
        return medicamento;
    }

    // Update an existing medicamento by its ID
    @PutMapping("/{id}")
    public Medicamento updateMedicamento(@PathVariable("id") Long id, @RequestBody Medicamento medicamento) {
        medicamento.setId(id);
        medicamentoService.update(medicamento);
        return medicamento;
    }

    // Delete a medicamento by its ID
    @DeleteMapping("/{id}")
    public void deleteMedicamento(@PathVariable("id") Long id) {
        medicamentoService.deleteById(id);
    }
}
