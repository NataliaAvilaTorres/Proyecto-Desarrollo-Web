package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Login;
import com.example.demo.service.PropietarioService;
import com.example.demo.service.VeterinarioService;

@Controller
public class LoginController {

    @Autowired
    private PropietarioService propietarioService;

    @Autowired
    private VeterinarioService veterinarioService;

    // http://localhost:8090/
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // http://localhost:8090/login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // http://localhost:8090/logout
    @GetMapping("/logout")
    public String cerrarSesion() {
        return "index";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Login loginRequest, Model model) {
        String correo = loginRequest.getCorreo();
        String contrasena = loginRequest.getContrasena();
        String role = loginRequest.getRole();

        boolean isAuthenticated = false;

        if ("veterinario".equals(role)) {
            isAuthenticated = veterinarioService.validateLogin(correo, contrasena);
            if (isAuthenticated) {
                return "redirect:/veterinario/panel";
            }
        } else if ("dueno".equals(role)) {
            isAuthenticated = propietarioService.validateLogin(correo, contrasena);
            if (isAuthenticated) {
                return "redirect:/propietario/panel";
            }
        }

        // Si no está autenticado, redirige a la página de inicio de sesión con un
        // mensaje de error.
        model.addAttribute("error", "Credenciales incorrectas.");
        return "login"; // Redirige de nuevo a la página de inicio de sesión.
    }

}
