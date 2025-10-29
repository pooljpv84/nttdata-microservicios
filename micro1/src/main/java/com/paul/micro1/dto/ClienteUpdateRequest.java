package com.paul.micro1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ClienteUpdateRequest(
        @NotBlank @Size(min = 4, max = 255) String contrasenia,
        @NotNull Boolean estado
) {
}
