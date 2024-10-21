package com.example.demo.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.model.Mascota;

@DataJpaTest
@RunWith(SpringRunner.class)
public class MascotaReporitoryTest {

    @Autowired
    private MascotaRepository MascotaRepository;


    @BeforeEach
    public void init(){

        MascotaRepository.save(new Mascota( "Fido", "Labrador", 5, 25.0, "Alergia", "foto.jpg", "Activo"));
        MascotaRepository.save(new Mascota( "Fido2", "Golden Retriever", 8, 25.0, "Alergia", "foto.jpg", "Activo"));
        MascotaRepository.save(new Mascota( "Fido3", "Pug", 8, 25.0, "Alergia", "foto.jpg", "Activo"));
        MascotaRepository.save(new Mascota( "Fido4", "Beagle", 3, 25.0, "Alergia", "foto.jpg", "Activo"));

    }

    


    @Test
    public void MascotaReporitoryTest_save_Mascota(){
        //arrange
        //act
        //assert

        Mascota mascota = new Mascota( "Fido", "Labrador", 5, 25.0, "Alergia", "foto.jpg", "Activo");

       Mascota saveMascota = MascotaRepository.save(mascota);

        Assertions.assertThat(saveMascota).isNotNull();
    }


    @Test
    public void MascotaReporitoryTest_FindAll_NotEmptyList(){

        Mascota mascota = new Mascota( "Lucas", "Labrador", 5, 25.0, "Alergia", "foto.jpg", "Activo");
        Mascota mascota2 = new Mascota( "Fido", "Labrador", 5, 25.0, "Alergia", "foto.jpg", "Activo");

        MascotaRepository.save(mascota);
        MascotaRepository.save(mascota2);
        List<Mascota> mascotas = MascotaRepository.findAll();


        Assertions.assertThat(mascotas).isNotNull();
        Assertions.assertThat(mascotas.size()).isEqualTo(6);
        Assertions.assertThat(mascotas.size()).isGreaterThan(0);





    }




    
}
