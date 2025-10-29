package com.paul.micro1.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClientePersonaCreateRequest(
        @Valid PersonaMiniRequest persona,
        @Size(max=20) String genero,
        @Min(0) @Max(120) Integer edad,
        @NotBlank @Size(min=4, max=255) String password,
        Boolean estado,
        @Size(max=50) String clienteId
) {}
