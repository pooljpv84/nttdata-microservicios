package com.paul.micro1.dto;

public record ClientePersonaResponse(
        ClienteResponse cliente,
        PersonaResponse persona
) {}
