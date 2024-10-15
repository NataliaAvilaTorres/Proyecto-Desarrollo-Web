package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Medicamento;
import com.example.demo.repository.MedicamentoRepository;

@Service
public class MedicamentoServiceImpl implements MedicamentoService {

    @Autowired
    MedicamentoRepository repo;

    @Override
    public Medicamento findById(Long id) {
        Optional<Medicamento> medicamentoOpt = repo.findById(id);
        return medicamentoOpt.orElse(null);
    }

    @Override
    public List<Medicamento> findAll() {
        return new ArrayList<>(repo.findAll());

    }

    @Override
    public void deleteById(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        }
    }

    @Override
    public void update(Medicamento medicamento) {
        if (medicamento != null && medicamento.getId() != null && repo.existsById(medicamento.getId())) {
            repo.save(medicamento);
        }
    }

    @Override
    public void add(Medicamento medicamento) {
        if (medicamento != null) {
            repo.save(medicamento);
        }
    }
}