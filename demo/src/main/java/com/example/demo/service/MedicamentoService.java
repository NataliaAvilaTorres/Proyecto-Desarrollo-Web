package com.example.demo.service;

import java.util.Collection;
import com.example.demo.model.Medicamento;

public interface MedicamentoService {

    public Medicamento findById(Long id);
    public Collection<Medicamento> findAll();
    public void deleteById(Long id);
    public void update(Medicamento medicamento);
    public void add(Medicamento medicamento);
}
