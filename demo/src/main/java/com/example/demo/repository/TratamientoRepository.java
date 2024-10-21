package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Tratamiento;
import java.util.List;
import java.sql.Date;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {

    List<Tratamiento> findByMascotaId(Long mascotaId); // Método para obtener todos los tratamientos asociados a una mascota por su ID
    List<Tratamiento> findByVeterinarioId(Long veterinarioId);   // Método para obtener todos los tratamientos asociados a un veterinario por su ID
    int countByFechaAfter(LocalDate date); // Método para contar tratamientos en el último mes
    List<Tratamiento> findByFecha(Date fecha); //Encontrar un tratamiento por fecha
    List<Tratamiento> findByCantidadGreaterThan(Float cantidad);

    // Cantidad de tratamientos por tipo de medicamento en el último mes
    @Query("SELECT t.medicamento.nombre, COUNT(t) FROM Tratamiento t WHERE t.fecha >= :fecha GROUP BY t.medicamento.nombre")
    List<Object[]> countByMedicamentoAndFechaAfter(@Param("fecha") LocalDate fecha);

    // Suma total de las ventas (precio de venta de medicamentos)
    @Query("SELECT SUM(t.medicamento.precioVenta * t.medicamento.unidadesVendidas) FROM Tratamiento t")
    Double sumTotalVentas();


    // Suma total de las ganancias (precio venta - precio compra) por unidades vendidas
    @Query("SELECT SUM((t.medicamento.precioVenta - t.medicamento.precioCompra) * t.medicamento.unidadesVendidas) FROM Tratamiento t")
    Double sumGanancias();

    // Top 3 tratamientos con más unidades vendidas
    @Query("SELECT t.medicamento.nombre, COUNT(t) AS unidades FROM Tratamiento t " +
            "GROUP BY t.medicamento.nombre ORDER BY unidades DESC")
    List<Object[]> findTop3ByUnidadesVendidas(Pageable pageable);
}