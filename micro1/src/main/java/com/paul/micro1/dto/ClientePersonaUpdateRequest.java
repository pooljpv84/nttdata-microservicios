package com.paul.micro1.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Solicitud para actualizar campos combinados de Persona y Cliente.
 * No permite cambiar la identificacion; se mantiene la existente.
 */
public record ClientePersonaUpdateRequest(
        // Persona
        @NotBlank @Size(max = 100) String nombre,
        @Size(max = 20) String genero,
        @Min(0) @Max(120) Integer edad,
        @Size(max = 255) String direccion,
        @Size(max = 20) String telefono,
        // Cliente
        @NotBlank @Size(min = 4, max = 255) String contrasenia,
        @NotNull Boolean estado
) {}
