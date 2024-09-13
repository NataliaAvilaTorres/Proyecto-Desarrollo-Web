package com.example.demo.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;

import com.example.demo.repository.MascotaRepository;
import com.example.demo.repository.PropietarioRepository;

import jakarta.transaction.Transactional;

@Controller
@Transactional
public class DataBaseInit implements ApplicationRunner {

    @Autowired
    MascotaRepository mascotaRepository;

    @Autowired
    PropietarioRepository propietarioRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
    // Crea los propietarios
    Propietario luis = propietarioRepository.save(new Propietario("123456789", "Luis", "perez@p.com", "1234", "1234"));
    Propietario pedro = propietarioRepository.save(new Propietario("1013456789", "Pedro", "pedro@p.com", "1234", "1234"));
    Propietario ana = propietarioRepository.save(new Propietario("108456786", "Ana", "ana@p.com", "1234", "1234"));

    // Crea las mascotas y asigna el propietario correspondiente
    Mascota coco = new Mascota("Coco", "Cachorro", 3, 1, "Gato", "foto", "Cafe");
    coco.setPropietario(luis); // Asigna a Luis como propietario de Coco
    mascotaRepository.save(coco);

    Mascota nala = new Mascota("Nala", "Cachorro", 4, 5, "Perro", "foto", "negro");
    nala.setPropietario(pedro); // Asigna a Pedro como propietario de Nala
    mascotaRepository.save(nala);

    Mascota luna = new Mascota("Luna", "Cachorro", 9, 10, "Gato", "foto", "manchas");
    luna.setPropietario(ana); // Asigna a Ana como propietaria de Luna
    mascotaRepository.save(luna);
    }
    
}
