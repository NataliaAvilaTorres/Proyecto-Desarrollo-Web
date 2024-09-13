package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccesoController {

    //http://localhost:8090/
    @GetMapping("/")
    public String home() {
        return "index";
    }

    //http://localhost:8090/login
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    //http://localhost:8090/veterinario/panel
    @GetMapping("/veterinario/panel")
    public String panelVeterinario() {
        return "panel_veterinario";
    }

    //http://localhost:8090/logout
    @GetMapping("/logout")
    public String cerrarSesion() {
        return "index";
    }
}
