package com.paul.micro1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PersonaMiniRequest(
        @NotBlank @Size(max=100) String nombre,
        @NotBlank @Size(max=20) String identificacion,
        @Size(max=255) String direccion,
        @Size(max=20) String telefono
) {}
