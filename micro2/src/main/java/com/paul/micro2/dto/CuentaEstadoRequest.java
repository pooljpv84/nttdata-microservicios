package com.paul.micro2.dto;

import jakarta.validation.constraints.NotNull;

public record CuentaEstadoRequest(@NotNull Boolean activa) {}
