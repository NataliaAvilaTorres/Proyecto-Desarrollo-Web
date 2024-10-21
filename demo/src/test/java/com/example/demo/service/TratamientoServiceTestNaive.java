package com.example.demo.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.model.Mascota;
import com.example.demo.model.Medicamento;
import com.example.demo.model.Tratamiento;
import com.example.demo.model.Veterinario;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class TratamientoServiceTestNaive {

    @Autowired
    private TratamientoService tratamientoService;

    @Autowired
    private MascotaService mascotaService;

    @Autowired
    private VeterinarioService veterinarioService;

    @Autowired
    private MedicamentoService medicamentoService;

    @BeforeEach
    public void init() {

        Mascota mascota = new Mascota("Fido", "Labrador", 5, 25.0, "Alergia", "foto.jpg", "Activo");
        mascotaService.add(mascota);

        Veterinario veterinario = new Veterinario("12345678", "Dr. López", "Dermatología", "password123",
                "dr.lopez@example.com", "Activo");
        veterinarioService.add(veterinario);

        Medicamento medicamento = new Medicamento("Corticoide", 50.0f, 100.0f, 30, 5);
        medicamentoService.add(medicamento);

        // Tratamiento 1
        Tratamiento tratamiento1 = new Tratamiento(Date.valueOf("2024-10-15"), 2.0f);
        tratamiento1.setMascota(mascota);
        tratamiento1.setVeterinario(veterinario);
        tratamiento1.setMedicamento(medicamento);

        // Tratamiento 2
        Tratamiento tratamiento2 = new Tratamiento(Date.valueOf("2024-10-16"), 3.0f);
        tratamiento2.setMascota(mascota);
        tratamiento2.setVeterinario(veterinario);
        tratamiento2.setMedicamento(medicamento);

        tratamientoService.add(tratamiento1);
        tratamientoService.add(tratamiento2);
    }

    // 1. Prueba findById
    @Test
    public void TratamientoService_findById_Tratamiento() {

        // Arrange
        List<Tratamiento> allTratamientos = tratamientoService.findAll();
        Tratamiento tratamientoEsperado = allTratamientos.get(0); // Obtén el primer tratamiento
        Long idTratamiento = tratamientoEsperado.getId(); // Obtén su ID

        // Act
        Tratamiento tratamientoEncontrado = tratamientoService.findById(idTratamiento);

        // Assert
        assertThat(tratamientoEncontrado).isNotNull();
        assertThat(tratamientoEncontrado.getId()).isEqualTo(tratamientoEsperado.getId());

    }

    // 2. Prueba findAll
    @Test
    public void TratamientoService_findAll_TratamientoList() {

        // Arrange

        // act
        List<Tratamiento> allTratamiento = tratamientoService.findAll();

        // assert
        assertThat(allTratamiento).isNotNull();
        assertThat(allTratamiento.size()).isEqualTo(2);
    }

    // 3. Prueba deleteById
    @Test
    public void TratamientoService_deleteById_Tratamiento() {

        // Arrange
        List<Tratamiento> allTratamientos = tratamientoService.findAll();
        Tratamiento tratamientoAEliminar = allTratamientos.get(0); // Elige el primer tratamiento
        Long idTratamiento = tratamientoAEliminar.getId(); // Obtén el ID

        // Act
        tratamientoService.deleteById(idTratamiento);

        // Assert: Verificamos que el tratamiento ya no exista
        Tratamiento tratamientoEncontrado = tratamientoService.findById(idTratamiento);
        assertThat(tratamientoEncontrado).isNull(); // El tratamiento debería ser nulo tras la eliminación
    }

    // 4. Prueba update
    @Test
    public void TratamientoService_update_Tratamiento() {

        // Arrange
        List<Tratamiento> allTratamientos = tratamientoService.findAll();
        Tratamiento tratamientoActualizar = allTratamientos.get(0); // Seleccionamos el primer tratamiento
        Long idTratamiento = tratamientoActualizar.getId(); // Obtenemos su ID

        tratamientoActualizar.setCantidad(5.0f); // Actualizamos la cantidad

        // Act
        tratamientoService.update(tratamientoActualizar);

        // Assert
        Tratamiento tratamientoActualizado = tratamientoService.findById(idTratamiento);
        assertThat(tratamientoActualizado).isNotNull(); // Aseguramos que el tratamiento sigue existiendo
        assertThat(tratamientoActualizado.getCantidad()).isEqualTo(5.0f); // Verificamos que la cantidad fue actualizada
    }

    // 5. Prueba add
    @Test
    public void TratamientoService_add_Tratamiento() {

        // Arrange
        Mascota mascota = new Mascota("Max", "Golden Retriever", 3, 30.0, "Fiebre", "foto2.jpg", "Activo");
        mascotaService.add(mascota);

        Veterinario veterinario = new Veterinario("87654321", "Dra. Fernández", "Cardiología", "password456",
                "dra.fernandez@example.com", "Activo");
        veterinarioService.add(veterinario);

        Medicamento medicamento = new Medicamento("Ibuprofeno", 60.0f, 120.0f, 50, 10);
        medicamentoService.add(medicamento);

        Tratamiento nuevoTratamiento = new Tratamiento(Date.valueOf("2024-10-20"), 1.5f);
        nuevoTratamiento.setMascota(mascota);
        nuevoTratamiento.setVeterinario(veterinario);
        nuevoTratamiento.setMedicamento(medicamento);

        // Act
        tratamientoService.add(nuevoTratamiento);

        // Assert: Verificar que el tratamiento fue agregado correctamente
        List<Tratamiento> allTratamientos = tratamientoService.findAll();
        assertThat(allTratamientos).isNotNull();
        assertThat(allTratamientos.size()).isEqualTo(3); // Verificamos que ahora hay 3 tratamientos
    }

    // 6. Prueba findByVeterinarioId
    @Test
    public void TratamientoService_findByVeterinarioId_TratamientoList() {

        // Arrange
        Veterinario veterinario = veterinarioService.findAll().get(0); // Veterinario creado en init()
        Long veterinarioId = veterinario.getId();

        // Act
        List<Tratamiento> tratamientos = tratamientoService.findByVeterinarioId(veterinarioId);

        // Assert
        assertThat(tratamientos).isNotNull();
        assertThat(tratamientos.size()).isEqualTo(2); // Deberían haber dos tratamientos creados en init() para este
                                                      // veterinario

    }

    // 7. Prueba countByFechaAfter
    @Test
    public void TratamientoService_countByFechaAfter_TratamientoCount() {

        // Arrange
        LocalDate fechaReferencia = LocalDate.of(2024, 10, 14); // Fecha anterior a las fechas en los tratamientos

        // Act
        int count = tratamientoService.countByFechaAfter(fechaReferencia);

        // Assert
        assertThat(count).isEqualTo(2); // Hay 2 tratamientos después del 14 de octubre de 2024
    }

    // 8. Prueba countByMedicamentoAndFechaAfter
    @Test
    public void TratamientoService_countByMedicamentoAndFechaAfter_TratamientoList() {
        
        // Arrange
        LocalDate fecha = LocalDate.of(2024, 10, 15); // Establecer la fecha límite
        Medicamento medicamento2 = new Medicamento("Antibiótico", 100.0f, 200.0f, 20, 10);
        medicamentoService.add(medicamento2);

        Mascota mascota = mascotaService.findAll().get(0); // Reutilizamos la mascota creada en init()
        Veterinario veterinario = veterinarioService.findAll().get(0); // Reutilizamos el veterinario creado en init()

        // Crear tratamientos adicionales después de la fecha límite
        Tratamiento tratamiento3 = new Tratamiento(Date.valueOf("2024-10-17"), 4.0f);
        tratamiento3.setMascota(mascota);
        tratamiento3.setVeterinario(veterinario);
        tratamiento3.setMedicamento(medicamento2); // Usar el segundo medicamento

        Tratamiento tratamiento4 = new Tratamiento(Date.valueOf("2024-10-18"), 2.0f);
        tratamiento4.setMascota(mascota);
        tratamiento4.setVeterinario(veterinario);
        tratamiento4.setMedicamento(medicamento2);

        tratamientoService.add(tratamiento3);
        tratamientoService.add(tratamiento4);

        // Act
        List<Object[]> resultado = tratamientoService.countByMedicamentoAndFechaAfter(fecha);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.size()).isEqualTo(2); // Debe haber 2 tipos de medicamentos

        // Verificar el medicamento 1
        Object[] medicamento1Result = resultado.get(0);
        assertThat(medicamento1Result[0]).isEqualTo("Antibiótico");
        assertThat((Long) medicamento1Result[1]).isEqualTo(2L); // Dos tratamientos

        // Verificar el medicamento 2
        Object[] medicamento2Result = resultado.get(1);
        assertThat(medicamento2Result[0]).isEqualTo("Corticoide");
        assertThat((Long) medicamento2Result[1]).isEqualTo(2L); // Dos tratamientos
    }

    // 9. Prueba findByMascotaId
    @Test
    public void TratamientoService_findByMascotaId_TratamientoList() {

        // Arrange
        Mascota mascota = mascotaService.findAll().get(0); // Usamos la mascota creada en init()
        Long mascotaId = mascota.getId(); // Obtenemos el ID de la mascota

        // Act
        List<Tratamiento> tratamientos = tratamientoService.findByMascotaId(mascotaId);

        // Assert
        assertThat(tratamientos).isNotNull();
        assertThat(tratamientos.size()).isEqualTo(2); // Deberían ser 2 tratamientos para esta mascota
        assertThat(tratamientos.get(0).getMascota().getId()).isEqualTo(mascotaId); // Verificamos que la mascota es la
                                                                                   // correcta
        assertThat(tratamientos.get(1).getMascota().getId()).isEqualTo(mascotaId); // Verificamos que la mascota es la
                                                                                   // correcta
    }

    // 10. Prueba findByCantidadGreaterThan
    @Test
    public void TratamientoService_findByCantidadGreaterThan_TratamientoList() {

        // Arrange
        Float cantidadUmbral = 2.0f; // Establecemos un umbral de cantidad

        // Act
        List<Tratamiento> tratamientos = tratamientoService.findByCantidadGreaterThan(cantidadUmbral);

        // Assert
        assertThat(tratamientos).isNotNull();
        assertThat(tratamientos.size()).isEqualTo(1); // Debería devolver solo 1 tratamiento (3.0f)

        // Verificamos que el tratamiento devuelto es el correcto
        assertThat(tratamientos.get(0).getCantidad()).isGreaterThan(cantidadUmbral);
    }
}