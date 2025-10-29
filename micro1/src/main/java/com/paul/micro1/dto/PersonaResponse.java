package com.paul.micro1.dto;

public record PersonaResponse(
        Long id,
        String identificacion,
        String nombre,
        String direccion,
        String genero,
        String telefono) {
}
