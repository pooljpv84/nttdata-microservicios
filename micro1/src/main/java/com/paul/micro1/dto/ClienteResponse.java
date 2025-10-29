package com.paul.micro1.dto;

public record ClienteResponse(
        Long id,
        String clienteId,
        String identificacion,
        String nombre,
        Boolean estado
) {

}
