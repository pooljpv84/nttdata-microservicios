package com.paul.micro1.events;

public record ClienteEventData(
        Long id,
        String clienteId,
        String identificacion,
        String nombre,
        Boolean estado
) {}
