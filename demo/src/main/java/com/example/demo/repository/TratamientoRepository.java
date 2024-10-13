package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Tratamiento;
import java.util.List;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {
    // Método para obtener todos los tratamientos asociados a una mascota por su ID
    List<Tratamiento> findByMascotaId(Long mascotaId);

    // Método para obtener todos los tratamientos asociados a un veterinario por su ID
    List<Tratamiento> findByVeterinarioId(Long veterinarioId);

    // Método para contar tratamientos en el último mes
    int countByFechaAfter(LocalDate date);

    // Cantidad de tratamientos por tipo de medicamento en el último mes
    @Query("SELECT t.medicamento.nombre, COUNT(t) FROM Tratamiento t WHERE t.fecha >= :fecha GROUP BY t.medicamento.nombre")
    List<Object[]> countByMedicamentoAndFechaAfter(@Param("fecha") LocalDate fecha);


    // Suma total de las ventas (precio de venta de medicamentos)
    @Query("SELECT SUM(t.medicamento.precioVenta) FROM Tratamiento t")
    Double sumTotalVentas();

    // Suma total de las ganancias (precio venta - precio compra)
    @Query("SELECT SUM(t.medicamento.precioVenta - t.medicamento.precioCompra) FROM Tratamiento t")
    Double sumGanancias();

    // Top 3 tratamientos con más unidades vendidas
    @Query("SELECT t.medicamento.nombre, COUNT(t) FROM Tratamiento t GROUP BY t.medicamento.nombre ORDER BY COUNT(t) DESC")
    List<Object[]> findTop3ByUnidadesVendidas();
}
