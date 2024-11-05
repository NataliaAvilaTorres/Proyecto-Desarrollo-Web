// File: demo/src/main/java/com/example/demo/security/JwtUtil.java
package com.example.demo.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import com.example.demo.model.UserEntity;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private String secret = "TuClaveSecretaParaJWT"; // Puedes inyectarlo desde application.properties
    private long expiration = 86400000; // 1 día

    public String generateToken(UserEntity user) {
        String roles = user.getRoles().stream()
                           .map(Role::getName)
                           .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(user.getCorreo())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | 
                 ExpiredJwtException | UnsupportedJwtException | 
                 IllegalArgumentException ex) {
            // Manejar excepciones
            return false;
        }
    }
}
