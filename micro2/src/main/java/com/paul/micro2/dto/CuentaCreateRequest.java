package com.paul.micro2.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record CuentaCreateRequest(
        @NotBlank String numero,
        @NotBlank String tipo,
        @NotBlank String clienteId, //id_cliente_cod
        @NotNull @DecimalMin(value = "0.00") BigDecimal saldo
) {}
