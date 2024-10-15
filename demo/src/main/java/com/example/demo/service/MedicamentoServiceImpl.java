package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Medicamento;
import com.example.demo.repository.MedicamentoRepository;

@Service
public class MedicamentoServiceImpl implements MedicamentoService {

    @Autowired
    private MedicamentoRepository repo;

    /**
     * Encuentra un medicamento por su ID.
     */
    @Override
    public Medicamento findById(Long id) {
        Optional<Medicamento> medicamentoOpt = repo.findById(id);
        return medicamentoOpt.orElse(null);
    }

    /**
     * Devuelve una lista de todos los medicamentos.
     */
    @Override
    public List<Medicamento> findAll() {
        return new ArrayList<>(repo.findAll());
    }

    /**
     * Elimina un medicamento por su ID, si existe.
     */
    @Override
    public void deleteById(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new IllegalArgumentException("El medicamento con ID " + id + " no existe.");
        }
    }

    /**
     * Actualiza un medicamento, gestionando correctamente la lista de tratamientos.
     */
    @Override
    @Transactional  // Garantiza la consistencia dentro de una transacción
    public Medicamento update(Long id, Medicamento medicamento) {
        Medicamento existingMedicamento = findById(id);
        
        if (existingMedicamento == null) {
            throw new IllegalArgumentException("No se encontró el medicamento con ID " + id);
        }

        // Limpia los tratamientos existentes para evitar inconsistencias
        existingMedicamento.getTratamientos().clear();

        // Si hay tratamientos nuevos, asignarlos y establecer la relación bidireccional
        if (medicamento.getTratamientos() != null) {
            medicamento.getTratamientos().forEach(tratamiento -> {
                tratamiento.setMedicamento(existingMedicamento);
            });
            existingMedicamento.getTratamientos().addAll(medicamento.getTratamientos());
        }

        // Actualiza las demás propiedades del medicamento
        existingMedicamento.setNombre(medicamento.getNombre());
        existingMedicamento.setPrecioCompra(medicamento.getPrecioCompra());
        existingMedicamento.setPrecioVenta(medicamento.getPrecioVenta());
        existingMedicamento.setUnidadesDisponibles(medicamento.getUnidadesDisponibles());
        existingMedicamento.setUnidadesVendidas(medicamento.getUnidadesVendidas());

        // Guarda los cambios en la base de datos
        return repo.save(existingMedicamento);
    }

    /**
     * Añade un nuevo medicamento a la base de datos.
     */
    @Override
    public void add(Medicamento medicamento) {
        if (medicamento != null) {
            repo.save(medicamento);
        } else {
            throw new IllegalArgumentException("El medicamento no puede ser nulo.");
        }
    }
}
