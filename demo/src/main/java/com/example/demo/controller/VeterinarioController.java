package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VeterinarioController {

    // http://localhost:8090/veterinario/panel
    @GetMapping("/veterinario/panel")
    public String panelVeterinario() {
        return "panel_veterinario";
    }

}
