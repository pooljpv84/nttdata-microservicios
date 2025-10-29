package com.paul.micro2.dto;

import com.paul.micro2.domain.TipoMovimiento;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record MovimientoCreateRequest(
        @NotBlank String numeroCuenta,
        @NotNull @DecimalMin("0.01") BigDecimal valor,
        @NotNull TipoMovimiento tipoMovimiento
) {}
