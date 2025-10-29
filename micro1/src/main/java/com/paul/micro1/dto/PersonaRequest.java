package com.paul.micro1.dto;

import jakarta.validation.constraints.*;

public record PersonaRequest(
        @NotBlank @Size(max=100) String nombre,
        @Size(max=20) String genero,
        @Min(0) @Max(120) Integer edad,
        @NotBlank @Size(max=20) String identificacion,
        @Size(max=255) String direccion,
        @Size(max=20) String telefono
) {}
