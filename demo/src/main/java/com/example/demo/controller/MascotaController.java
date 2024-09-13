package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Mascota;
import com.example.demo.service.MascotaService;

@Controller
@RequestMapping("/pet")
public class MascotaController {

    @Autowired
    MascotaService mascotaService;

    // http://localhost:8090/veterinario/addMascota
    @GetMapping("/addMascota")
    public String mostrarFormularioPet(Model model) {
        Mascota pet = new Mascota("", "", 0, 0, "", "", "");
        model.addAttribute("mascota", pet);
        return "pet_form";
    }

    @PostMapping("/agregarMascota")
    public String agregarMascota(@ModelAttribute("mascota") Mascota mascota) {
        mascotaService.add(mascota);
        return "redirect:/veterinario/panel"; // Toca cambiar esto
    }

    @GetMapping("/listMascotas")
    public String mostrarMascotas(Model model) {

        model.addAttribute("mascotas", mascotaService.findAll());
        return "pets_list";
    }

    @GetMapping("/update/{id}")
    public String mostrarFormulario(@PathVariable("id") Long identificacion, Model model) {
        model.addAttribute("mascota", mascotaService.findById(identificacion));
        return "pet_modificar";
    }

    @PostMapping("/update/{id}")
    public String updateMascpta(@PathVariable("id") Long identificacion, @ModelAttribute("mascota") Mascota mascota) {

        mascotaService.update(mascota);
        return "redirect:/veterinario/panel";
    }

    @GetMapping("/find/{id}")
    public String mostrarInfoMascota(Model model, @PathVariable("id") Long identificacion) {
        Mascota mascota = mascotaService.findById(identificacion);
        if (mascota != null) {
            model.addAttribute("mascota", mascota);
        } else {

        }
        return "pet_details";
    }

    @GetMapping("/delete/{id}")
    public String borrarMascota(@PathVariable("id") Long identificacion) {
        mascotaService.deleteById(identificacion);
        return "redirect:/pet/listMascotas";
    }

}