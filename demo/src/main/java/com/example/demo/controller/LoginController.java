package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Login;
import com.example.demo.model.Propietario;
import com.example.demo.model.Veterinario;
import com.example.demo.service.PropietarioService;
import com.example.demo.service.VeterinarioService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allow cross-origin requests from any origin
public class LoginController {

    @Autowired
    private PropietarioService propietarioService;

    @Autowired
    private VeterinarioService veterinarioService;

    @PostMapping("/login")
    public Object login(@RequestBody Login loginRequest) {
        String correo = loginRequest.getCorreo();
        String contrasena = loginRequest.getContrasena();
        String role = loginRequest.getRole();

        if ("veterinario".equals(role)) {
            Veterinario veterinario = veterinarioService.validateLoginAndGetVeterinario(correo, contrasena);
            if (veterinario != null) {
                return veterinario; // Return the authenticated Veterinario object
            }
        } else if ("dueno".equals(role)) {
            Propietario propietario = propietarioService.validateLoginAndGetPropietario(correo, contrasena);
            if (propietario != null) {
                return propietario; // Return the authenticated Propietario object
            }
        }

        // Return error response in case of failed authentication
        return new ErrorResponse("Credenciales incorrectas.");
    }

    // ErrorResponse class to handle error messages
    class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }
}
