package com.example.demo.service;

import com.example.demo.model.Medicamento;
import java.util.Collection;

public interface MedicamentoService {

    Medicamento findById(Long id);
    Collection<Medicamento> findAll();
    void deleteById(Long id);
    Medicamento update(Long id, Medicamento medicamento);  // Aquí añadimos el ID
    void add(Medicamento medicamento);
}
