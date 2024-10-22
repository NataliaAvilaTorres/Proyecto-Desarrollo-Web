package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.model.Veterinario;

@DataJpaTest
@RunWith(SpringRunner.class)
public class VeterinarioRepositoryTest {

    @Autowired
    private VeterinarioRepository veterinarioRepository;
     @Test
    public void VeterinarioRepositoryTest_save_Veterinario(){

        Veterinario veterinario = new Veterinario("100118790", "Vet. Juan", "cirujano","12345",
                                "juan@p.com", "Activo");
        
       Veterinario saveVeterinario = veterinarioRepository.save(veterinario);

        Assertions.assertThat(saveVeterinario).isNotNull();                     


    }

    @Test
    public void VeterinarioReporitoryTest_FindAll_NotEmptyList(){

        Veterinario veterinario = new Veterinario("100118790", "Vet. Juan", "cirujano","12345",
                                "juan@p.com", "Activo");
        
        Veterinario veterinario2 = new Veterinario("100118790", "Vet. Pedro", "cirujano","12345",
                                "juan@p.com", "Activo");
        
        
        veterinarioRepository.save(veterinario);
        veterinarioRepository.save(veterinario2);
        List<Veterinario> veterinarios = veterinarioRepository.findAll();


        Assertions.assertThat(veterinarios).isNotNull();
        Assertions.assertThat(veterinarios.size()).isEqualTo(2);
        Assertions.assertThat(veterinarios.size()).isGreaterThan(0);

    }

    @Test
    public void VeterinarioReporitoryTest_findById_FindWrongIndex(){

        Long index = -1l;

        Optional<Veterinario> veterinario =  veterinarioRepository.findById(index);

        Assertions.assertThat(veterinario).isEmpty();

    }

    @Test
    public void VeterinarioReporitoryTest_deleteById_EmptyVeterinario(){

        Long index = 2l;

        veterinarioRepository.deleteById(index);

        Assertions.assertThat(veterinarioRepository.findById(index)).isEmpty();
        
    }


    @Test
public void VeterinarioRepositoryTest_updateVeterinario(){

    // Guardamos un veterinario
    Veterinario veterinario = new Veterinario("100118790", "Vet. Juan", "cirujano", "12345",
                        "juan@p.com", "Activo");
    Veterinario saveVeterinario = veterinarioRepository.save(veterinario);

    // Act - Actualizamos el nombre del veterinario
    saveVeterinario.setNombre("Vet. Carlos");
    Veterinario updatedVeterinario = veterinarioRepository.save(saveVeterinario);

    // Assert - Verificamos que el nombre se haya actualizado
    Assertions.assertThat(updatedVeterinario.getNombre()).isEqualTo("Vet. Carlos");
}

    
    
}
