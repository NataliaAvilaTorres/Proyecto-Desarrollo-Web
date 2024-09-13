package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Propietario;
import com.example.demo.service.PropietarioService;

@Controller
@RequestMapping("/propietario")
public class PropietarioController {

    @Autowired
    PropietarioService propietarioService;

    //http://localhost:8090/propietario/addPropietario
    @GetMapping("/addPropietario")
    public String mostrarFormularioPet(Model model) {
        Propietario pro = new Propietario("", "", "", "", "");
        model.addAttribute("propietario", pro);
        return "propietario_form";
    }

    @PostMapping("/agregarPropietario")
    public String agregarMascota(@ModelAttribute("propietario") Propietario pro) {
        propietarioService.add(pro);
        return "redirect:/veterinario/panel"; //Toca cambiar esto
    }

    //http://localhost:8090/propietario/listPropietarios
    @GetMapping("/listPropietarios")
    public String mostrarPropietarios(Model model) {
        
        model.addAttribute("propietarios", propietarioService.findAll());
        return "propietario_list";
    }

    @GetMapping("/update/{id}")
    public String mostrarFormulario(@PathVariable("id") Long identificacion, Model model) {
        model.addAttribute("propietario", propietarioService.findById(identificacion));
        return "propietario_modificar";
    }

    @PostMapping("/update/{id}")
    public String updatePropietario(@PathVariable("id") Long identificacion, @ModelAttribute("propietario") Propietario propietario) {

        propietarioService.update(propietario);
        return "redirect:/propietario/listPropietarios";
    }

    @GetMapping("/find/{id}")
    public String mostrarInfoPropietario(Model model, @PathVariable("id") Long identificacion) {
        Propietario propietario = propietarioService.findById(identificacion);
        if (propietario != null) {
            model.addAttribute("propietario", propietario);
        } else {

        }
        return "propietario_details";
    }

    @GetMapping("/delete/{id}")
    public String borrarPropietario(@PathVariable("id") Long identificacion) {
        propietarioService.deleteById(identificacion);
        return "redirect:/propietario/listPropietarios";
    }

    
}