// File: demo/src/main/java/com/example/demo/dto/UserResponseDTO.java
package com.example.demo.dto;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private Long id;
    private String cedula;
    private String nombre;
    private String correo;
    private String celular;
    private Set<String> roles;
}
