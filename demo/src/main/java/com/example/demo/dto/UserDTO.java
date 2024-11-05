// File: demo/src/main/java/com/example/demo/dto/UserDTO.java
package com.example.demo.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private String cedula;
    private String nombre;
    private String correo;
    private String contrasena;
    private String celular;
    private String role; // Por ejemplo: ADMIN, VETERINARIO, PROPIETARIO
}
