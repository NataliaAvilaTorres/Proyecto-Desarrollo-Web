package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Administrador;
import com.example.demo.repository.AdministradorRepository;
import com.example.demo.repository.MascotaRepository;
import com.example.demo.repository.TratamientoRepository;
import com.example.demo.repository.VeterinarioRepository;

@Service
public class AdministradorServiceImpl implements AdministradorService {

    @Autowired
    AdministradorRepository repo;

    @Autowired
    private TratamientoRepository tratamientoRepository;

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    @Override
    public Administrador findById(Long id) {
        Optional<Administrador> adminOpt = repo.findById(id);
        return adminOpt.orElse(null);
    }

    @Override
    public List<Administrador> findAll() {
        return new ArrayList<>(repo.findAll());
    }

    @Override
    public void deleteById(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        }
    }

    @Override
    public void update(Administrador administrador) {
        if (administrador != null && administrador.getId() != null && repo.existsById(administrador.getId())) {
            repo.save(administrador);
        }
    }

    @Override
    public void add(Administrador administrador) {
        if (administrador != null) {
            repo.save(administrador);
        }
    }

    @Override
    public Map<String, Object> getDashboardKPIs() {
        Map<String, Object> kpis = new HashMap<>();
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);

        try {
            // 1. Total tratamientos del último mes
            int totalTratamientos = tratamientoRepository.countByFechaAfter(oneMonthAgo);
            kpis.put("totalTratamientos", totalTratamientos);

            // 2. Tratamientos por medicamento
            List<Object[]> tratamientosPorMedicamento = tratamientoRepository.countByMedicamentoAndFechaAfter(oneMonthAgo);
            kpis.put("tratamientosPorMedicamento", tratamientosPorMedicamento);

            // 3. Veterinarios activos/inactivos
            //int veterinariosActivos = veterinarioRepository.countByActivo(true);
            //int veterinariosInactivos = veterinarioRepository.countByActivo(false);
            //kpis.put("veterinariosActivos", veterinariosActivos);
            //kpis.put("veterinariosInactivos", veterinariosInactivos);

            // 4. Mascotas totales y activas
            long totalMascotas = mascotaRepository.count();
            long mascotasActivas = mascotaRepository.countByEstado("Activo");  // Usamos el estado "Activo"
            kpis.put("totalMascotas", totalMascotas);
            kpis.put("mascotasActivas", mascotasActivas);

            // 5. Ventas y ganancias
            Double ventasTotales = tratamientoRepository.sumTotalVentas();
            Double gananciasTotales = tratamientoRepository.sumGanancias();
            kpis.put("ventasTotales", ventasTotales != null ? ventasTotales : 0.0);
            kpis.put("gananciasTotales", gananciasTotales != null ? gananciasTotales : 0.0);

            // 6. Top 3 tratamientos con más unidades vendidas
            Pageable top3 = PageRequest.of(0, 3);  // Página 0, 3 elementos por página
            List<Object[]> topTratamientos = tratamientoRepository.findTop3ByUnidadesVendidas(top3);
            kpis.put("topTratamientos", topTratamientos);

        } catch (Exception e) {
            kpis.put("error", "Ocurrió un error al obtener los KPIs: " + e.getMessage());
        }

        return kpis;
    }


}