// File: demo/src/main/java/com/example/demo/model/Role.java
package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name; // Ejemplo: ROLE_ADMIN, ROLE_VETERINARIO, ROLE_PROPETARIO
}
