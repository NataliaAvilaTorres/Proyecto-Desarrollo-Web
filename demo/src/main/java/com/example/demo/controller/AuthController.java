// File: demo/src/main/java/com/example/demo/controller/AuthController.java
package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.model.Role;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;
    
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private RoleRepository roleRepo;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO loginRequest) {
        try {
            Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getCorreo(), loginRequest.getContrasena()
                )
            );
            
            UserEntity user = userRepo.findByCorreo(loginRequest.getCorreo());
            String token = jwtUtil.generateToken(user);
            
            UserResponseDTO response = UserResponseDTO.builder()
                .id(user.getId())
                .cedula(user.getCedula())
                .nombre(user.getNombre())
                .correo(user.getCorreo())
                .celular(user.getCelular())
                .roles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
                .build();
            
            return ResponseEntity.ok(new AuthResponse(token, response));
            
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body("Credenciales incorrectas.");
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        if (userRepo.findByCorreo(userDTO.getCorreo()) != null) {
            return ResponseEntity.badRequest().body("El correo ya está registrado.");
        }
        
        Role role = roleRepo.findByName("ROLE_" + userDTO.getRole().toUpperCase());
        if (role == null) {
            return ResponseEntity.badRequest().body("Rol inválido.");
        }
        
        UserEntity user = UserEntity.builder()
            .cedula(userDTO.getCedula())
            .nombre(userDTO.getNombre())
            .correo(userDTO.getCorreo())
            .contrasena(passwordEncoder.encode(userDTO.getContrasena()))
            .celular(userDTO.getCelular())
            .roles(Set.of(role))
            .build();
        
        userRepo.save(user);
        
        return ResponseEntity.ok("Usuario registrado exitosamente.");
    }
    
    // Clase interna para la respuesta de autenticación
    @Data
    @AllArgsConstructor
    class AuthResponse {
        private String token;
        private UserResponseDTO user;
    }
}
