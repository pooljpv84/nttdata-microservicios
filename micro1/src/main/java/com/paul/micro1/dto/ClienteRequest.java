package com.paul.micro1.dto;

import jakarta.validation.constraints.*;

public record ClienteRequest(
        @NotBlank @Size(max=20) String identificacion,
        @NotBlank @Size(max=50) String clienteId,
        @NotBlank @Size(min=4, max=255) String contrasenia,
        Boolean estado
) {}