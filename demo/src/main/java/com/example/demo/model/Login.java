package com.example.demo.model;

public class Login {

    private String correo;
    private String contrasena;
    private String role;

    public Login() {
    }

    public Login(String correo, String contrasena, String role) {
        this.correo = correo;
        this.contrasena = contrasena;
        this.role = role;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
