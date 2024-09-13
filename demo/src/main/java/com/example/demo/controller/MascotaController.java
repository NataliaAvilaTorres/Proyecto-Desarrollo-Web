package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Mascota;
import com.example.demo.service.MascotaService;


@Controller
@RequestMapping("/veterinario")
public class MascotaController {

    @Autowired
    MascotaService mascotaService;

    //http://localhost:8090/veterinario/panel
    @GetMapping("/panel")
    public String panelVeterinario() {
        return "panel_veterinario";
    }

    //http://localhost:8090/veterinario/addMascota
    @GetMapping("/addMascota")
    public String mostrarFormularioPet(Model model) {
        Mascota pet = new Mascota("", "", 0, 0, "", "", "");
        model.addAttribute("mascota", pet);
        return "pet_form";
    }

    @PostMapping("/agregarMascota")
    public String agregarMascota(@ModelAttribute("mascota") Mascota mascota) {
        mascotaService.add(mascota);
        return "redirect:/veterinario/panel"; //Toca cambiar esto
    }
    
}
